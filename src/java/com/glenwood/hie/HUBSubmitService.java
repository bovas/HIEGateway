package com.glenwood.hie;

import com.glenwood.hie.submitters.HIEConnector;
import com.glenwood.hie.submitters.HIEConnectorBuilder;
import com.glenwood.hie.utils.message.HIEMessage;
import com.glenwood.hie.utils.message.ObjectFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author gsivashanmugam
 */
public class HUBSubmitService extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder requestString = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                requestString.append(line);
            }
            rd.close();
            
            JAXBContext jc=JAXBContext.newInstance("com.glenwood.hie.utils.message");
            Unmarshaller unmarshal=jc.createUnmarshaller();
            HIEMessage requestMessage = (HIEMessage)((JAXBElement)unmarshal.unmarshal(new ByteArrayInputStream(URLDecoder.decode(requestString.toString(), "UTF-8").getBytes()))).getValue();
            System.out.println(URLDecoder.decode(requestString.toString(),"UTF-8"));
            HIEConnector connector = HIEConnectorBuilder.getConnector(requestMessage.getHeader().getTo());
            
            HIEMessage responseMessage = connector.submitMessage(requestMessage);
            
            Marshaller marshal=jc.createMarshaller();
            JAXBElement<HIEMessage> doc = (new ObjectFactory()).createHieMessage(responseMessage);
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            marshal.marshal(doc,out);
            out.flush();
        }catch(Exception e){
            e.printStackTrace();
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
    }// </editor-fold>
}
