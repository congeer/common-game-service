package com.congeer.game;

import com.congeer.game.bean.GameMessageCodec;
import com.congeer.game.bean.Message;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Launcher {

    private static Vertx vert;

    public static Vertx getVert() {
        return vert;
    }

    public static void main(String[] args) {
        vert = Vertx.vertx();
        vert.eventBus().registerDefaultCodec(Message.class, new GameMessageCodec());
        vert.deployVerticle(new GameVerticle(), new DeploymentOptions().setWorker(true));
        vert.deployVerticle(new RequestVerticle());
    }

}
