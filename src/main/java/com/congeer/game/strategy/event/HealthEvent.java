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
        gameContext.getRoomBySocketId(body.getSocketId()).onSuccess(room -> {
            room.setLastUpdateTime(System.nanoTime());
            gameContext.setSocketAlive(body.getSocketId());
        });
    }

}
