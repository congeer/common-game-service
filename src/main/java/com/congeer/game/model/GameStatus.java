package com.congeer.game.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;

import java.util.List;

public class GameStatus {

    private int roomCount;

    private int playerCount;

    private List<Room> roomList;

    private List<Player> playerList;

    public int getRoomCount() {
        return roomCount;
    }

    public GameStatus setRoomCount(int roomCount) {
        this.roomCount = roomCount;
        return this;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public GameStatus setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public GameStatus setRoomList(List<Room> roomList) {
        this.roomList = roomList;
        return this;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public GameStatus setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
        return this;
    }

}
