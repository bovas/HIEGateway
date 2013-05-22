/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.gateway.endpoints;

/**
 *
 * @author gsivashanmugam
 */
public class EndpointBuilder {
    public static Endpoint getConnector(String endPointName)throws UnsupportedOperationException{
        Endpoint endPoint = null;
        if(endPointName.equalsIgnoreCase("HIE")){
            endPoint = new HIEEndpoint();
        }else{
            throw new UnsupportedOperationException("Endpoint not supported by hub currently.");
        }
        return endPoint;
    }
}
