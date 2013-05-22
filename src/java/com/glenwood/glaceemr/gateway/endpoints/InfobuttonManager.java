/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.endpoints;

import com.glenwood.glaceemr.gateway.config.GatewayConfig;
import com.glenwood.glaceemr.infobutton.client.InfobuttonClient;
import com.glenwood.glaceemr.infobutton.manager.sources.MedlinePlusConnect;
import com.glenwood.glaceemr.infobutton.manager.sources.MerckManual;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVKnowledgeRequestNotification;


/**
 *
 * @author gsivashanmugam
 */
public class InfobuttonManager extends Endpoint {

    public InfobuttonManager(long transactionId, GatewayConfig config){
        super(transactionId, config);
    }
    
    @Override
    public String forward(String requestMessage) throws Exception {
        InfobuttonClient infoButtonClient = new InfobuttonClient();
        REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification = infoButtonClient.getInfoButtonRequestObject(requestMessage);

        StringBuilder knowledgeResponse = new StringBuilder();
        knowledgeResponse.append("<knowledgeResponse>");
        MedlinePlusConnect medlinePlusConnecter = new MedlinePlusConnect();
        knowledgeResponse.append(medlinePlusConnecter.getResource(knowledgeRequestNotification));

        MerckManual merckManulaConnecter = new MerckManual();
        knowledgeResponse.append(merckManulaConnecter.getResource(knowledgeRequestNotification));        
        knowledgeResponse.append("<knowledgeResponse>");
        return knowledgeResponse.toString();
    }
    
}
