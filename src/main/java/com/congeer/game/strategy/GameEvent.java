package com.congeer.game.strategy;

import com.congeer.game.Application;
import com.congeer.game.bean.BaseMessage;
import com.congeer.game.strategy.model.GameContext;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.json.JsonObject;

public abstract class GameEvent<T> extends AbstractEvent<T> {

    protected GameContext gameContext;

    @Fluent
    public GameEvent<T> context(GameContext gameContext) {
        this.gameContext = gameContext;
        return this;
    }

    protected void removeSocket() {
        gameContext.removeSocket(socketId);
    }

    protected void reply(BaseMessage msg) {
        notice(socketId, msg);
    }

    protected void sendTo(String socketId, BaseMessage msg) {
        notice(socketId, msg);
    }

    protected void notice(String socketId, BaseMessage data) {
        System.out.println("SEND TO CLIENT: " + data.toJson());
        Application.getVert().eventBus().send(socketId, JsonObject.mapFrom(data).toBuffer());
    }


}
