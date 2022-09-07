package com.congeer.game.strategy;

import com.congeer.game.bean.Message;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public abstract class AbstractEvent implements Handler<io.vertx.core.eventbus.Message<Message>> {

    @Override
    public void handle(io.vertx.core.eventbus.Message<Message> event) {
        Message body = event.body();
        if (!body.getType().equals("h")) {
            JsonObject log = new JsonObject();
            log.put("time", LocalDateTime.now());
            log.put("type", "RECEIVE_FROM_CLIENT");
            log.put("data", body);
            System.out.println(log);
        }
        handle(body);
    }

    protected abstract void handle(Message body);

}
