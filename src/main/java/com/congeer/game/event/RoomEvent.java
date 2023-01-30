package com.congeer.game.event;

import com.congeer.game.Application;
import com.congeer.game.bean.Room;
import com.congeer.game.model.RoomContext;
import io.vertx.core.Future;

public abstract class RoomEvent<T extends RoomContext> extends AbstractEvent<T> {

    @Override
    protected void handleData(T context) {
        getRoom(context).compose(room -> {
            context.setRoom(room);
            return Future.succeededFuture(context);
        }).compose(c -> {
            handleRoom(context);
            return Future.succeededFuture(context);
        }).onSuccess(RoomContext::updateRoom).recover(throwable -> {
            handleEmpty(context);
            return Future.succeededFuture(context);
        });
    }

    protected Future<Room> getRoom(T context) {
        return Application.getGame().getRoomBySocketId(context.getSocketId());
    }

    protected abstract void handleRoom(T context);

    protected void handleEmpty(T context) {

    }

}
