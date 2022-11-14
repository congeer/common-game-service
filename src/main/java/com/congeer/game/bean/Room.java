package com.congeer.game.bean;


import com.congeer.game.strategy.model.RoomData;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {

    public Room(String id) {
        this.id = id;
        this.lastUpdateTime = System.nanoTime();
    }

    // 房间id
    private final String id;

    // 房间拥有人
    private Player owner;

    // 房间最大玩家数
    private int maxPlayer = 2;

    // 最后更新时间
    private long lastUpdateTime;

    // 房间是否已经被配置
    private boolean config;

    // 房间随机数种子
    private Map<String, List<Integer>> seedMap = new ConcurrentHashMap<>();

    // 房间玩家列表
    private final List<Player> players = new CopyOnWriteArrayList<>();

    // 房间观众列表
    private final List<Player> viewers = new CopyOnWriteArrayList<>();

    // 存储的帧列表
    private List<JsonObject> frameList = new CopyOnWriteArrayList<>();

    // 存储的设置列表
    private List<JsonObject> configList = new CopyOnWriteArrayList<>();

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


    public Player getPlayer(String socketId) {
        return players.stream().filter(v -> socketId.equals(v.getSocketId())).findFirst().orElse(null);
    }

    public Room resetActionList() {
        this.frameList = new CopyOnWriteArrayList<>();
        return this;
    }

    public List<Player> allPlayer() {
        List<Player> ret = new CopyOnWriteArrayList<>();
        ret.addAll(players);
        ret.addAll(viewers);
        return ret;
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

    public List<Integer> getSeed(String key) {
        return seedMap.get(key);
    }

    public Integer pullSeed(String key) {
        return seedMap.get(key).remove(0);
    }

    public Room setSeed(String key, List<Integer> source) {
        List<Integer> seed = new ArrayList<>(source);
        seedMap.put(key, seed);
        return this;
    }

    public Room resetSeed() {
        this.seedMap = new HashMap<>();
        return this;
    }

    public Room resetRoom() {
        return resetActionList().resetSeed();
    }

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

    public Map<String, List<Integer>> getSeedMap() {
        return seedMap;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getViewers() {
        return viewers;
    }


    public List<JsonObject> getFrameList() {
        return frameList;
    }


    public List<JsonObject> getConfigList() {
        return configList;
    }

}
