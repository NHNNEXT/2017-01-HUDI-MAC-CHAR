package com.mapia.result;

public interface Result {
	public enum Status {
		Ok, EmailNotFound, InvalidPassword, EmailExists, NicknameExists;
	}
}
