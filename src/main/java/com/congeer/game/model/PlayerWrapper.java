package com.congeer.game.model;

import com.congeer.game.bean.Player;

public class PlayerWrapper {

    private Player player;

    private Integer count;

    public Player getPlayer() {
        return player;
    }

    public PlayerWrapper setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public PlayerWrapper setCount(Integer count) {
        this.count = count;
        return this;
    }

}
