package com.glenwood.glaceemr.hie.connectors;

import com.glenwood.glaceemr.hie.utils.message.HIEMessage;


public interface HIEConnector {
	public HIEMessage submitMessage(HIEMessage hieRequest) throws Exception;
}
