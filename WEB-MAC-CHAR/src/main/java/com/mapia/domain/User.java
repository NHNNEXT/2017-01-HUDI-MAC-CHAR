package com.mapia.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	// TODO @대훈 아래 4개의 상태 값을 enum으로 리팩토링한다.
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

	// TODO @대훈 User와 같은 클래스에 DB와 관련있는 ResultSet를 받는 것은 좋은 방법은 아님. 
	// 다음 구현 코드는 RowMapper가 담당하는 것이 맞음.
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
