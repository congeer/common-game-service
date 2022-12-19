package com.congeer.game.model;

public class RoomStringContext extends RoomContext implements StringContext {

    private String data;

    public String getData() {
        return data;
    }

    public RoomStringContext setData(String data) {
        this.data = data;
        return this;
    }

}
