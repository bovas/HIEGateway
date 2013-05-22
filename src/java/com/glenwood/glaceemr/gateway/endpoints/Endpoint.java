/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.endpoints;

import com.glenwood.glaceemr.gateway.config.GatewayConfig;
import com.glenwood.glaceemr.gateway.utils.message.GatewayMessage;

/**
 *@author gsivashanmugam
 */
public abstract class Endpoint {
    long transactionId;
    GatewayConfig config;
    
    private Endpoint(){
    }
    
    public Endpoint(long transactionId, GatewayConfig config){
        this.transactionId=transactionId;
        this.config=config;
    }
    
    public abstract String forward(String requestMessage)throws Exception;
}
