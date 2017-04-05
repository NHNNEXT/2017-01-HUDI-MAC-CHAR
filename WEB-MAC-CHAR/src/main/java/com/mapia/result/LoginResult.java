package com.mapia.result;

// TODO @대훈 LoginResult와 SignUpResult에 상당히 많은 중복 코드 있음. AbstractResult와 같은 클래스 생성해 중복 제거한다.
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
