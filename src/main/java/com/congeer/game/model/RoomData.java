package com.congeer.game.model;

import java.util.List;

public class RoomData {

    private String id;

    private Integer maxPlayer;

    private Integer playerCount;

    private List<PlayerData> players;

    public String getId() {
        return id;
    }

    public RoomData setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public RoomData setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
        return this;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public RoomData setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }

    public RoomData setPlayers(List<PlayerData> players) {
        this.players = players;
        return this;
    }

}
