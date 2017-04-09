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
	
	// 모바일에서 필요한 nickname을 일단 msg에 담아 전달. User 객체를 전달하는 게 더 좋을지도 모름.
	public static LoginResult ok(String msg) {
		return new LoginResult(Status.Ok, msg);
	}
	
	public static LoginResult emailNotFound(String msg) {
		return new LoginResult(Status.EmailNotFound, msg);
	}
	
	public static LoginResult invalidPassword(String msg) {
		return new LoginResult(Status.InvalidPassword, msg);
	}
}
