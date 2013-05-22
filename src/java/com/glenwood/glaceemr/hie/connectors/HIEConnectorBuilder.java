/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.hie.connectors;

import com.glenwood.glaceemr.hie.exceptions.UnSupportedHIEException;

/**
 *
 * @author gsivashanmugam
 */
public class HIEConnectorBuilder {
    public static HIEConnector getConnector(String entityName)throws UnSupportedHIEException{
        HIEConnector connector = null;
        if(entityName.equalsIgnoreCase("khie")){
            connector = new KHIEConnector();
        }if(entityName.equalsIgnoreCase("khiestage")){
            connector = new KHIEConnector();
        }if(connector==null){
            throw new UnSupportedHIEException("Entity not supported by hub currently.");
        }
        return connector;
    }
}
