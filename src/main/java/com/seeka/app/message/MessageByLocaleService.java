package com.seeka.app.message;

/**
 * @author SeekADegree
 * 
 *
 */
public interface MessageByLocaleService {

	String getMessage(String id, Object[] arg, String language);
	
	String getMessage(String id, Object[] arg);
}