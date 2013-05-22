/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author gsivashanmugam
 */
public class GatewayContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.getAttribute("sharedPath");
        GatewayConfig config = new GatewayConfig();
        config.setConnectionString(context.getInitParameter("connectionString"));
        config.setSharedPath(context.getInitParameter("sharedPath"));
        context.setAttribute("gatewayConfig", config);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
}
