/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.infobutton.manager;

import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVKnowledgeRequestNotification;

/**
 *
 * @author gsivashanmugam
 */
public interface KnowledgeSource {
    public String getResource(REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification)throws Exception;
}
