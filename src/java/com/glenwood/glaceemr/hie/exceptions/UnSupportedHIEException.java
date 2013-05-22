/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.hie.exceptions;

/**
 *
 * @author gsivashanmugam
 */
public class UnSupportedHIEException extends Exception {
    public UnSupportedHIEException(){
        super();
    }
    
    public UnSupportedHIEException(String message){
        super(message);
    }    
}
