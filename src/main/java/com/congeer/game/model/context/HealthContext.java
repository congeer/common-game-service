package com.congeer.game.model.context;


public class HealthContext extends EventContext {

    private String playerId;

    private String roomId;

    public String getPlayerId() {
        return playerId;
    }

    public HealthContext setPlayerId(String playerId) {
        this.playerId = playerId;
        return this;
    }

    public String getRoomId() {
        return roomId;
    }

    public HealthContext setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

}
