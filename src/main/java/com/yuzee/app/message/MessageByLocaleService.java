package com.yuzee.app.message;

public interface MessageByLocaleService {

	String getMessage(String id, Object[] arg, String language);
	
	String getMessage(String id, Object[] arg);
}