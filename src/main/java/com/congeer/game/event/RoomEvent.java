package com.congeer.game.event;

import com.congeer.game.Application;
import com.congeer.game.bean.Room;
import com.congeer.game.model.context.RoomContext;
import io.vertx.core.Future;

public abstract class RoomEvent<T extends RoomContext> extends AbstractEvent<T> {

    @Override
    protected void handleData(T context) {
        Room room = getRoom(context);
        if (room != null) {
            context.setRoom(room);
            handleRoom(context);
            context.updateRoom();
            replyData(context);
        } else {
            handleEmpty(context);
        }
    }

    protected Room getRoom(T context) {
        return Application.getGame().getRoomBySocketId(context.getSocketId());
    }

    protected abstract void handleRoom(T context);

    protected abstract void replyData(T context);

    protected void handleEmpty(T context) {

    }

}
