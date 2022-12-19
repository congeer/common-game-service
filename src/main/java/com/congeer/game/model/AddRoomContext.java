package com.congeer.game.model;


import com.congeer.game.Application;
import com.congeer.game.bean.Room;

public class AddRoomContext extends RoomContext {

    private String playerId;

    private String roomId;

    public String getPlayerId() {
        return playerId;
    }

    public AddRoomContext setPlayerId(String playerId) {
        this.playerId = playerId;
        return this;
    }

    public Room getRoom() {
        if (room == null) {
            room = Application.getGame().getRoom(roomId);
        }
        return room;
    }

    public String getRoomId() {
        return roomId;
    }

    public AddRoomContext setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

}
