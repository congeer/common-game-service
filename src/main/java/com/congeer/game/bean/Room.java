package com.congeer.game.bean;


import com.congeer.game.strategy.model.RoomData;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {

    public Room(String id) {
        this.id = id;
        this.lastUpdateTime = System.nanoTime();
    }

    private final String id;

    private Player owner;

    private int maxPlayer = 2;

    private long lastUpdateTime;

    private boolean config;

    private final List<Player> players = new CopyOnWriteArrayList<>();

    private final List<Player> viewers = new CopyOnWriteArrayList<>();

    private List<JsonObject> actionList = new CopyOnWriteArrayList<>();

    private List<JsonObject> configList = new CopyOnWriteArrayList<>();

    public String getId() {
        return id;
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
        return this;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Room setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public boolean isConfig() {
        return config;
    }

    public Room setConfig(boolean config) {
        this.config = config;
        return this;
    }

    /**
     * 设置房间的初始化玩家信息
     */
    public void configPlayer() {
        players.removeIf(next -> next.getIndex() >= maxPlayer);
        for (int i = players.size(); i < maxPlayer; i++) {
            Player player = new Player();
            player.setPlayer(true);
            player.setIndex(i);
            players.add(player);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String socketId) {
        return players.stream().filter(v -> socketId.equals(v.getSocketId())).findFirst().orElse(null);
    }

    public List<Player> getViewers() {
        return viewers;
    }


    public List<JsonObject> getActionList() {
        return actionList;
    }

    public Room resetActionList() {
        this.actionList = new CopyOnWriteArrayList<>();
        return this;
    }

    public List<JsonObject> getConfigList() {
        return configList;
    }

    public List<Player> allPlayer() {
        List<Player> ret = new CopyOnWriteArrayList<>();
        ret.addAll(players);
        ret.addAll(viewers);
        return ret;
    }

    public void reset() {
        this.actionList = new CopyOnWriteArrayList<>();
    }

    public Player playerLeave(String socketId) {
        Optional<Player> player = players.stream().filter(v -> socketId.equals(v.getSocketId())).findFirst();
        Optional<Player> viewer = viewers.stream().filter(v -> socketId.equals(v.getSocketId())).findFirst();
        if (player.isPresent()) {
            player.get().clearPlayer();
            return player.get();
        } else if (viewer.isPresent()) {
            viewers.remove(viewer.get());
            return viewer.get();
        }
        return null;
    }

    public void clearConfig() {
        players.forEach(Player::resetConfig);
        configList = new CopyOnWriteArrayList<>();
        config = false;
    }

    public RoomData baseInfo() {
        long count = players.stream().filter(v -> v.getSocketId() != null).count();
        RoomData room = new RoomData().setId(id).setMaxPlayer(maxPlayer).setPlayerCount((int) count);
        room.setPlayers(players.stream().map(Player::baseInfo).toList());
        return room;
    }

}
