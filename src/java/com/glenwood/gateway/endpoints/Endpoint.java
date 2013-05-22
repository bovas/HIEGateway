/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.gateway.endpoints;

import com.glenwood.gateway.utils.message.GatewayMessage;

/**
 *@author gsivashanmugam
 */
public interface Endpoint {
    public GatewayMessage forward(GatewayMessage requestMessage)throws Exception;
}
