/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.exceptions;

/**
 *
 * @author gsivashanmugam
 */
public class UnSupportedEndpointException extends Exception {
    public UnSupportedEndpointException(){
        super();
    }
    
    public UnSupportedEndpointException(String message){
        super(message);
    }    
}
