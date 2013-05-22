/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.infobutton.manager.sources;

import com.glenwood.glaceemr.infobutton.client.InfobuttonClient;
import com.glenwood.glaceemr.infobutton.manager.KnowledgeSource;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVKnowledgeRequestNotification;
import java.io.ByteArrayOutputStream;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;

/**
 *
 * @author gsivashanmugam
 */
public class MedlinePlusConnect implements KnowledgeSource {

    @Override
    public String getResource(REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification)throws Exception{
        InfobuttonClient infoButtonClient = new InfobuttonClient();
        Abdera abdera = new Abdera();
        Feed feed = abdera.newFeed();
        feed.setTitle("MedlinePlus");
        Entry entry = feed.addEntry();
        entry.setTitle("Patient Education");
        entry.addLink("https://apps2.nlm.nih.gov/medlineplus/services/mpconnect.cfm?"+infoButtonClient.getInfoButtonRequestURL(knowledgeRequestNotification));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        abdera.getWriter().writeTo(feed, out);
        return new String(out.toByteArray());
    }
    
}
