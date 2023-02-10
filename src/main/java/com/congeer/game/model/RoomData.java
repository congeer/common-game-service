package com.congeer.game.model;

import java.util.List;

public class RoomData {

    private String id;

    private String module;

    private List<String> tags;

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

    public String getModule() {
        return module;
    }

    public RoomData setModule(String module) {
        this.module = module;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public RoomData setTags(List<String> tags) {
        this.tags = tags;
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
