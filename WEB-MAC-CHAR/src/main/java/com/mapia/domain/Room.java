package com.mapia.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.format.datetime.joda.LocalDateTimeParser;

import com.mapia.model.User;

public class Room {
	private long id;
	
	private String title;
	
	private Set<User> users = new HashSet<>();
	
	private LocalDateTime madeTime;
	
	private int status;
	
	public Room() {}
	
	public Room(long roomId, String title) {
		this.id = roomId;
		this.title = title;
	}
	
	public User enterRoom(User user){
		this.users.add(user);
		return user;
	}
	
	public User outRoom(User user){
		this.users.remove(user);
		return user;
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

	public void setUserList(HashSet<User> userList) {
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
	
}
