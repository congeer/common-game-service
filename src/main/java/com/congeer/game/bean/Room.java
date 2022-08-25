package com.congeer.game.bean;


import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DataObject(generateConverter = true)
public class Room {

    public Room() {
    }

    public Room(String id) {
        this.id = id;
    }

    public Room(JsonObject json) {
        RoomConverter.fromJson(json, this);
    }


    private String id;

    private Player owner;

    private int maxPlayer = 2;

    private List<Player> players = new ArrayList<>();

    private List<Player> viewers = new ArrayList<>();

    private List<JsonObject> actionList = new ArrayList<>();

    private List<JsonObject> configList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public Room setId(String id) {
        this.id = id;
        return this;
    }

    public Player getOwner() {
        return owner;
    }

    public Room setOwner(Player owner) {
        this.owner = owner;
        return this;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public Room setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
        for (int i = players.size(); i < maxPlayer; i++) {
            players.add(new Player());
        }
        for (int i = maxPlayer; i < players.size(); i++) {
            players.remove(i);
        }
        return this;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Room setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    public List<Player> getViewers() {
        return viewers;
    }

    public Room setViewers(List<Player> viewers) {
        this.viewers = viewers;
        return this;
    }

    public List<JsonObject> getActionList() {
        return actionList;
    }

    public Room setActionList(List<JsonObject> actionList) {
        this.actionList = actionList;
        return this;
    }

    public List<JsonObject> getConfigList() {
        return configList;
    }

    public Room setConfigList(List<JsonObject> configList) {
        this.configList = configList;
        return this;
    }

    public List<Player> getAll() {
        List<Player> ret = new ArrayList<>();
        ret.addAll(players);
        ret.addAll(viewers);
        return ret;
    }

    public void reset() {
        this.actionList = new ArrayList<>();
    }

    public int playerLeave(String socketId) {
        int ret = 0;
        if (socketId.equals(owner.getSocketId())) {
            owner.setSocketId(null);
            ret += 1;
        }
        if (players.stream().anyMatch(v -> socketId.equals(v.getSocketId()))) {
            players.stream().filter(v -> socketId.equals(v.getSocketId())).forEach(Player::clearPlayer);
            ret += 2;
        } else if (viewers.stream().anyMatch(v -> v.getSocketId().equals(socketId))) {
            viewers = viewers.stream().filter(v -> !v.getSocketId().equals(socketId)).collect(Collectors.toList());
            ret += 4;
        }
        return ret;
    }

    public void clearConfig() {
        players.forEach(v -> v.setConfigList(new ArrayList<>()));
        configList = new ArrayList<>();
    }

}
