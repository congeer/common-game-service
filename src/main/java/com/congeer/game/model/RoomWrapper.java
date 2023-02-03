package com.congeer.game.model;

import com.congeer.game.bean.Room;

public class RoomWrapper {

    private Room room;

    private Integer count;

    public Room getRoom() {
        return room;
    }

    public RoomWrapper setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public RoomWrapper setCount(Integer count) {
        this.count = count;
        return this;
    }

}
