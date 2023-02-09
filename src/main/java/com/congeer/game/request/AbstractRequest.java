package com.congeer.game.request;

import com.congeer.game.bean.Result;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public abstract class AbstractRequest<T, R> implements Handler<Message<T>> {

    @Override
    public void handle(Message<T> event) {
        T body = event.body();
        R resp = handleBody(body);
        event.reply(new Result<>(resp));
    }

    protected abstract R handleBody(T body);

}
