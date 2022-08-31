package com.congeer.game.strategy.model;


public class AddRoomData {

    private String playerId;

    private String roomId;

    public String getPlayerId() {
        return playerId;
    }

    public AddRoomData setPlayerId(String playerId) {
        this.playerId = playerId;
        return this;
    }

    public String getRoomId() {
        return roomId;
    }

    public AddRoomData setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

}
