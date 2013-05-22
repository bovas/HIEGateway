/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.hie.submitters;

import com.glenwood.hie.khie.submitters.KHIESubmitter;

/**
 *
 * @author gsivashanmugam
 */
public class HIEConnectorBuilder {
    public static HIEConnector getConnector(String entityName)throws UnsupportedOperationException{
        HIEConnector connector = null;
        if(entityName.equalsIgnoreCase("khie")){
            connector = new KHIESubmitter();
        }if(entityName.equalsIgnoreCase("khiestage")){
            connector = new KHIESubmitter();
        }if(connector==null){
            throw new UnsupportedOperationException("Entity not supported by hub currently.");
        }
        return connector;
    }
}
