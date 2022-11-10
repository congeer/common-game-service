package com.congeer.game;

import com.congeer.game.bean.Message;
import com.congeer.game.strategy.enums.RoomEventEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class RequestVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println(Thread.currentThread().getName() + ", Start Worker...");
        EventBus eventBus = getVertx().eventBus();
        Router router = Router.router(getVertx());
        router.get("/game/status").handler(handler -> {
            eventBus.request("GAME_REQ/STATUS", null, resp -> {
                if (resp.succeeded()) {
                    handler.end(resp.result().body().toString());
                }
            });
        });
        getVertx().createHttpServer().webSocketHandler(webSocket -> {
            if (!webSocket.path().equals("/game/ws")) {
                webSocket.reject();
            }
            String socketId = webSocket.binaryHandlerID();
            webSocket.handler(handler -> {
                JsonObject entries = handler.toJsonObject();
                entries.put("socketId", socketId);
                Message message = entries.mapTo(Message.class);
                eventBus.send("GAME_EVENT/" + message.getType(), message);
            }).closeHandler(handler -> {
            }).exceptionHandler(handler -> {
            }).drainHandler(handler -> {
            }).endHandler(handler -> {
                Message message = new Message().setType(RoomEventEnum.LEAVE_ROOM.getCode()).setSocketId(socketId);
                eventBus.send("GAME_EVENT/" + message.getType(), message);
            });
        }).requestHandler(router).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

}
