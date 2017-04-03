package com.mapia.domain;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class Room {
	private long id;
	private String title;
	private Set<User> users = new LinkedHashSet<>();
	private LocalDateTime madeTime;
	private int userCount = 0;
	private int status;
	private final int CAPACITY = 8;
	
	public Room() {}

	public Room(String title) {
		this.id = createRoomId();
		this.title = title;
	}
	
	public Room(long roomId, String title) {
		this.id = roomId;
		this.title = title;
	}
	
	public User enterRoom(User user){
		this.users.add(user);
		++userCount;
		return user;
	}
	
	public User outRoom(User user){
		this.users.remove(user);
		--userCount;
		return user;
	}

	public int getUserCount() {
		return this.userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public Set<User> getUserList() {
		return users;
	}

	public void setUserList(Set<User> userList) {
		this.users = userList;
	}

	public LocalDateTime getMadeTime() {
		return madeTime;
	}

	public void setMadeTime(LocalDateTime madeTime) {
		this.madeTime = madeTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private long createRoomId() {
		String token = StringUtils.remove(String.valueOf(UUID.randomUUID()), "-");
		return Long.parseLong(token);
	}

	public int getCountOfUserInRoom() {
		return users.size();
	}

	public boolean isAvailableEnter() {
		return getCountOfUserInRoom() <= CAPACITY;
	}
	
}
