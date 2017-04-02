package com.mapia.utils;

public interface Result {
	public enum Status {
		Ok, EmailNotFound, InvalidPassword, EmailExists, NicknameExists;
	}
}
