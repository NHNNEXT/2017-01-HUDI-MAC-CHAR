package com.mapia.domain;

import java.util.LinkedHashSet;
import java.util.Set;

public class Room {
	private Set<User> users = new LinkedHashSet<>();
	private long id;
	private String title;
	private final int CAPACITY = 8;
	private volatile int userCount;
	private boolean secretMode;

	public Room(long roomId, String title) {
		this.id = roomId;
		this.title = title;
	}

	public void enterRoom(User user){
        user.enterRoom();
		this.users.add(user);
	}
	
	public void outRoom(User user){
	    user.outRoom();
		this.users.remove(user);
	}

	public Set<User> getUsers() {
		return users;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getUserCount() {
		return getCountOfUserInRoom();
	}

	public boolean isSecretMode() {
		return secretMode;
	}

	public void setSecretMode() {
		this.secretMode = true;
	}

	public int getCountOfUserInRoom() {
		return users.size();
	}

	public boolean isFull() {
		return getCountOfUserInRoom() >= CAPACITY;
	}
	
	public boolean isEmpty() {
		return getCountOfUserInRoom() == 0;
	}

    @Override
    public String toString() {
        return "Room{" +
                "users=" + users +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", userCount=" + userCount +
                '}';
    }
}
