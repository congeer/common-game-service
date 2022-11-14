package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;

/**
 * 健康检查
 */
public class HealthEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        Room room = gameContext.getRoomBySocketId(body.getSocketId());
        room.setLastUpdateTime(System.nanoTime());
        gameContext.setSocketAlive(body.getSocketId());
    }

}
