package com.congeer.game.Verticle;

import com.congeer.game.enums.BaseEventEnum;
import com.congeer.game.enums.LobbyEventEnum;
import com.congeer.game.enums.RoomEventEnum;
import com.congeer.game.event.AbstractEvent;
import com.congeer.game.request.base.ServiceStatusRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

import static com.congeer.game.bean.Constant.EVENT_KEY;
import static com.congeer.game.bean.Constant.GAME_KEY;
import static com.congeer.game.bean.Constant.REQUEST_KEY;

public class GameVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        System.out.println(Thread.currentThread().getName() + ", Start Worker...");
        EventBus eventBus = getVertx().eventBus();
        for (BaseEventEnum value : BaseEventEnum.values()) {
            if (value.getClz() == null) {
                continue;
            }
            AbstractEvent<?> handler = value.getClz().getDeclaredConstructor().newInstance();
            String address = EVENT_KEY + GAME_KEY + value.getCode();
            eventBus.consumer(address, handler);
        }
        for (RoomEventEnum value : RoomEventEnum.values()) {
            if (value.getClz() == null) {
                continue;
            }
            AbstractEvent<?> handler = value.getClz().getDeclaredConstructor().newInstance();
            String address = EVENT_KEY + GAME_KEY + value.getCode();
            eventBus.consumer(address, handler);
        }
        for (LobbyEventEnum value : LobbyEventEnum.values()) {
            if (value.getClz() == null) {
                continue;
            }
            AbstractEvent<?> handler = value.getClz().getDeclaredConstructor().newInstance();
            String address = EVENT_KEY + GAME_KEY + value.getCode();
            eventBus.consumer(address, handler);
        }
        eventBus.consumer(REQUEST_KEY + GAME_KEY + "STATUS", new ServiceStatusRequest());
    }

}
