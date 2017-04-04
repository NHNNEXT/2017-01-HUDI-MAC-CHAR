package com.mapia.utils;

import javax.servlet.http.HttpSession;

import com.mapia.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "logined";
	
	public static boolean isLoginUser(HttpSession session) {
		User sessionedUser = (User)session.getAttribute(USER_SESSION_KEY);
		if (sessionedUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}
