package com.congeer.game.strategy.model;

import io.vertx.core.json.JsonArray;

public class ConfigRoomData {

    private int maxPlayer;

    private JsonArray playerConfig;

    private JsonArray baseConfig;

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public ConfigRoomData setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
        return this;
    }

    public JsonArray getPlayerConfig() {
        return playerConfig;
    }

    public ConfigRoomData setPlayerConfig(JsonArray playerConfig) {
        this.playerConfig = playerConfig;
        return this;
    }

    public JsonArray getBaseConfig() {
        return baseConfig;
    }

    public ConfigRoomData setBaseConfig(JsonArray baseConfig) {
        this.baseConfig = baseConfig;
        return this;
    }

}
