package com.glenwood.glaceemr.infobutton.client;

import com.glenwood.glaceemr.infobutton.client.utils.SearchCriteria;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.ActClassObservation;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.ActClassRoot;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.ActRelationshipHasSubject;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.CD;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.CE;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.ObjectFactory;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.PQ;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.ParticipationInformationRecipient;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.ParticipationTargetSubject;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVAge;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVHealthCareProvider;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVInformationRecipient;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVKnowledgeRequestNotification;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVLanguageCommunication;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVMainSearchCriteria;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVPatient;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVPatientContext;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVPatientPerson;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVPerformer;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVPerson;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVSubTopic;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVSubject;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVSubject1;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVSubject2;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVSubject3;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVSubject6;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.REDSMT010001UVTaskContext;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.RoleClassHealthcareProvider;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.RoleClassPatient;
import com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest.TS;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 *
 * @author gsivashanmugam
 */
public class InfobuttonClient {

    /**
     * @param args the command line arguments
     */
    
    public ObjectFactory objectFactory = new ObjectFactory();
    public String languageCodeString = new String();
    public final static String TASKCONTEXT_PROBLEM_LIST_REVIEW= new String("PROBLISTREV");
    public final static String TASKCONTEXT_PROBLEM_LIST_ENTRY= new String("PROBLISTE");
    public final static String TASKCONTEXT_LAB_RESULTS_REVIEW= new String("LABRREV");
    public final static String TASKCONTEXT_LAB_ORDER_ENTRY= new String("LABOE");
    public final static String TASKCONTEXT_MED_LIST_REVIEW= new String("MLREV");
    public final static String TASKCONTEXT_MED_ORDER_ENTRY= new String("MEDOE");
    public final static int INFORMATION_RECIPIENT_HEALTH_CARE_PROVIDER = 1;
    public final static int INFORMATION_RECIPIENT_PATIENT = 2;
    
    public REDSMT010001UVKnowledgeRequestNotification getInfoButtonRequestObject(int patientAgeInDays, String genderFlag, int recipientType, int userType, String languageCode, String taskContext, String subTopic, ArrayList<SearchCriteria> searchCriteria)throws Exception{
        this.languageCodeString = languageCode;
        
        REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification = new REDSMT010001UVKnowledgeRequestNotification();    
        TS effectiveTime = new TS();
        effectiveTime.setValue("20121201");
        knowledgeRequestNotification.setEffectiveTime(effectiveTime);
        
        REDSMT010001UVSubject subject1 = new REDSMT010001UVSubject();

        REDSMT010001UVPatientContext patientContext = new REDSMT010001UVPatientContext();
        patientContext.setClassCode(RoleClassPatient.PAT);

        REDSMT010001UVPatientPerson patientPerson = new REDSMT010001UVPatientPerson();
        if(genderFlag!=null && !genderFlag.trim().equals("")){
            patientPerson.setAdministrativeGenderCode(this.getGenderObject(genderFlag));
        }
        patientContext.setPatientPerson(this.objectFactory.createREDSMT010001UVPatientContextPatientPerson(patientPerson));
                      
        REDSMT010001UVSubject6 subjectOf = new REDSMT010001UVSubject6();
        subjectOf.setAge(this.objectFactory.createREDSMT010001UVSubject6Age(this.getAgeElement(patientAgeInDays)));
        patientContext.getSubjectOf().add(subjectOf);

        subject1.setPatientContext(patientContext);
        subject1.setTypeCode(ParticipationTargetSubject.SBJ);

        knowledgeRequestNotification.setSubject1(this.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject1(subject1));
        knowledgeRequestNotification.setPerformer(this.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationPerformer(this.getPerformerAsHealthCareProvider(recipientType,userType)));
        knowledgeRequestNotification.setInformationRecipient(this.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationInformationRecipient(this.getInformationRecipient(recipientType, userType)));

        REDSMT010001UVSubject1 subject2 = new REDSMT010001UVSubject1();
        subject2.setTaskContext(this.getTaskContext(taskContext));
        subject2.setTypeCode(ActRelationshipHasSubject.SUBJ);
        knowledgeRequestNotification.setSubject2(this.objectFactory.createREDSMT010001UVKnowledgeRequestNotificationSubject2(subject2));
        
        for(int criteriaCnt=0; criteriaCnt<searchCriteria.size();criteriaCnt++){
            REDSMT010001UVSubject3 subject4 = new REDSMT010001UVSubject3();
            SearchCriteria criteria = searchCriteria.get(criteriaCnt);
            subject4.setMainSearchCriteria(this.getMainSearchCriteria(criteria.getCode(), criteria.getDescription(), criteria.getCodeSystem()));
            knowledgeRequestNotification.getSubject4().add(subject4);        
        }
        
        return knowledgeRequestNotification;
    }

