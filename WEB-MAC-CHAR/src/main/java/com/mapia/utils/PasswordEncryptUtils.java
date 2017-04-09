package com.mapia.utils;

import java.security.MessageDigest;

public class PasswordEncryptUtils {
	public static String getEncSHA256(String password) throws Exception { 
		StringBuffer sb = new StringBuffer();
		
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
		mDigest.update(password.getBytes());
		
		byte[] msgStr = mDigest.digest();
		
		for (int i=0; i < msgStr.length; i++) {
			byte tmpStrByte = msgStr[i];
			String tmpEncPasswd = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
			
			sb.append(tmpEncPasswd);
		}
		
		return sb.toString();
	}
}
