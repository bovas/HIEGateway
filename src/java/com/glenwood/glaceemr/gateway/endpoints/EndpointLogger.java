/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.endpoints;

import com.glenwood.glaceemr.utils.DataBaseUtils;
import com.glenwood.glaceemr.utils.LogFile;
import com.glenwood.glaceemr.utils.LogLevel;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author gsivashanmugam
 */
public class EndpointLogger {
    public static long insertEndpointRequestLog(DataBaseUtils dbUtils, long gatewayTransactionId, String entityId, LogLevel level, String requestString, String rootPath)throws Exception{
        Calendar cal = GregorianCalendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyyHHMMss");
        String fileName = "/"+cal.get(Calendar.YEAR) +"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+formatter.format(cal.getTime())+"_endpoint_request.txt";
        LogFile.writeToFile(rootPath + "/log" + fileName, requestString);
        StringBuilder qryString = new StringBuilder();
        qryString.append("insert into entity_transaction_log(entity_transaction_log_gateway_transaction_log_id,entity_transaction_log_level,entity_transaction_log_entity_id,entity_transaction_log_request_file_name) ");
        qryString.append(" values("+gatewayTransactionId+","+level.intValue()+",'"+entityId+"','"+fileName+"')");
        Statement stmt = dbUtils.getStatement();
        stmt.executeUpdate(qryString.toString());
        ResultSet resultSet = stmt.executeQuery("select LAST_INSERT_ID()");
        long primaryId = -1;
        if(resultSet.first()){
            primaryId = resultSet.getLong(1);
        }
        return primaryId;
    }
    
    public static void updateEndpointResponseLog(DataBaseUtils dbUtils, long endPointTransactionId, LogLevel level, String responseString, String rootPath)throws Exception{
        Calendar cal = GregorianCalendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyyHHMMss");
        String fileName = "/"+cal.get(Calendar.YEAR) +"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+formatter.format(cal.getTime())+"_endpoint_response.txt";
        LogFile.writeToFile(rootPath + "/log" + fileName, responseString);
        StringBuilder qryString = new StringBuilder();
        qryString.append("update entity_transaction_log set entity_transaction_log_level="+level.intValue()+", entity_transaction_log_response_file_name='"+fileName+"' where entity_transaction_log_id="+endPointTransactionId);
        dbUtils.getStatement().executeUpdate(qryString.toString());        
    }
}
