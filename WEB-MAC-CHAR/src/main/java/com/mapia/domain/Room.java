package com.mapia.domain;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.format.datetime.joda.LocalDateTimeParser;

public class Room {
	private Long id;
	
	private String title;
	
	private HashSet<User> userList;
	
	private LocalDateTime madeTime;
	
	private int status;
	
	public Room() {}
	
	public Room(Long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public User enterRoom(User user){
		this.userList.add(user);
		return user;
	}
	
	public User outRoom(User user){
		this.userList.remove(user);
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public HashSet<User> getUserList() {
		return userList;
	}

	public void setUserList(HashSet<User> userList) {
		this.userList = userList;
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
