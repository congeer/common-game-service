package com.congeer.game.event.room;

import com.congeer.game.model.LockContext;
import com.congeer.game.bean.Room;
import com.congeer.game.event.RoomEvent;

/**
 * 锁定座位
 */
public class LockSeatEvent extends RoomEvent<LockContext> {

    @Override
    protected void handleRoom(LockContext context) {
        Room room = context.getRoom();
        String socketId = context.getSocketId();
        if (context.isAll() && room.getPlayer(socketId).isOwner()) {
            room.getPlayers().forEach(v -> v.setLock(true));
        } else {
            room.getPlayer(socketId).setLock(true);
        }
    }

}
