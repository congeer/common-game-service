package com.congeer.game.request;

import com.congeer.game.bean.Result;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public abstract class AbstractRequest<T, R> implements Handler<Message<T>> {

    @Override
    public void handle(Message<T> event) {
        T body = event.body();
        event.reply(handleBody(body));
    }

    protected abstract Result<R> handleBody(T body);

}
