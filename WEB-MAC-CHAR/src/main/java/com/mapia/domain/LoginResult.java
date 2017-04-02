package com.mapia.domain;

import com.mapia.utils.Result;

public class LoginResult implements Result {
	private Status statusCode;
	private String errorMessage;
	
	private LoginResult(Status statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}
	
	public Status getStatusCode() {
		return statusCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static LoginResult ok() {
		return new LoginResult(Status.Ok, null);
	}
	
	public static LoginResult emailNotFound(String errorMessage) {
		return new LoginResult(Status.EmailNotFound, errorMessage);
	}
	
	public static LoginResult invalidPassword(String errorMessage) {
		return new LoginResult(Status.InvalidPassword, errorMessage);
	}
}
