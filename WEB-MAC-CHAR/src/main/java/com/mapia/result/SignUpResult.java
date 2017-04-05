package com.mapia.result;

public class SignUpResult implements Result {
	private Status status;
	private String msg;
	
	private SignUpResult(Status status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public static SignUpResult ok() {
		return new SignUpResult(Status.Ok, null);
	}
	
	public static SignUpResult emailExist(String msg) {
		return new SignUpResult(Status.EmailExists, msg);
	}
	
	public static SignUpResult nicknameExist(String msg) {
		return new SignUpResult(Status.NicknameExists, msg);
	}
}
