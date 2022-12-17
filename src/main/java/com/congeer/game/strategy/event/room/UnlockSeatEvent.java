package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.Room;
import com.congeer.game.strategy.RoomEvent;
import com.congeer.game.strategy.model.LockData;

/**
 * 解除座位锁定
 */
public class UnlockSeatEvent extends RoomEvent<LockData> {

    @Override
    protected void handleData(LockData data) {
        Room room = getRoom();
        if (data.isAll() && room.getPlayer(socketId).isOwner()) {
            room.getPlayers().forEach(v -> v.setLock(false));
        } else {
            room.getPlayer(socketId).setLock(false);
        }
        updateRoom();
    }

}
