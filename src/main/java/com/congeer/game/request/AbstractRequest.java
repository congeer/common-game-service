package com.congeer.game.request;

import com.congeer.game.bean.Result;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public abstract class AbstractRequest<T, R> implements Handler<Message<T>> {

    @Override
    public void handle(Message<T> event) {
        T body = event.body();
        handleBody(body).onComplete(resp -> {
            if (resp.succeeded()) {
                event.reply(new Result<>(resp.result()));
            } else {
                event.reply(new Result<>("400", resp.cause().getMessage()));
            }
        });
    }

    protected abstract Future<R> handleBody(T body);

}
