package com.congeer.game.model;

import com.congeer.game.Application;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisAPI;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Base64;

import static com.congeer.game.bean.Constant.ROOM_KEY;
import static com.congeer.game.bean.Constant.SOCKET_KEY;

public class RedisGameStorage extends GameStorage {

    public RedisAPI getRedis() {
        return Application.getRedis();
    }

    @Override
    public void updateRoom(Room room) {
        byte[] serialize = SerializationUtils.serialize(room);
        String s = Base64.getEncoder().encodeToString(serialize);
        getRedis().send(Command.HSET, ROOM_KEY, room.getId(), s);
    }

    @Override
    public void addSocket(String socketId, Player player) {
        byte[] serialize = SerializationUtils.serialize(player);
        String s = Base64.getEncoder().encodeToString(serialize);
        getRedis().send(Command.HSET, SOCKET_KEY, socketId, s);
    }

    @Override
    public Future<Player> getPlayerBySocketId(String socketId) {
        return getRedis().send(Command.HGET, SOCKET_KEY, socketId)
            .compose(response -> Future.succeededFuture(decode(response.toBytes())));
    }

    private static <T> T decode(byte[] bytes) {
        return SerializationUtils.deserialize(Base64.getDecoder().decode(bytes));
    }

    @Override
    public Future<Room> getRoom(String roomId) {
        return getRedis().send(Command.HGET, ROOM_KEY, roomId)
            .compose(response -> Future.succeededFuture(decode(response.toBytes())));
    }

    @Override
    public void removeSocket(String socketId) {
        getRedis().send(Command.HDEL, SOCKET_KEY, socketId);
    }

    @Override
    public Future<GameStatus> getStatus() {
        GameStatus status = new GameStatus();
        Future<GameStatus> compose1 = getRedis().hgetall(ROOM_KEY).compose(response -> {
            status.setRoomCount(response.size());
            status.setRoomList(new ArrayList<>());
            for (String key : response.getKeys()) {
                status.getRoomList().add(decode(response.get(key).toBytes()));
            }
            return Future.succeededFuture(status);
        });
        Future<GameStatus> compose2 = getRedis().hgetall(SOCKET_KEY).compose(response -> {
            status.setPlayerCount(response.size());
            status.setPlayerList(new ArrayList<>());
            for (String key : response.getKeys()) {
                status.getPlayerList().add(decode(response.get(key).toBytes()));
            }
            return Future.succeededFuture(status);
        });
        return CompositeFuture.all(compose1, compose2).compose(resp -> Future.succeededFuture(status));
    }

    @Override
    public void setSocketAlive(String socketId) {
    }

}
