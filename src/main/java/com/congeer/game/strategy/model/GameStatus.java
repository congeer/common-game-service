package com.congeer.game.strategy.model;

public class GameStatus {

    private int roomCount;

    private int socketCount;

    public int getRoomCount() {
        return roomCount;
    }

    public GameStatus setRoomCount(int roomCount) {
        this.roomCount = roomCount;
        return this;
    }

    public int getSocketCount() {
        return socketCount;
    }

    public GameStatus setSocketCount(int socketCount) {
        this.socketCount = socketCount;
        return this;
    }

}
