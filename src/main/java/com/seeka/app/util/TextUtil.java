package com.seeka.app.util;

public class TextUtil {
	public static boolean isNullOrEmpty(String value) {
		if(value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean isNullObject(Object object) {
		if(object == null) {
			return true;
		}
		return false;
	}

}
