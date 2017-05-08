package com.mapia.result;

/**
 * Created by Jbee on 2017. 4. 9..
 */
public class RoomResult {
    public enum Status {
        OK, ENTER_FAILED
    }

    private long roomId;
    private Status status;
    private String msg;

    public RoomResult(Status status, String msg, long roomId) {
        this.status = status;
        this.msg = msg;
        this.roomId = roomId;
    }

    public long getRoomId() {
        return roomId;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public static RoomResult invalidAccess(long roomId) {
        return new RoomResult(Status.ENTER_FAILED, "Invalid Access", roomId);
    }

    public static RoomResult rejectToEnterRoomOfFull(long roomId) {
        return new RoomResult(Status.ENTER_FAILED, "The room is full", roomId);
    }
    
    public static RoomResult successToEnterRoom(long roomId) {
        return new RoomResult(Status.OK, "success to enter the room", roomId);
    }

    public static RoomResult successToCreateRoom(long roomId) {
        return new RoomResult(Status.OK, "success to create the room", roomId);
    }

    public static RoomResult successToOutRoom(long roomId) {
        return new RoomResult(Status.OK, "success to out the room", roomId);
    }
}
