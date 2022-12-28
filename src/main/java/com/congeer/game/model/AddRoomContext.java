package com.congeer.game.model;


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

    public String getRoomId() {
        return roomId;
    }

    public AddRoomContext setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

}
