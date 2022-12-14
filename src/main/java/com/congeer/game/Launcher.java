package com.congeer.game;

import com.congeer.game.bean.Message;
import com.congeer.game.codec.GameMessageCodec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;

public class Launcher {

    private static Vertx vert;

    private static RedisAPI redisAPI;

    public static Vertx getVert() {
        return vert;
    }

    public static RedisAPI getRedis() {
        return redisAPI;
    }

    public static void configJackson() {
        ObjectMapper mapper = DatabindCodec.mapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) {
        configJackson();
        vert = Vertx.vertx();
        vert.eventBus().registerDefaultCodec(Message.class, new GameMessageCodec());
        vert.deployVerticle(new GameVerticle(), new DeploymentOptions().setWorker(true));
        vert.deployVerticle(new RequestVerticle());
        Redis client = Redis.createClient(
            vert,
            "redis://192.168.64.6:6379/0");
        client.connect().onSuccess(conn -> {
            System.out.println("redis start success...");
        }).onFailure(info -> {
            System.out.println("redis start failed...");
        });
        redisAPI = RedisAPI.api(client);
    }

}
