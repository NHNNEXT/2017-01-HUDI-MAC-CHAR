package com.mapia.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public enum Status {
        LOBBY, READY, NOT_READY, IN_GAME;

		public boolean isLobby() {
        	return this == Status.LOBBY;
		}
    }

	private long id;
	private String email;
	private String password;
	private String nickname;
	private Status status;
    private long enteredRoomId;
    private long LOBBY_ID = 0;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getEnteredRoomId() {
        return this.enteredRoomId;
    }

    public void setEnteredRoomId(long enteredRoomId) {
        this.enteredRoomId = enteredRoomId;
    }
	
	public boolean matchPassword(User user) {
		return this.password.equals(user.password);
	}

	public boolean isAbleToEnter(long id) {
	    return this.status == Status.LOBBY || enteredRoomId == id;
    }

    public boolean isLobby() {
    	return this.status.isLobby();
	}

	public void enterLobby() {
		this.enteredRoomId = LOBBY_ID;
		this.status = Status.LOBBY;
	}

	public void enterRoom(long id) {
		this.enteredRoomId = id;
	    this.status = Status.READY;
    }

	@Override
	public String toString() {
		return "User[nickname=" + nickname +
                ", Status=" + status +
                ", enteredRoomId=" + enteredRoomId;
	}
}
