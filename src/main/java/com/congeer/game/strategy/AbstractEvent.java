package com.congeer.game.strategy;

import com.congeer.game.bean.BaseMessage;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractEvent<T> implements Handler<Message<BaseMessage>> {

    protected String socketId;

    @Override
    public void handle(Message<BaseMessage> event) {
        BaseMessage body = event.body();
        if (!body.getType().equals("h")) {
            System.out.println("RECEIVE_FROM_CLIENT: " + body);
        }
        socketId = body.getSocketId();
        T data = body.getData(getType());
        handleData(data);
    }

    public Class<T> getType() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            return (Class<T>) actualTypeArguments[0];
        }
        throw new IllegalArgumentException("No Type");
    }

    protected abstract void handleData(T data);

}
