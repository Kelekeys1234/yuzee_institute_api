package com.seeka.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class PasswordUtil {
	
	public static String generateEncrptedPassword(String clearPassword) {
	    MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-1 algorithm not found!");
		}
	    byte [] digest = algorithm.digest(clearPassword.getBytes());
	    String encrptedPw = new String(Hex.encodeHex(digest));
	    return encrptedPw;
	}

}
