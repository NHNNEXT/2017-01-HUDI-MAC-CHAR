package com.mapia.result;

public class LoginResult implements Result {
	private Status status;
	private String msg;
	
	private LoginResult(Status status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public static LoginResult ok() {
		return new LoginResult(Status.Ok, null);
	}
	
	public static LoginResult emailNotFound(String msg) {
		return new LoginResult(Status.EmailNotFound, msg);
	}
	
	public static LoginResult invalidPassword(String msg) {
		return new LoginResult(Status.InvalidPassword, msg);
	}
}
