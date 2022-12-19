package com.congeer.game.event.room;

import com.congeer.game.model.LockContext;
import com.congeer.game.bean.Room;
import com.congeer.game.event.RoomEvent;

/**
 * 解除座位锁定
 */
public class UnlockSeatEvent extends RoomEvent<LockContext> {

    @Override
    protected void handleRoom(LockContext context) {
        Room room = context.getRoom();
        String socketId = context.getSocketId();
        if (context.isAll() && room.getPlayer(socketId).isOwner()) {
            room.getPlayers().forEach(v -> v.setLock(false));
        } else {
            room.getPlayer(socketId).setLock(false);
        }
    }

}
