/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.gateway.endpoints;

import com.glenwood.gateway.utils.message.GatewayMessage;
import com.glenwood.hie.submitters.HIEConnector;
import com.glenwood.hie.submitters.HIEConnectorBuilder;
import com.glenwood.hie.utils.message.HIEMessage;
import com.glenwood.hie.utils.message.ObjectFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.codec.binary.Base64;

/**
 * @author gsivashanmugam
 */
public class HIEEndpoint implements Endpoint {
    @Override
    public GatewayMessage forward(GatewayMessage requestMessage)throws Exception{
        try {
            GatewayMessage responseMessage = new GatewayMessage();
            JAXBContext context=JAXBContext.newInstance("com.glenwood.hie.utils.message");

            Unmarshaller unMarshaller=context.createUnmarshaller();
            HIEMessage requestHIEmessage = (HIEMessage)((JAXBElement)unMarshaller.unmarshal(new ByteArrayInputStream(Base64.decodeBase64(new String(requestMessage.getBody()))))).getValue();
            HIEConnector connector = HIEConnectorBuilder.getConnector(requestHIEmessage.getHeader().getTo());
            HIEMessage responseHIEMessage = connector.submitMessage(requestHIEmessage);
            responseMessage.setHeader(requestMessage.getHeader());
            
            Marshaller marshaller=context.createMarshaller();
            JAXBElement<HIEMessage> rootElement = (new ObjectFactory()).createHieMessage(responseHIEMessage);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            marshaller.marshal(rootElement, outStream);
            
            responseMessage.setBody(Base64.encodeBase64(outStream.toByteArray()));
            
            return responseMessage;
        }catch (JAXBException e) {
            e.printStackTrace();
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
