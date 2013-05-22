/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.endpoints;

import com.glenwood.glaceemr.gateway.config.GatewayConfig;
import com.glenwood.glaceemr.gateway.exceptions.UnSupportedEndpointException;
import com.glenwood.glaceemr.gateway.utils.message.Body;
import com.glenwood.glaceemr.gateway.utils.message.GatewayMessage;

/**
 *
 * @author gsivashanmugam
 */
public class EndpointBuilder {
    public static GatewayMessage getConnector(long transactionId, GatewayMessage requestMessage, GatewayConfig config)throws UnSupportedEndpointException,Exception{
        String endPointName = requestMessage.getHeader().getTo().getEndpoint();
        String messageString = new String();
        
        GatewayMessage responseMessage = new GatewayMessage();
        responseMessage.setHeader(requestMessage.getHeader());
        
        if(endPointName.equalsIgnoreCase("HIE")){
            HIEEndpoint endPoint = new HIEEndpoint(transactionId, config);
            messageString = endPoint.forward(new String(requestMessage.getBody().getContent()));
        }else if(endPointName.equalsIgnoreCase("infobutton")){
            InfobuttonManager infoButtonManger = new InfobuttonManager(transactionId, config);
            messageString = infoButtonManger.forward(new String(requestMessage.getBody().getContent()));
        }else{
            throw new UnSupportedEndpointException("Endpoint not supported by hub currently.");
        }
        
        Body messageBody = new Body();
        messageBody.setContent(messageString.getBytes());
        responseMessage.setBody(messageBody);
        return responseMessage;        
    }
}
