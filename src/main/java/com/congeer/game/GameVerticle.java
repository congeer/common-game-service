package com.congeer.game;

import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.enums.BaseEventEnum;
import com.congeer.game.strategy.enums.LobbyEventEnum;
import com.congeer.game.strategy.enums.RoomEventEnum;
import com.congeer.game.strategy.model.GameContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class GameVerticle extends AbstractVerticle {

    private final static GameContext GAME_CONTEXT = new GameContext();

    @Override
    public void start() throws Exception {
        System.out.println(Thread.currentThread().getName() + ", Start Worker...");
        EventBus eventBus = getVertx().eventBus();
        for (BaseEventEnum value : BaseEventEnum.values()) {
            if (value.getClz() == null) {
                continue;
            }
            GameEvent handler = value.getClz().getDeclaredConstructor().newInstance().context(GAME_CONTEXT);
            String address = "GAME_EVENT/" + value.getCode();
            eventBus.consumer(address, handler);
        }
        for (RoomEventEnum value : RoomEventEnum.values()) {
            if (value.getClz() == null) {
                continue;
            }
            GameEvent handler = value.getClz().getDeclaredConstructor().newInstance().context(GAME_CONTEXT);
            String address = "GAME_EVENT/" + value.getCode();
            eventBus.consumer(address, handler);
        }
        for (LobbyEventEnum value : LobbyEventEnum.values()) {
            if (value.getClz() == null) {
                continue;
            }
            GameEvent handler = value.getClz().getDeclaredConstructor().newInstance().context(GAME_CONTEXT);
            String address = "GAME_EVENT/" + value.getCode();
            eventBus.consumer(address, handler);
        }

        eventBus.consumer("GAME_REQ/STATUS", handler -> handler.reply(JsonObject.mapFrom(GAME_CONTEXT.getStatus())));
        eventBus.consumer("GAME_REQ/ROOM", handler -> handler.reply(JsonObject.mapFrom(GAME_CONTEXT.getStatus())));
    }

}
