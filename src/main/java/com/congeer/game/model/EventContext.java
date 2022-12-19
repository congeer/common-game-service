package com.congeer.game.model;

import com.congeer.game.Application;
import com.congeer.game.bean.BaseMessage;
import io.vertx.core.json.JsonObject;

public abstract class EventContext {

    protected String socketId;

    public void removeSocket() {
        Application.getGame().removeSocket(socketId);
    }

    public void reply(BaseMessage msg) {
        notice(socketId, msg);
    }

    public void sendTo(String socketId, BaseMessage msg) {
        notice(socketId, msg);
    }

    public void notice(String socketId, BaseMessage data) {
        System.out.println("SEND TO CLIENT: " + data.toJson());
        Application.getVert().eventBus().send(socketId, JsonObject.mapFrom(data).toBuffer());
    }

    public String getSocketId() {
        return socketId;
    }

    public EventContext setSocketId(String socketId) {
        this.socketId = socketId;
        return this;
    }

}
