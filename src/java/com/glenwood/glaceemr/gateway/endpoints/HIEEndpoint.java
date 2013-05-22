/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.endpoints;

import com.glenwood.glaceemr.gateway.config.GatewayConfig;
import com.glenwood.glaceemr.hie.connectors.HIEConnector;
import com.glenwood.glaceemr.hie.connectors.HIEConnectorBuilder;
import com.glenwood.glaceemr.hie.exceptions.UnSupportedHIEException;
import com.glenwood.glaceemr.hie.utils.message.HIEMessage;
import com.glenwood.glaceemr.hie.utils.message.Message;
import com.glenwood.glaceemr.hie.utils.message.ObjectFactory;
import com.glenwood.glaceemr.hie.utils.message.Response;
import com.glenwood.glaceemr.utils.DataBaseUtils;
import com.glenwood.glaceemr.utils.LogLevel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author gsivashanmugam
 */
public class HIEEndpoint extends Endpoint {

    public HIEEndpoint(long transactionId, GatewayConfig config){
        super(transactionId, config);
    }
    
    public String forward(String requestMessage)throws Exception{
        DataBaseUtils dbUtils = null;
        long endPointTransactionId=-1;
        try {
            dbUtils = new DataBaseUtils(this.config.getConnectionString());
            
            JAXBContext context=JAXBContext.newInstance("com.glenwood.glaceemr.hie.utils.message");

            Unmarshaller unMarshaller=context.createUnmarshaller();
            Marshaller marshaller=context.createMarshaller();
            
            HIEMessage requestHIEmessage = (HIEMessage)((JAXBElement)unMarshaller.unmarshal(new ByteArrayInputStream(requestMessage.getBytes()))).getValue();
            HIEMessage responseHIEMessage = new HIEMessage();
            
            try{
                try{
                    endPointTransactionId = EndpointLogger.insertEndpointRequestLog(dbUtils, this.transactionId, requestHIEmessage.getHeader().getTo(), LogLevel.INFO, requestMessage, this.config.getSharedPath());
                }catch(Exception e){
                    e.printStackTrace();
                }
                HIEConnector connector = HIEConnectorBuilder.getConnector(requestHIEmessage.getHeader().getTo());
                responseHIEMessage = connector.submitMessage(requestHIEmessage);
                JAXBElement<HIEMessage> rootElement = (new ObjectFactory()).createHieMessage(responseHIEMessage);
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                marshaller.marshal(rootElement, outStream);
                try{
                    EndpointLogger.updateEndpointResponseLog(dbUtils, endPointTransactionId, LogLevel.INFO, new String(outStream.toByteArray()), this.config.getSharedPath());
                }catch(Exception e){
                    e.printStackTrace();
                }
                return new String(outStream.toByteArray());
            }catch(UnSupportedHIEException e){
                com.glenwood.glaceemr.hie.utils.message.Error err = new com.glenwood.glaceemr.hie.utils.message.Error();
                err.setDescription("HIE not supported");
                Message message = new Message();
                message.setError(err);
                Response response = new Response();
                response.getMessage().add(message);
                com.glenwood.glaceemr.hie.utils.message.Body body = new com.glenwood.glaceemr.hie.utils.message.Body();
                body.setResponse(response);
                responseHIEMessage.setBody(body);
                JAXBElement<HIEMessage> rootElement = (new ObjectFactory()).createHieMessage(responseHIEMessage);
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                marshaller.marshal(rootElement, outStream);
                EndpointLogger.updateEndpointResponseLog(dbUtils, endPointTransactionId, LogLevel.CRITICAL, new String(outStream.toByteArray()), this.config.getSharedPath());
                return new String(outStream.toByteArray());
            }
        }catch (JAXBException e) {
            throw e;
        }catch (Exception e) {
            throw e;
        }
    }
}
