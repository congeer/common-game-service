package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.Room;
import com.congeer.game.strategy.RoomEvent;
import com.congeer.game.strategy.model.LockData;

/**
 * 锁定座位
 */
public class LockSeatEvent extends RoomEvent<LockData> {

    @Override
    protected void handleData(LockData data) {
        Room room = getRoom();
        if (data.isAll() && room.getPlayer(socketId).isOwner()) {
            room.getPlayers().forEach(v -> v.setLock(true));
        } else {
            room.getPlayer(socketId).setLock(true);
        }
        updateRoom();
    }

}
