package com.congeer.game.model;

import java.util.List;

public class RoomData {

    private String id;

    private String tag;

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

    public String getTag() {
        return tag;
    }

    public RoomData setTag(String tag) {
        this.tag = tag;
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
