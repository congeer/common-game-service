package com.congeer.game.strategy;

import com.congeer.game.bean.Message;
import io.vertx.core.Handler;

public abstract class AbstractEvent implements Handler<io.vertx.core.eventbus.Message<Message>> {


    @Override
    public void handle(io.vertx.core.eventbus.Message<Message> event) {
        handle(event.body());
    }

    protected abstract void handle(Message body);

}
