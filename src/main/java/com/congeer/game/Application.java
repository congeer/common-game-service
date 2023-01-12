package com.congeer.game;

import com.congeer.game.Verticle.GameVerticle;
import com.congeer.game.Verticle.RequestVerticle;
import com.congeer.game.bean.BaseMessage;
import com.congeer.game.model.GameStorage;
import com.congeer.game.bean.Result;
import com.congeer.game.codec.BaseMessageCodec;
import com.congeer.game.codec.ResultCodec;
import com.congeer.game.model.MapGameStorage;
import com.congeer.game.model.RedisGameStorage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;

public class Application {

    private static Vertx vert;

    private static RedisAPI redisAPI;

    private static GameStorage gameStorage;

    public static GameStorage getGame() {
        return gameStorage;
    }

    public static Vertx getVert() {
        return vert;
    }

    public static EventBus getEventBus() {
        return vert.eventBus();
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
        vert.eventBus().registerDefaultCodec(BaseMessage.class, new BaseMessageCodec());
        vert.eventBus().registerDefaultCodec(Result.class, new ResultCodec());
        vert.deployVerticle(GameVerticle.class, new DeploymentOptions().setWorker(true).setInstances(1));
        vert.deployVerticle(RequestVerticle.class, new DeploymentOptions());
        String redisUrl = "redis://192.168.64.10:6379/0";
        Redis client = Redis.createClient(
            vert,
            redisUrl);
        client.connect().onSuccess(conn -> {
            gameStorage = new RedisGameStorage();
            System.out.println("redis start success...");
        }).onFailure(info -> {
            gameStorage = new MapGameStorage();
            System.out.println("redis start failed...");
        });
        redisAPI = RedisAPI.api(client);
    }

}
