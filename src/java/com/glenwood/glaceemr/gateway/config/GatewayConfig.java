/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.config;

/**
 *
 * @author gsivashanmugam
 */
public class GatewayConfig {
    private String connectionString;
    private String sharedPath;
    
    GatewayConfig(){
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getSharedPath() {
        return sharedPath;
    }

    public void setSharedPath(String sharedPath) {
        this.sharedPath = sharedPath;
    }
}
