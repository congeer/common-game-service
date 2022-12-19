package com.congeer.game.event;

import com.congeer.game.Application;
import com.congeer.game.model.RoomContext;

public abstract class RoomEvent<T extends RoomContext> extends AbstractEvent<T> {

    @Override
    protected void handleData(T context) {
        context.setRoom(Application.getGame().getRoomBySocketId(context.getSocketId()));
        handleRoom(context);
        context.updateRoom();
    }

    protected abstract void handleRoom(T context);

}
