package com.glenwood.hie.services;

import com.glenwood.gateway.endpoints.Endpoint;
import com.glenwood.gateway.endpoints.EndpointBuilder;
import com.glenwood.gateway.utils.message.GatewayMessage;
import com.glenwood.gateway.utils.message.ObjectFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.codec.binary.Base64;

public class SubmitService extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            //Reading the inputstream
            BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder requestString = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                requestString.append(line);
            }
            rd.close();
            
            System.out.println(new String(Base64.decodeBase64(requestString.toString())));
            
            //Converting the content to Gatewaymessage object
            JAXBContext context=JAXBContext.newInstance("com.glenwood.gateway.utils.message");
            Unmarshaller unMarshaller=context.createUnmarshaller();
            GatewayMessage requestMessage = (GatewayMessage)((JAXBElement)unMarshaller.unmarshal(new ByteArrayInputStream(Base64.decodeBase64(requestString.toString())))).getValue();
            
            System.out.println("*******Before getting the connector**************");
            //Based on the endpoint string getting the endpoint object
            Endpoint endPoint = EndpointBuilder.getConnector(requestMessage.getHeader().getTo().getEndpoint());
            
            System.out.println("**********Before forwarding the request***********");
            //forwarding the gateway request message to the endpoint and getting the response back
            GatewayMessage responseMessage = endPoint.forward(requestMessage);
            
            System.out.println("**********After forwarding the request***********");
            //Writing the response back to the HTTP response stream
            Marshaller marshaller = context.createMarshaller();
            JAXBElement<GatewayMessage> rootElement = (new ObjectFactory()).createGatewayMessage(responseMessage);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            marshaller.marshal(rootElement, outStream);
            out.print(Base64.encodeBase64String(outStream.toByteArray()));
            out.flush();
        }catch(Exception e){
            e.printStackTrace();
            out.print("Error: " + e.getMessage());
            out.flush();
        }finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}