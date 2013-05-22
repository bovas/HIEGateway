package com.glenwood.hie.khie.submitters;

import com.glenwood.hie.khie.stub.IPartnerHIEService;
import com.glenwood.hie.khie.stub.IPartnerHIEServiceSendHIEMessageDefaultFaultContractFaultFaultMessage;
import com.glenwood.hie.khie.stub.PartnerHIEService;
import com.glenwood.hie.submitters.HIEConnector;
import com.glenwood.hie.utils.message.Body;
import com.glenwood.hie.utils.message.HIEMessage;
import com.glenwood.hie.utils.message.Header;
import com.glenwood.hie.utils.message.Message;
import com.glenwood.hie.utils.message.Property;
import com.glenwood.hie.utils.message.Response;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author gsivashanmugam
 */
public class KHIESubmitter implements HIEConnector {

    @Override
    public HIEMessage submitMessage(HIEMessage HIERequest) throws Exception {
        try{
            HIEMessage HIEResponse = new HIEMessage();
            boolean isTesting = true;
            
            if(HIERequest.getHeader().getTo().equalsIgnoreCase("khie")){
                isTesting = false;
                Logger.getAnonymousLogger().log(Level.INFO, "Submitting to KHIE production endpoint");
            }else if(HIERequest.getHeader().getTo().equalsIgnoreCase("khiestage")){
                isTesting = true;
                Logger.getAnonymousLogger().log(Level.INFO, "Submitting to KHIE testing endpoint");
            }else{
                isTesting = true;
            }

            String emrSystemOID = new String();
            String partnerLocationID = new String();
            String locationNPI = new String();
            String userNPI = new String();
            String requestingSystemUserID = new String();
            String businessName = new String();
            String messageSystem = new String();

            Iterator<Property> propertyList = HIERequest.getProperties().getProperty().iterator();
            
            while (propertyList.hasNext()) {
                Property property = propertyList.next();
                String propertyName = property.getName();
                if (propertyName.equals("emrSystemOID")) {
                    emrSystemOID = property.getValue();
                } else if (propertyName.equals("partnerLocationID")) {
                    partnerLocationID = property.getValue();
                } else if (propertyName.equals("locationNPI")) {
                    locationNPI = property.getValue();
                } else if (propertyName.equals("userNPI")) {
                    userNPI = property.getValue();
                } else if (propertyName.equals("requestingSystemUserID")) {
                    requestingSystemUserID = property.getValue();
                } else if (propertyName.equals("businessName")) {
                    businessName = property.getValue();
                } else if (propertyName.equals("messageSystem")) {
                    messageSystem = property.getValue();
                }
            }

            List<Message> messages = HIERequest.getBody().getRequest().getMessage();

            Body responseBody = new Body();
            Response response = new Response();

            for (int msgCnt = 0; msgCnt < messages.size(); msgCnt++) {
                Message requestMessage = messages.get(msgCnt);
                Message responseMessage = new Message();
                String endPointURL = "http://kentuckyhieuat.acsmessaging.com/PartnerHIEService/PartnerHIEService.svc";
                if (isTesting) {
                    endPointURL = "http://kentuckyhieuat.acsmessaging.com/PartnerHIEService/PartnerHIEService.svc";
                } else {
                    endPointURL = "http://kentuckyhie.acsmessaging.com/PartnerHIEService/PartnerHIEService.svc";
                }
                try {
                    PartnerHIEService service = new PartnerHIEService();
                    IPartnerHIEService proxy = service.getPort(new QName("http://ACS.HIE.ServiceContracts/2009/10", "WSHttpBinding_IPartnerHIEService", ""), IPartnerHIEService.class);
                    ((BindingProvider) proxy).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointURL);
                    responseMessage.setId(requestMessage.getId());
                    responseMessage.setContent(new String(Base64.decodeBase64(proxy.sendHIEMessage(emrSystemOID, partnerLocationID, locationNPI, userNPI, requestingSystemUserID, businessName, messageSystem, requestMessage.getType(), Base64.encodeBase64String(requestMessage.getContent().getBytes())))));
                } catch (IPartnerHIEServiceSendHIEMessageDefaultFaultContractFaultFaultMessage ex) {
                    ex.printStackTrace();
                    com.glenwood.hie.utils.message.Error err = new com.glenwood.hie.utils.message.Error();
                    err.setDescription(ex.getLocalizedMessage());
                    responseMessage.setError(err);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    com.glenwood.hie.utils.message.Error err = new com.glenwood.hie.utils.message.Error();
                    err.setDescription(ex.getLocalizedMessage());
                    responseMessage.setError(err);
                }
                response.getMessage().add(responseMessage);
            }
            Header responseHeader = new Header();
            responseHeader.setFrom(HIERequest.getHeader().getTo());
            responseHeader.setTo(HIERequest.getHeader().getFrom());
            HIEResponse.setHeader(responseHeader);
            responseBody.setResponse(response);
            HIEResponse.setBody(responseBody);
            return HIEResponse;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
