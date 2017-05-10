package com.mapia.utils;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.api.ApiUserController;
import com.mapia.domain.User;

public class HttpSessionUtils {
	private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);

	public static final String USER_SESSION_KEY = "logined";
	
	public static boolean isLoginUser(HttpSession session) {
		User sessionedUser = (User)session.getAttribute(USER_SESSION_KEY);
		logger.info(sessionedUser.toString());
	
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
