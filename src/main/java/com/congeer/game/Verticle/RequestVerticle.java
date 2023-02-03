package com.congeer.game.Verticle;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.enums.RoomEventEnum;
import com.congeer.game.enums.RouteEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;

import static com.congeer.game.bean.Constant.EVENT_KEY;
import static com.congeer.game.bean.Constant.GAME_KEY;
import static com.congeer.game.bean.Constant.REQUEST_KEY;
import static com.congeer.game.bean.Constant.ROOM_KEY;

public class RequestVerticle extends AbstractVerticle {

    private EventBus eventBus;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println(Thread.currentThread().getName() + ", Start Worker...");
        eventBus = getVertx().eventBus();
        Router router = createRouter();
        getVertx().createHttpServer().webSocketHandler(createWebSocket()).requestHandler(router).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
                System.out.println("HTTP server started failed");
            }
        });
    }

    private Handler<ServerWebSocket> createWebSocket() {
        return webSocket -> {
            if (!webSocket.path().equals("/game/ws")) {
                webSocket.reject();
            }
            String socketId = webSocket.binaryHandlerID();
            webSocket.handler(buffer -> {
                BaseMessage message = buffer.toJsonObject().mapTo(BaseMessage.class).setSocketId(socketId);
                eventBus.send(EVENT_KEY + GAME_KEY + message.getType(), message);
            }).closeHandler(handler -> {
                System.out.println("close");
                BaseMessage message = new BaseMessage().setSocketId(socketId)
                    .setType(RoomEventEnum.LEAVE_ROOM.getCode());
                eventBus.send(EVENT_KEY + GAME_KEY + ROOM_KEY + message.getType(), message);
            }).exceptionHandler(handler -> {
                System.out.println("exception");
                BaseMessage message = new BaseMessage().setSocketId(socketId)
                    .setType(RoomEventEnum.LEAVE_ROOM.getCode());
                eventBus.send(EVENT_KEY + GAME_KEY + ROOM_KEY + message.getType(), message);
            }).drainHandler(handler -> {
                System.out.println("drain");
                BaseMessage message = new BaseMessage().setSocketId(socketId)
                    .setType(RoomEventEnum.LEAVE_ROOM.getCode());
                eventBus.send(EVENT_KEY + GAME_KEY + ROOM_KEY + message.getType(), message);
            }).endHandler(handler -> {
                System.out.println("end");
                BaseMessage message = new BaseMessage().setSocketId(socketId)
                    .setType(RoomEventEnum.LEAVE_ROOM.getCode());
                eventBus.send(EVENT_KEY + GAME_KEY + ROOM_KEY + message.getType(), message);
            });
        };
    }

    private Router createRouter() {
        Router router = Router.router(getVertx());
        for (RouteEnum value : RouteEnum.values()) {
            router.route(value.getMethod(), value.getPath()).handler(context -> {
                BaseMessage message = value.getData().apply(context);
                eventBus.request(REQUEST_KEY + GAME_KEY + value.name(), message, resp -> {
                    if (resp.succeeded()) {
                        context.end(resp.result().body().toString());
                    } else {
                        context.fail(resp.cause());
                    }
                });
            });
        }
        return router;
    }

}
