package com.congeer.game;

import com.congeer.game.bean.GameMessageCodec;
import com.congeer.game.bean.Message;
import com.congeer.game.bean.MessageConverter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class RequestVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println(Thread.currentThread().getName() + ", Start Worker...");
        EventBus eventBus = getVertx().eventBus();
        getVertx().createHttpServer().webSocketHandler(webSocket -> {
            if (!webSocket.path().equals("/game/ws")) {
                webSocket.reject();
            }
            String socketId = webSocket.binaryHandlerID();
            webSocket.handler(handler -> {
                JsonObject entries = handler.toJsonObject();
                entries.put("socketId", socketId);
                Message message = entries.mapTo(Message.class);
                System.out.println(message);
                eventBus.<Message>send("GAME_EVENT/" + message.getType(), message);
            }).closeHandler(handler -> {
            }).exceptionHandler(handler -> {
            }).drainHandler(handler -> {
            }).endHandler(handler -> {
                eventBus.send("websocket.end", socketId);
            });
        }).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

}
