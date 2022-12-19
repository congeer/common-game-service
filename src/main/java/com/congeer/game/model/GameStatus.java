package com.congeer.game.model;

import com.congeer.game.bean.Room;

import java.util.List;

public class GameStatus {

    private int roomCount;

    private int socketCount;

    private List<Room> roomList;

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

    public List<Room> getRoomList() {
        return roomList;
    }

    public GameStatus setRoomList(List<Room> roomList) {
        this.roomList = roomList;
        return this;
    }

}
