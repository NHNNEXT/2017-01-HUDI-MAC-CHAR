package com.mapia.domain;

import org.springframework.stereotype.Component;

import com.mapia.domain.User.Status;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Jbee on 2017. 4. 5..
 */
@Component
public class Lobby implements Iterable {
    private volatile Map<Long, Room> rooms = new ConcurrentHashMap<>();
    private final AtomicLong roomIdentifier = new AtomicLong();


    public Lobby(){
        //Test data(1 room, 5 users)
    	Room testRoom = getRoom(createRoom("test"));
    	testRoom.enter(new User(100, "a@a.com", "testUser1"));
    	testRoom.enter(new User(100, "b@b.com", "testUser2"));
    	testRoom.enter(new User(100, "c@c.com", "testUser3"));
    	testRoom.enter(new User(100, "d@d.com", "testUser4"));
    	testRoom.enter(new User(100, "e@e.com", "testUser5"));
    	testRoom.getUsers().forEach(user -> user.setStatus(Status.READY));
    }
    
    public Collection<Room> rooms() {
        return rooms.values();
    }

    public Room getRoom(long id) {
        return rooms.get(id);
    }

    public long createRoom(String title) {
        long roomId = roomIdentifier.incrementAndGet();
        rooms.put(roomId, new Room(roomId, title));
        return roomId;
    }

    public void delRoom(long id) {
        rooms.remove(id);
    }

    public boolean isExistRoom(long id) {
        return rooms.keySet().contains(id);
    }

    @Override
    public Iterator iterator() {
        return rooms.values().iterator();
    }
}
