package com.mapia.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private long id;
	private String email;
	private String password;
	private String nickname;
	
	public User(){} // default constructor

	public User(ResultSet rs) throws SQLException {
		this.id = rs.getLong("id");
		this.email = rs.getString("email");
		this.password = rs.getString("password");
		this.nickname = rs.getString("nickname");
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public boolean matchPassword(User user) {
		return this.password.equals(user.getPassword());
	}

	@Override
	public String toString() {
		return "User [userId=" + id + ", email=" + email + ", password=" + password + ", nickname=" + nickname
				+ "]";
	}
}
