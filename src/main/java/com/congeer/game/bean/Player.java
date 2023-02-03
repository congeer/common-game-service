package com.congeer.game.bean;

import com.congeer.game.model.PlayerData;
import io.vertx.core.json.JsonObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public Player() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public Player(String id, String socketId) {
        this.id = id;
        this.socketId = socketId;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    private String id;

    private String socketId;

    private boolean player;

    private boolean owner;

    private boolean lock;

    private int index = -1;

    // 最后更新时间
    private long lastUpdateTime;

    private Room where;

    private List<String> configList = new ArrayList<>();

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

    public Player setConfigList(List<String> configList) {
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

    public List<String> getConfigList() {
        return configList;
    }

    public Player resetConfig() {
        this.configList = new CopyOnWriteArrayList<>();
        return this;
    }

    public void clearPlayer() {
        this.socketId = null;
        this.lastUpdateTime = -1;
        if (!lock) {
            this.id = null;
        }
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Player setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public String getWhere() {
        if (where != null) {
            return where.getId();
        } else {
            return null;
        }
    }

    public Player setWhere(Room where) {
        this.where = where;
        return this;
    }

    public PlayerData baseInfo() {
        return new PlayerData().setId(id).setOnline(socketId != null).setLock(lock).setOwner(owner).setPlayer(player)
            .setIndex(index).setConfigList(configList);
    }

    public boolean expire() {
        return lastUpdateTime + 5 * 60 * 1000 < System.currentTimeMillis();
    }

}