    public REDSMT010001UVSubject2 addSubTopic(String subTopicCode, String subTopicName, String subTopicCodeSystem){
        REDSMT010001UVSubject2 subject3 = new REDSMT010001UVSubject2();
        REDSMT010001UVSubTopic subTopic = new REDSMT010001UVSubTopic();
        subTopic.setClassCode(ActClassObservation.OBS);
        CD code = new CD();
        code.setCode(subTopicCode);
        code.setCodeSystem(subTopicCodeSystem);
        code.setDisplayName(subTopicName);
        subTopic.setValue(code);
        return subject3;
    }
    
    public REDSMT010001UVKnowledgeRequestNotification getInfoButtonRequestObject(String knowledgeRequestNotficationXML)throws Exception{
        JAXBContext jc = JAXBContext.newInstance("com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest");
        Unmarshaller u = jc.createUnmarshaller();
        REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotfication = (REDSMT010001UVKnowledgeRequestNotification)((JAXBElement) u.unmarshal(new ByteArrayInputStream(knowledgeRequestNotficationXML.getBytes()))).getValue();
        return knowledgeRequestNotfication;
    }
    
    public String getInfoButtonRequestXML(int patientAgeInDays, String genderFlag, int recipientType, int userType, String languageCode, String taskContext, String subTopic, ArrayList<SearchCriteria> searchCriteria)throws Exception{
        REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification = this.getInfoButtonRequestObject(patientAgeInDays, genderFlag, recipientType, userType, languageCode, taskContext, subTopic, searchCriteria);
        return this.getInfoButtonRequestXML(knowledgeRequestNotification);
    }
    
    public String getInfoButtonRequestXML(REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification)throws Exception{
        JAXBContext jc = JAXBContext.newInstance("com.glenwood.glaceemr.infobutton.utils.KnowledgeRequest");
        Marshaller marshal = jc.createMarshaller();
        JAXBElement<REDSMT010001UVKnowledgeRequestNotification> doc = (new ObjectFactory()).createREDSMT010001UVKnowledgeRequestNotification(knowledgeRequestNotification);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(outStream);
        marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshal.marshal(doc, out);
        return new String(outStream.toByteArray());
    }
    
    public String getInfoButtonRequestURL(int patientAgeInDays, String genderFlag, int recipientType, int userType, String languageCode, String taskContext, String subTopic, ArrayList<SearchCriteria> searchCriteria)throws Exception{
        REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification = this.getInfoButtonRequestObject(patientAgeInDays, genderFlag, recipientType, userType, languageCode, taskContext, subTopic, searchCriteria);    
        String URL = new String();
        this.getInfoButtonRequestURL(knowledgeRequestNotification);
        return URL;
    }
    
    public String getInfoButtonRequestURL(String knowledgeRequestNotificationXML)throws Exception{
        REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification = this.getInfoButtonRequestObject(knowledgeRequestNotificationXML);
        return this.getInfoButtonRequestURL(knowledgeRequestNotification);
    } 
    
