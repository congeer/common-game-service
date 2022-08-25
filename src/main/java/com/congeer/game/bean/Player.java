package com.congeer.game.bean;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

@DataObject(generateConverter = true)
public class Player {

    public Player() {
    }

    public Player(String id, String socketId) {
        this.id = id;
        this.socketId = socketId;
    }

    public Player(JsonObject json) {
        PlayerConverter.fromJson(json, this);
    }

    private String id;

    private String socketId;

    private List<JsonObject> configList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public Player setId(String id) {
        this.id = id;
        return this;
    }

    public String getSocketId() {
        return socketId;
    }

    public Player setSocketId(String socketId) {
        this.socketId = socketId;
        return this;
    }

    public List<JsonObject> getConfigList() {
        return configList;
    }

    public Player setConfigList(List<JsonObject> configList) {
        this.configList = configList;
        return this;
    }

    public void clearPlayer() {
        this.socketId = null;
    }

}
