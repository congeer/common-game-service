package com.congeer.game.event.base;

import com.congeer.game.Application;
import com.congeer.game.event.AbstractEvent;
import com.congeer.game.model.context.HealthContext;

/**
 * 健康检查
 */
public class HealthEvent extends AbstractEvent<HealthContext> {

    @Override
    protected void handleData(HealthContext body) {
        Application.getGame().setSocketAlive(body.getSocketId());
    }

}