    public String getInfoButtonRequestURL(REDSMT010001UVKnowledgeRequestNotification knowledgeRequestNotification)throws Exception{
        StringBuilder URL = new StringBuilder();
        TreeMap<String,String> parameters = new TreeMap<String,String>();
        
        if(knowledgeRequestNotification.getSubject1().getValue().getPatientContext()!=null){
            if(knowledgeRequestNotification.getSubject1().getValue().getPatientContext().getPatientPerson()!=null){
                if(knowledgeRequestNotification.getSubject1().getValue().getPatientContext().getPatientPerson().getValue().getAdministrativeGenderCode()!=null){
                    this.getParameterString(parameters, "patientPerson.administrativeGenderCode.c", knowledgeRequestNotification.getSubject1().getValue().getPatientContext().getPatientPerson().getValue().getAdministrativeGenderCode().getCode());
                    this.getParameterString(parameters, "patientPerson.administrativeGenderCode.cs", knowledgeRequestNotification.getSubject1().getValue().getPatientContext().getPatientPerson().getValue().getAdministrativeGenderCode().getCodeSystem());
                }
            }
            List<REDSMT010001UVSubject6> subjectOfList = knowledgeRequestNotification.getSubject1().getValue().getPatientContext().getSubjectOf();
            this.getParameterString(parameters, "age.c.c", subjectOfList.get(0).getAge().getValue().getCode().getCode());
            this.getParameterString(parameters, "age.c.cs", subjectOfList.get(0).getAge().getValue().getCode().getCodeSystem());
            this.getParameterString(parameters, "age.v.v", subjectOfList.get(0).getAge().getValue().getValue().getValue());
            this.getParameterString(parameters, "age.v.u", subjectOfList.get(0).getAge().getValue().getValue().getUnit());
        }
        
        if(knowledgeRequestNotification.getPerformer()!=null){
            if(knowledgeRequestNotification.getPerformer().getValue().getHealthCareProvider()!=null){
                this.getParameterString(parameters, "performer", "PROV");
                this.getParameterString(parameters,"performer.healthCareProvider.c.c",knowledgeRequestNotification.getPerformer().getValue().getHealthCareProvider().getValue().getCode().getCode());
                this.getParameterString(parameters,"performer.healthCareProvider.c.cs",knowledgeRequestNotification.getPerformer().getValue().getHealthCareProvider().getValue().getCode().getCodeSystem());
                this.getParameterString(parameters,"performer.healthCareProvider.c.dn",knowledgeRequestNotification.getPerformer().getValue().getHealthCareProvider().getValue().getCode().getDisplayName());
                this.getParameterString(parameters,"performer.languageCode.c",knowledgeRequestNotification.getPerformer().getValue().getHealthCareProvider().getValue().getHealthCarePerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCode());
                this.getParameterString(parameters,"performer.languageCode.cs",knowledgeRequestNotification.getPerformer().getValue().getHealthCareProvider().getValue().getHealthCarePerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCodeSystem());
                this.getParameterString(parameters,"performer.languageCode.dn",knowledgeRequestNotification.getPerformer().getValue().getHealthCareProvider().getValue().getHealthCarePerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getDisplayName());
            }
            if(knowledgeRequestNotification.getPerformer().getValue().getPatient()!=null){
                this.getParameterString(parameters, "performer", "PAT");
                this.getParameterString(parameters,"performer.languageCode.c",knowledgeRequestNotification.getPerformer().getValue().getPatient().getValue().getPatientPerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCode());
                this.getParameterString(parameters,"performer.languageCode.cs",knowledgeRequestNotification.getPerformer().getValue().getPatient().getValue().getPatientPerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCodeSystem());
                this.getParameterString(parameters,"performer.languageCode.dn",knowledgeRequestNotification.getPerformer().getValue().getPatient().getValue().getPatientPerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getDisplayName());
            }
        }
        if(knowledgeRequestNotification.getInformationRecipient()!=null){
            if(knowledgeRequestNotification.getInformationRecipient().getValue().getHealthCareProvider()!=null){
                this.getParameterString(parameters, "informationRecipient", "PROV");
                this.getParameterString(parameters, "informationRecipient.healthCareProvider.c.c", knowledgeRequestNotification.getInformationRecipient().getValue().getHealthCareProvider().getValue().getCode().getCode());
                this.getParameterString(parameters, "informationRecipient.healthCareProvider.c.cs", knowledgeRequestNotification.getInformationRecipient().getValue().getHealthCareProvider().getValue().getCode().getCodeSystem());
                this.getParameterString(parameters, "informationRecipient.healthCareProvider.c.dn", knowledgeRequestNotification.getInformationRecipient().getValue().getHealthCareProvider().getValue().getCode().getDisplayName());
                this.getParameterString(parameters, "informationRecipient.languageCode.c", knowledgeRequestNotification.getInformationRecipient().getValue().getHealthCareProvider().getValue().getHealthCarePerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCode());
                this.getParameterString(parameters, "informationRecipient.languageCode.cs", knowledgeRequestNotification.getInformationRecipient().getValue().getHealthCareProvider().getValue().getHealthCarePerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCodeSystem());
                this.getParameterString(parameters, "informationRecipient.languageCode.dn", knowledgeRequestNotification.getInformationRecipient().getValue().getHealthCareProvider().getValue().getHealthCarePerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getDisplayName());
            }
            else if(knowledgeRequestNotification.getInformationRecipient().getValue().getPatient()!=null){
                this.getParameterString(parameters, "informationRecipient", "PAT");
                this.getParameterString(parameters, "informationRecipient.languageCode.c", knowledgeRequestNotification.getInformationRecipient().getValue().getPatient().getValue().getPatientPerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCode());
                this.getParameterString(parameters, "informationRecipient.languageCode.cs", knowledgeRequestNotification.getInformationRecipient().getValue().getPatient().getValue().getPatientPerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getCodeSystem());
                this.getParameterString(parameters, "informationRecipient.languageCode.dn", knowledgeRequestNotification.getInformationRecipient().getValue().getPatient().getValue().getPatientPerson().getValue().getLanguageCommunication().get(0).getLanguageCode().getDisplayName());
            }
        }        

        if(knowledgeRequestNotification.getSubject2().getValue().getTaskContext()!=null){
            this.getParameterString(parameters,"taskContext.c.c",knowledgeRequestNotification.getSubject2().getValue().getTaskContext().getCode().getCode());
            this.getParameterString(parameters,"taskContext.c.cs",knowledgeRequestNotification.getSubject2().getValue().getTaskContext().getCode().getCodeSystem());
            this.getParameterString(parameters,"taskContext.c.dn",knowledgeRequestNotification.getSubject2().getValue().getTaskContext().getCode().getDisplayName());
        }
        
        if(knowledgeRequestNotification.getSubject3()!=null){
            if(knowledgeRequestNotification.getSubject3().getValue().getSubTopic()!=null){
                this.getParameterString(parameters,"subTopic.c.c",knowledgeRequestNotification.getSubject3().getValue().getSubTopic().getValue().getCode());
                this.getParameterString(parameters,"subTopic.c.cs",knowledgeRequestNotification.getSubject3().getValue().getSubTopic().getValue().getCodeSystem());
                this.getParameterString(parameters,"subTopic.c.dn",knowledgeRequestNotification.getSubject3().getValue().getSubTopic().getValue().getDisplayName());
            }
        }        
        
        List<REDSMT010001UVSubject3> searchCriteriaList = knowledgeRequestNotification.getSubject4();
        for(int i=0;i<searchCriteriaList.size();i++){
            this.getParameterString(parameters,"mainSearchCriteria.v.c"+(i>0?i:""),searchCriteriaList.get(i).getMainSearchCriteria().getValue().getCode());
            this.getParameterString(parameters,"mainSearchCriteria.v.cs"+(i>0?i:""),searchCriteriaList.get(i).getMainSearchCriteria().getValue().getCodeSystem());
            this.getParameterString(parameters,"mainSearchCriteria.v.dn"+(i>0?i:""),searchCriteriaList.get(i).getMainSearchCriteria().getValue().getDisplayName());
        }
        
        Iterator<String> parameterSet = parameters.keySet().iterator();
        int i=0;
        while(parameterSet.hasNext()){
            String parameterName = parameterSet.next();
            if(i>0) {
                URL.append("&");
            }
            URL.append(parameterName).append("=").append(URLEncoder.encode(parameters.get(parameterName),"UTF-8"));
            i++;
        }
        return URL.toString();
    }
    
