package com.congeer.game.bean;


import com.congeer.game.model.RoomData;
import io.vertx.core.json.JsonObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public Room(String id) {
        this.id = id;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    // 房间id
    private final String id;

    // 房间标签
    private String tag;

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

    // 房间座位列表
    private final List<Player> seats = new CopyOnWriteArrayList<>();

    // 房间观众列表
    private final List<Player> viewers = new CopyOnWriteArrayList<>();

    // 存储的帧列表
    private List<String> frameList = new CopyOnWriteArrayList<>();

    // 存储的设置列表
    private List<String> configList = new CopyOnWriteArrayList<>();

    /**
     * 设置房间的初始化玩家信息
     */
    public void configPlayer() {
        seats.removeIf(next -> next.getIndex() >= maxPlayer);
        for (int i = seats.size(); i < maxPlayer; i++) {
            Player player = new Player();
            player.setPlayer(true);
            player.setIndex(i);
            player.setWhere(this);
            seats.add(player);
        }
    }

    /**
     * 获取房间的一个玩家
     *
     * @param socketId
     * @return
     */
    public Player getPlayer(String socketId) {
        Player viewer = viewers.stream().filter(v -> socketId.equals(v.getSocketId())).findFirst().orElse(null);
        return seats.stream().filter(v -> socketId.equals(v.getSocketId())).findFirst().orElse(viewer);
    }

    /**
     * 获取一个空位置
     *
     * @param playerId
     * @return
     */
    public Player getEmptySeat(String playerId) {
        Optional<Player> has = getPlayers().stream().filter(v -> playerId.equals(v.getId())).findFirst();
        if (has.isPresent()) {
            return has.get();
        }
        Optional<Player> first = getPlayers().stream().filter(v -> (!v.isLock() && v.getSocketId() == null)
            || v.getId() == null).findFirst();
        return first.orElse(null);
    }

    /**
     * 重制房间帧
     *
     * @return
     */
    public Room resetFrameList() {
        this.frameList = new CopyOnWriteArrayList<>();
        return this;
    }

    public List<Player> allPlayer() {
        List<Player> ret = new CopyOnWriteArrayList<>();
        ret.addAll(seats);
        ret.addAll(viewers);
        return ret;
    }

    /**
     * 玩家离开房间
     *
     * @param socketId
     * @return
     */
    public Player playerLeave(String socketId) {
        Optional<Player> player = seats.stream().filter(v -> socketId.equals(v.getSocketId())).findFirst();
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

    /**
     * 清除配置
     */
    public void clearConfig() {
        seats.forEach(Player::resetConfig);
        configList = new CopyOnWriteArrayList<>();
        config = false;
    }

    public RoomData baseInfo() {
        long count = seats.stream().filter(v -> v.getSocketId() != null).count();
        RoomData room = new RoomData().setId(id).setMaxPlayer(maxPlayer).setPlayerCount((int) count).setTag(tag);
        room.setPlayers(seats.stream().map(Player::baseInfo).toList());
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
        return resetFrameList().resetSeed();
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public Room setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getOwner() {
        if (owner != null) {
            return owner.getId();
        } else {
            return null;
        }
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
        return seats;
    }

    public List<Player> getViewers() {
        return viewers;
    }


    public List<String> getFrameList() {
        return frameList;
    }

    public void appendFrame(String frame) {
        frameList.add(frame);
    }


    public List<String> getConfigList() {
        return configList;
    }

    public boolean expire() {
        return lastUpdateTime + 12 * 60 * 60 * 1000 < System.currentTimeMillis();
    }

}
