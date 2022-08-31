package com.congeer.game.strategy.model;

import lombok.Data;

public class PlayerData {

    private String id;

    private boolean isPlayer;

    private boolean isOwner;

    private boolean isLock;

    private int index = -1;

    public String getId() {
        return id;
    }

    public PlayerData setId(String id) {
        this.id = id;
        return this;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public PlayerData setPlayer(boolean player) {
        isPlayer = player;
        return this;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public PlayerData setOwner(boolean owner) {
        isOwner = owner;
        return this;
    }

    public boolean isLock() {
        return isLock;
    }

    public PlayerData setLock(boolean lock) {
        isLock = lock;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public PlayerData setIndex(int index) {
        this.index = index;
        return this;
    }

}
