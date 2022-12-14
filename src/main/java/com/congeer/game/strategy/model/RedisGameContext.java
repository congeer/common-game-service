package com.congeer.game.strategy.model;

import com.congeer.game.Launcher;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.utils.JsonUtil;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;

import java.util.List;

public class RedisGameContext extends GameContext {

    private final String ROOM_KEY = "room/";
    private final String SOCKET_KEY = "socket/";

    public RedisGameContext() {
    }

    public RedisAPI getRedis() {
        return Launcher.getRedis();
    }

    @Override
    public void addSocket(String socketId, String roomId) {
        String key = SOCKET_KEY + socketId;
        getRedis().setex(key, "6000", roomId);
    }

    @Override
    public void updateRoom(Room room) {
        getRedis().send(Command.SET, ROOM_KEY + room.getId(), JsonUtil.toJsonString(room));
    }

    @Override
    public void createRoom(Room room) {
        getRedis().send(Command.SETNX, ROOM_KEY + room.getId(), JsonUtil.toJsonString(room));
    }

    @Override
    public Future<Room> getRoomBySocketId(String socketId) {
        Promise<Room> promise = Promise.promise();
        getRedis().get(SOCKET_KEY + socketId).onSuccess(resp -> {
            if (resp != null) {
                getRoom(resp.toString()).onSuccess(promise::complete).onFailure(promise::fail);
            } else {
                promise.fail("not exist");
            }
        }).onFailure(promise::fail);
        return promise.future();
    }

    @Override
    public Future<Player> getPlayerBySocketId(String socketId) {
        return null;
    }

    @Override
    public Future<Room> getRoom(String roomId) {
        Promise<Room> promise = Promise.promise();
        getRedis().get(ROOM_KEY + roomId).onSuccess(resp -> {
            if (resp != null) {
                Room room = JsonUtil.fromJson(resp.toString(), Room.class);
                promise.complete(room);
            } else {
                promise.fail("not exist");
            }
        }).onFailure(promise::fail);
        return promise.future();
    }

    @Override
    public Future<Player> getEmptyPlayer(String roomId, String playerId) {
        return null;
    }

    @Override
    public void removeSocket(String socketId) {
        getRedis().send(Command.DEL, SOCKET_KEY + socketId);
    }

    @Override
    public Future<GameStatus> getStatus() {
        Promise<GameStatus> promise = Promise.promise();
        GameStatus status = new GameStatus();
//        status.setRoomCount(roomMap.size());
//        status.setSocketCount(socketRoom.size());
//        status.setRoomList(roomMap.values().stream().toList());
        CompositeFuture all = CompositeFuture.all(getRedis().keys(ROOM_KEY + "*"), getRedis().keys(SOCKET_KEY+"*"));
        all.onComplete(resp->{
            List<Response> list = resp.result().list();
            System.out.println(list);
        }).onFailure(promise::fail);
        return promise.future();
    }

    @Override
    public void setSocketAlive(String socketId) {
        getRedis().send(Command.EXPIRE, SOCKET_KEY + socketId, "6000");
    }

}
