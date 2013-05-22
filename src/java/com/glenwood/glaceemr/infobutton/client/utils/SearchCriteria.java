/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.infobutton.client.utils;

/**
 *
 * @author gsivashanmugam
 */
public class SearchCriteria {
    String code = new String();
    String codeSystem = new String();
    String description = new String();
    
    public SearchCriteria(String code, String codeSystem, String description){
        this.setCode(code);
        this.setCodeSystem(codeSystem);
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    private void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }
    
}
