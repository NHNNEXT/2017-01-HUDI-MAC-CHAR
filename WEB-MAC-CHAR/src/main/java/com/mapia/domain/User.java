package com.mapia.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class User {
    private enum Status {
        LOBBY, ENTERED, READY, NOT_READY, IN_GAME
    }

	private long id;
	private String email;
	private String password;
	private String nickname;
	private Status status;
    public long belongToRoomId;

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

	public long getBelongToRoomId() {
        return this.belongToRoomId;
    }

    public void setBelongToRoomId(long belongToRoomId) {
        this.belongToRoomId = belongToRoomId;
    }
	
	public boolean matchPassword(User user) {
		return this.password.equals(user.getPassword());
	}

	public boolean isLobby() {
        return this.status.equals(Status.LOBBY);
    }

	public boolean isAbleToEnter(long id) {
	    return (this.status.equals(Status.LOBBY) || belongToRoomId == id);
    }

	public void enterRoom(Room room) {
		Set<User> users = room.getUsers();
		users.add(this);
		this.belongToRoomId = room.getId();
	    this.status = Status.ENTERED;
    }

    public void exitRoom(Room room) {
		Set<User> users = room.getUsers();
		users.remove(this);
		this.belongToRoomId = 0;
		this.status = Status.LOBBY;
	}

    public void enterLobby() {
        this.belongToRoomId = 0;
        this.status = Status.LOBBY;
    }

	@Override
	public String toString() {
		return "User[nickname=" + nickname +
                ", Status=" + status +
                ", belongToRoomId=" + belongToRoomId;
	}
}
