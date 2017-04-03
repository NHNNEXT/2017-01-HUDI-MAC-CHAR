package com.mapia.result;

public class SignUpResult implements Result {
	private Status statusCode;
	private String errorMessage;
	
	private SignUpResult(Status statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}
	
	public Status getStatusCode() {
		return statusCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static SignUpResult ok() {
		return new SignUpResult(Status.Ok, null);
	}
	
	public static SignUpResult emailExist(String errorMessage) {
		return new SignUpResult(Status.EmailExists, errorMessage);
	}
	
	public static SignUpResult nicknameExist(String errorMessage) {
		return new SignUpResult(Status.NicknameExists, errorMessage);
	}
}
