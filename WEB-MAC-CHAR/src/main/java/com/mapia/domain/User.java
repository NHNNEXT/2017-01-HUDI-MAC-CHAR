package com.mapia.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private final String LOBBY = "lobby";
    private final String ENTERED = "entered";
    private final String READY = "ready";
    private final String NOT_READY = "notReady";
	private long id;
	private String email;
	private String password;
	private String nickname;
	private String status;
	
	public User(){}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean matchPassword(User user) {
		return this.password.equals(user.getPassword());
	}

	public boolean isEntered() {
	    return this.status == LOBBY;
    }

	public void enterRoom() {
	    this.status = ENTERED;
    }

    public void outRoom() {
	    this.status = LOBBY;
    }

    public void enterLobby() {
	    this.status = LOBBY;
    }

	@Override
	public String toString() {
		return "User[nickname=" + nickname + "]";
	}
}
