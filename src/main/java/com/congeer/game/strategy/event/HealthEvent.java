package com.congeer.game.strategy.event;

import com.congeer.game.strategy.RoomEvent;

/**
 * 健康检查
 */
public class HealthEvent extends RoomEvent<Void> {

    @Override
    protected void handleData(Void body) {
        updateRoom();
    }

}