    private void getParameterString(TreeMap<String,String> parameters, String parameterName, String parameterValue){
        if(parameterValue!=null && !parameterValue.trim().equals("")){
            parameters.put(parameterName, parameterValue);
        }
    }
    
    private REDSMT010001UVAge getAgeElement(int age){
            REDSMT010001UVAge ageObject = new REDSMT010001UVAge();
            ageObject.setClassCode(ActClassObservation.OBS);
            ageObject.setMoodCode("DEF");

            CD ageCode = new CD();
            ageCode.setCode("30525-0");
            ageCode.setCodeSystem("2.16.840.1.113883.6.1");
            ageCode.setCodeSystemName("LOINC");
            ageCode.setDisplayName("Age");

            ageObject.setCode(ageCode);
            PQ value = new PQ();
            value.setUnit("a");
            value.setValue(""+age);
            
            ageObject.setValue(value);
            return ageObject;
    }
    
    private CE getGenderObject(String genderFlag){
        CE genderObject = new CE();
        genderObject.setCode(genderFlag);
        genderObject.setCodeSystem("2.16.840.1.113883.5.1");
        return genderObject;
    }
    
    private REDSMT010001UVPerformer getPerformerAsHealthCareProvider(int recipientType,int providerType){
        REDSMT010001UVPerformer performer = new REDSMT010001UVPerformer();
        performer.setHealthCareProvider(this.objectFactory.createREDSMT010001UVInformationRecipientHealthCareProvider(this.getHealthCareProvider(providerType)));
        return performer;
    }
    
