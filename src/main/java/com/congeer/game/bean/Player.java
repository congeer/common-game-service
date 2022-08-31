package com.congeer.game.bean;

import com.congeer.game.strategy.model.PlayerData;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player {

    public Player() {
    }

    public Player(String id, String socketId) {
        this.id = id;
        this.socketId = socketId;
    }

    private String id;

    private String socketId;

    private boolean player;

    private boolean owner;

    private boolean lock;

    private int index = -1;

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

    public boolean isPlayer() {
        return player;
    }

    public Player setPlayer(boolean player) {
        this.player = player;
        return this;
    }

    public boolean isOwner() {
        return owner;
    }

    public Player setOwner(boolean owner) {
        this.owner = owner;
        return this;
    }

    public boolean isLock() {
        return lock;
    }

    public Player setLock(boolean lock) {
        this.lock = lock;
        return this;
    }

    public Player setConfigList(List<JsonObject> configList) {
        this.configList = configList;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public Player setIndex(int index) {
        this.index = index;
        return this;
    }

    public List<JsonObject> getConfigList() {
        return configList;
    }

    public Player resetConfig() {
        this.configList = new CopyOnWriteArrayList<>();
        return this;
    }

    public void clearPlayer() {
        this.socketId = null;
        if (!lock) {
            this.id = null;
        }
    }

    public PlayerData baseInfo() {
        return new PlayerData().setId(id).setOnline(socketId != null).setLock(lock).setOwner(owner).setPlayer(player)
            .setIndex(index);
    }

}
