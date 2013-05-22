package com.glenwood.hie.submitters;

import com.glenwood.hie.utils.message.HIEMessage;

public interface HIEConnector {
	public HIEMessage submitMessage(HIEMessage hieRequest) throws Exception;
}
