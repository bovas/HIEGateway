package com.glenwood.glaceemr.gateway.services;

import com.glenwood.glaceemr.gateway.config.GatewayConfig;
import com.glenwood.glaceemr.gateway.endpoints.EndpointBuilder;
import com.glenwood.glaceemr.gateway.exceptions.UnSupportedEndpointException;
import com.glenwood.glaceemr.gateway.utils.GatewayMessageUtils;
import com.glenwood.glaceemr.gateway.utils.log.GatewayLogger;
import com.glenwood.glaceemr.gateway.utils.message.Body;
import com.glenwood.glaceemr.gateway.utils.message.GatewayMessage;
import com.glenwood.glaceemr.utils.DataBaseUtils;
import com.glenwood.glaceemr.utils.LogLevel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import org.apache.commons.codec.binary.Base64;

public class SubmitService extends HttpServlet{

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
        DataBaseUtils dbUtils = null;
        try{
            GatewayConfig config = (GatewayConfig)request.getServletContext().getAttribute("gatewayConfig");

            String rootPath  = config.getSharedPath();
            String connectionString = config.getConnectionString();
            String requestIPAddress = request.getRemoteAddr();
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            StringBuilder requestString = new StringBuilder();
            while((line = rd.readLine()) != null){
                requestString.append(line);
            }
            rd.close();
            System.out.println("****************:"+connectionString);
            dbUtils = new DataBaseUtils(connectionString);
            
            GatewayMessage requestMessage = new GatewayMessage();
            GatewayMessage responseMessage = new GatewayMessage();
            
            long transactionId=-1;
                    
            try{
                requestMessage = GatewayMessageUtils.getMessageObject(new String(Base64.decodeBase64(requestString.toString())));

                /* Begin transaction*/
                try{
                    transactionId = GatewayLogger.insertRequestLog(dbUtils, requestMessage.getHeader().getTo().getEndpoint(), LogLevel.INFO,  requestMessage.getHeader().getFrom().getAccId(), requestMessage.getHeader().getFrom().getUserId(),requestIPAddress, new String(Base64.decodeBase64(requestString.toString())),rootPath);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                responseMessage = EndpointBuilder.getConnector(transactionId,requestMessage,config);

                String responseMessageString = GatewayMessageUtils.getXMLString(responseMessage);
                try{
                    GatewayLogger.updateResponseLog(dbUtils, transactionId, LogLevel.INFO, responseMessageString, rootPath);
                }catch(Exception e){
                    e.printStackTrace();
                }
                /* End Transaction */
                
                out.print(Base64.encodeBase64String(responseMessageString.getBytes()));
                out.flush();
            }catch(JAXBException e){
                responseMessage.setHeader(requestMessage.getHeader());
                Body messageBody = new Body();
                com.glenwood.glaceemr.gateway.utils.message.Error err = new com.glenwood.glaceemr.gateway.utils.message.Error();
                err.setDescription("Message format error: " + e.getLocalizedMessage());
                messageBody.setError(err);
                responseMessage.setBody(messageBody);
                String responseMessageString = GatewayMessageUtils.getXMLString(responseMessage);
                GatewayLogger.updateResponseLog(dbUtils, transactionId, LogLevel.ERROR, responseMessageString, rootPath);
                out.print(Base64.encodeBase64String(responseMessageString.getBytes()));
                out.flush();
            }catch(UnSupportedEndpointException e){
                responseMessage.setHeader(requestMessage.getHeader());
                Body messageBody = new Body();
                com.glenwood.glaceemr.gateway.utils.message.Error err = new com.glenwood.glaceemr.gateway.utils.message.Error();
                err.setDescription(e.getLocalizedMessage());
                messageBody.setError(err);
                responseMessage.setBody(messageBody);
                String responseMessageString = GatewayMessageUtils.getXMLString(responseMessage);
                GatewayLogger.updateResponseLog(dbUtils, transactionId, LogLevel.CRITICAL, responseMessageString, rootPath);
                out.print(Base64.encodeBase64String(responseMessageString.getBytes()));
                out.flush();                
            }
        }catch(Exception e){
            e.printStackTrace();
            out.print("Error: " + e.getMessage());
            out.flush();
        }finally{
            if(dbUtils!=null){
                try{
                    dbUtils.destroy();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
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