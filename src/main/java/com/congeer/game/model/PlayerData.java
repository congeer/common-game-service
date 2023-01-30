package com.congeer.game.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    private String id;

    private boolean player;

    private boolean owner;

    private boolean lock;

    private boolean create;

    private boolean online;

    private int index = -1;

    private List<String> configList;

    public String getId() {
        return id;
    }

    public PlayerData setId(String id) {
        this.id = id;
        return this;
    }

    public boolean isPlayer() {
        return player;
    }

    public PlayerData setPlayer(boolean player) {
        this.player = player;
        return this;
    }

    public boolean isOwner() {
        return owner;
    }

    public PlayerData setOwner(boolean owner) {
        this.owner = owner;
        return this;
    }

    public boolean isLock() {
        return lock;
    }

    public PlayerData setLock(boolean lock) {
        this.lock = lock;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public PlayerData setIndex(int index) {
        this.index = index;
        return this;
    }

    public boolean isCreate() {
        return create;
    }

    public PlayerData setCreate(boolean create) {
        this.create = create;
        return this;
    }

    public boolean isOnline() {
        return online;
    }

    public PlayerData setOnline(boolean online) {
        this.online = online;
        return this;
    }

    public List<String> getConfigList() {
        return configList;
    }

    public PlayerData setConfigList(List<String> configList) {
        this.configList = configList;
        return this;
    }

}
