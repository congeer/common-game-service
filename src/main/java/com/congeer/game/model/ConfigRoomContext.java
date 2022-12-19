package com.congeer.game.model;

import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public class ConfigRoomContext extends RoomContext {

    private int maxPlayer;

    private List<JsonObject> baseConfig;

    private List<List<JsonObject>> playerConfig;

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public ConfigRoomContext setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
        return this;
    }

    public List<JsonObject> getBaseConfig() {
        return baseConfig;
    }

    public ConfigRoomContext setBaseConfig(List<Map<String, Object>> baseConfig) {
        this.baseConfig = baseConfig.stream().map(JsonObject::new).toList();
        return this;
    }

    public List<List<JsonObject>> getPlayerConfig() {
        return playerConfig;
    }

    public ConfigRoomContext setPlayerConfig(List<List<Map<String, Object>>> playerConfig) {
        this.playerConfig = playerConfig.stream().map(v -> v.stream().map(JsonObject::new).toList()).toList();
        return this;
    }

}
