package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.model.LockData;

/**
 * 解除座位锁定
 */
public class UnlockSeatEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        String socketId = body.getSocketId();
        LockData data = body.getData(LockData.class);
        gameContext.getRoomBySocketId(socketId).onSuccess(room -> {
            if (data.isAll() && room.getPlayer(socketId).isOwner()) {
                room.getPlayers().forEach(v -> v.setLock(false));
            } else {
                room.getPlayer(socketId).setLock(false);
            }
        });
    }

}
