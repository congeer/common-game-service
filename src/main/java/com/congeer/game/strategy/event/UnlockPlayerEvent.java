package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.model.LockData;

public class UnlockPlayerEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        String socketId = body.getSocketId();
        LockData data = body.getData(LockData.class);
        Room room = gameContext.getRoomBySocketId(socketId);
        if (data.isAll() && room.getPlayer(socketId).isOwner()) {
            room.getPlayers().forEach(v -> v.setLock(false));
        } else {
            room.getPlayer(socketId).setLock(false);
        }
    }

}