    private REDSMT010001UVInformationRecipient getInformationRecipient(int recipientType, int providerType){
        REDSMT010001UVInformationRecipient recipient = new REDSMT010001UVInformationRecipient();
        recipient.setTypeCode(ParticipationInformationRecipient.IRCP);
        if(recipientType==this.INFORMATION_RECIPIENT_HEALTH_CARE_PROVIDER){
            recipient.setHealthCareProvider(this.objectFactory.createREDSMT010001UVInformationRecipientHealthCareProvider(this.getHealthCareProvider(providerType)));
        }else if(recipientType==this.INFORMATION_RECIPIENT_PATIENT){
            recipient.setPatient(this.objectFactory.createREDSMT010001UVInformationRecipientPatient(this.getPatient()));
        }
        return recipient;
    }
    
    private REDSMT010001UVHealthCareProvider getHealthCareProvider(int providerType){
        REDSMT010001UVHealthCareProvider provider = new REDSMT010001UVHealthCareProvider();
        REDSMT010001UVPerson person = new REDSMT010001UVPerson();
        
        REDSMT010001UVLanguageCommunication languageCommunication = new REDSMT010001UVLanguageCommunication();
        languageCommunication.setLanguageCode(this.getLanguageCode(this.languageCodeString));
        person.getLanguageCommunication().add(languageCommunication);
        provider.setHealthCarePerson(this.objectFactory.createREDSMT010001UVHealthCareProviderHealthCarePerson(person));
        
        provider.setClassCode(RoleClassHealthcareProvider.PROV);
        CE code = new CE();
        if(providerType==-1) {
            code.setCode("200000000X");
        }
        else if(providerType==-108) {
            code.setCode("163W00000X");
        }
        code.setCodeSystem("2.16.840.1.113883.6.101");
        provider.setCode(code);
        return provider;
    }
    
    private REDSMT010001UVPatient getPatient(){
        REDSMT010001UVPatient patient = new REDSMT010001UVPatient();

        REDSMT010001UVPerson person = new REDSMT010001UVPerson();
        REDSMT010001UVLanguageCommunication languageCommunication = new REDSMT010001UVLanguageCommunication();
        languageCommunication.setLanguageCode(this.getLanguageCode(this.languageCodeString));
        person.getLanguageCommunication().add(languageCommunication);
        
        patient.setPatientPerson(this.objectFactory.createREDSMT010001UVPatientPatientPerson(person));
        patient.setClassCode(RoleClassPatient.PAT);
        return patient;
    }
    
    private CE getLanguageCode(String code){
        CE languageCode = new CE();
        languageCode.setCode(code);
        languageCode.setCodeSystem("2.16.840.1.113883.6.121");
        return languageCode;
    }
    
    private REDSMT010001UVTaskContext getTaskContext(String taskCode){
        REDSMT010001UVTaskContext taskContext = new REDSMT010001UVTaskContext();
        taskContext.setClassCode(ActClassRoot.ACT);
        CD code = new CD();
        code.setCode(taskCode);
        code.setCodeSystem("2.16.840.1.113883.5.4");
        taskContext.setClassCode(ActClassRoot.ACT);
        taskContext.setCode(code);
        return taskContext;
    }
    
    private REDSMT010001UVSubTopic getSubTopic(String subTaskCode){
        REDSMT010001UVSubTopic subTopic = new REDSMT010001UVSubTopic();
        subTopic.setClassCode(ActClassObservation.OBS);
        
        CD code = new CD();
        code.setCode("KSBJ");
        code.setCodeSystem("2.16.840.1.113883.5.4");
        
        CD value = new CD();
        value.setCode(subTaskCode);
        value.setCodeSystem("2.16.840.1.113883.6.177");
        
        subTopic.setCode(code);
        subTopic.setValue(value);
        
        return subTopic;
    }
    
    private REDSMT010001UVMainSearchCriteria getMainSearchCriteria(String code, String codeDescription, String codeSystem){
        REDSMT010001UVMainSearchCriteria mainSearchCriteria = new REDSMT010001UVMainSearchCriteria();
        mainSearchCriteria.setClassCode(ActClassObservation.OBS);
        CD value = new CD();
        value.setCode(code);
        value.setCodeSystem(codeSystem);
        value.setDisplayName(codeDescription);
        mainSearchCriteria.setValue(value);
        return mainSearchCriteria;
    }
}