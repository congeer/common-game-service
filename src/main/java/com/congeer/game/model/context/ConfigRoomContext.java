package com.congeer.game.model.context;

import java.util.List;

public class ConfigRoomContext extends RoomContext {

    private int maxPlayer;

    private String module;

    private List<String> tags;

    private List<String> baseConfig;

    private List<List<String>> playerConfig;

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public ConfigRoomContext setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
        return this;
    }

    public String getModule() {
        return module;
    }

    public ConfigRoomContext setModule(String module) {
        this.module = module;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public ConfigRoomContext setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public List<String> getBaseConfig() {
        return baseConfig;
    }

    public ConfigRoomContext setBaseConfig(List<String> baseConfig) {
        this.baseConfig = baseConfig;
        return this;
    }

    public List<List<String>> getPlayerConfig() {
        return playerConfig;
    }

    public ConfigRoomContext setPlayerConfig(List<List<String>> playerConfig) {
        this.playerConfig = playerConfig;
        return this;
    }

}
