package com.congeer.game;

import com.congeer.game.bean.Message;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx1 = Vertx.vertx();
        vertx1.deployVerticle(new MainVerticle());
        vertx1.deployVerticle(new WorkerVerticle(), new DeploymentOptions().setWorker(true));
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println(Thread.currentThread().getName() + ", Start Worker...");
        Router router = Router.router(vertx);
        EventBus eventBus = getVertx().eventBus();
        getVertx().createHttpServer().webSocketHandler(webSocket -> {
            if (!webSocket.path().equals("/game/ws")) {
                webSocket.reject();
            }
            String socketId = webSocket.binaryHandlerID();
            webSocket.handler(handler -> {
                JsonObject entries = handler.toJsonObject();
                entries.put("socketId", socketId);
                eventBus.send("websocket.handler", entries);
            }).closeHandler(handler -> {
            }).exceptionHandler(handler -> {
            }).drainHandler(handler -> {
            }).endHandler(handler -> {
                eventBus.send("websocket.end", socketId);
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
