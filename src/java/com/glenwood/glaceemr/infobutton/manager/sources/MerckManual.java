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
public class MerckManual implements KnowledgeSource {

    @Override
    public String getResource(REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification) throws Exception {
        InfobuttonClient infoButtonClient = new InfobuttonClient();
        Abdera abdera = new Abdera();
        Feed feed = abdera.newFeed();
        feed.setTitle("Merck Manual");
        
        knowledgeRequestNotification.setSubject3(infoButtonClient.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject3(infoButtonClient.addSubTopic("Q000175", "Diagnosis", "2.16.840.1.113883.6.177")));
        Entry diagnosisEntry = feed.addEntry();
        diagnosisEntry.setTitle("Diagnosis");
        diagnosisEntry.addLink("https://www.merck-manual-infobutton.com/mminfbtn/search.do?"+infoButtonClient.getInfoButtonRequestURL(knowledgeRequestNotification));

        knowledgeRequestNotification.setSubject3(infoButtonClient.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject3(infoButtonClient.addSubTopic("Q000628", "Treatment", "2.16.840.1.113883.6.177")));
        Entry treatmentEntry = feed.addEntry();
        treatmentEntry.setTitle("Treatment");
        treatmentEntry.addLink("https://www.merck-manual-infobutton.com/mminfbtn/search.do?"+infoButtonClient.getInfoButtonRequestURL(knowledgeRequestNotification));

        knowledgeRequestNotification.setSubject3(infoButtonClient.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject3(infoButtonClient.addSubTopic("Q000209", "Etiology", "2.16.840.1.113883.6.177")));
        Entry etiologyEntry = feed.addEntry();
        etiologyEntry.setTitle("Etiology");
        etiologyEntry.addLink("https://www.merck-manual-infobutton.com/mminfbtn/search.do?"+infoButtonClient.getInfoButtonRequestURL(knowledgeRequestNotification));

        knowledgeRequestNotification.setSubject3(infoButtonClient.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject3(infoButtonClient.addSubTopic("D011379", "Prognosis", "2.16.840.1.113883.6.177")));
        Entry prognosisEntry = feed.addEntry();
        prognosisEntry.setTitle("Prognosis");
        prognosisEntry.addLink("https://www.merck-manual-infobutton.com/mminfbtn/search.do?"+infoButtonClient.getInfoButtonRequestURL(knowledgeRequestNotification));
        
        knowledgeRequestNotification.setSubject3(infoButtonClient.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject3(infoButtonClient.addSubTopic("Q000175", "Symptoms and Signs", "2.16.840.1.113883.6.177")));
        Entry symptomsEntry = feed.addEntry();
        symptomsEntry.setTitle("Symptoms and Signs");
        symptomsEntry.addLink("https://www.merck-manual-infobutton.com/mminfbtn/search.do?"+infoButtonClient.getInfoButtonRequestURL(knowledgeRequestNotification));

        knowledgeRequestNotification.setSubject3(infoButtonClient.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject3(infoButtonClient.addSubTopic("Q000193", "Patient Education", "2.16.840.1.113883.6.177")));
        Entry patientEducationEntry = feed.addEntry();
        patientEducationEntry.setTitle("Symptoms and Signs");
        patientEducationEntry.addLink("https://www.merck-manual-infobutton.com/mminfbtn/search.do?"+infoButtonClient.getInfoButtonRequestURL(knowledgeRequestNotification));
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        abdera.getWriter().writeTo(feed, out);
        return new String(out.toByteArray());        
    }
    
}
