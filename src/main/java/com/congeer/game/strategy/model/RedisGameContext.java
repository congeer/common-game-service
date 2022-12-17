package com.congeer.game.strategy.model;

import com.congeer.game.Application;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisAPI;

public class RedisGameContext extends GameContext {

    private final String ROOM_KEY = "room/";
    private final String SOCKET_KEY = "socket/";

    public RedisAPI getRedis() {
        return Application.getRedis();
    }

    public <T> void redisGet(String key, Class<T> clz, Handler<T> handler) {
        Promise<T> promise = Promise.promise();
        promise.complete();
        getRedis().get(key, result->{
            if (result.succeeded()) {
                String str = result.result().toString();
                JsonObject json = new JsonObject(str);
                T t = json.mapTo(clz);
                handler.handle(t);
                getRedis().send(Command.SET, key, JsonObject.mapFrom(t).toString());
            }
        });
    }

    @Override
    public boolean containsRoom(String roomId) {
        redisGet(roomId, Room.class, room->{

        });
        return false;
    }

    @Override
    public void updateRoom(Room room) {

    }

    @Override
    public void addSocket(String socketId, String roomId) {
    }

    @Override
    public Room getRoomBySocketId(String socketId) {
        return null;
    }

    @Override
    public Player getPlayerBySocketId(String socketId) {
        Room room = getRoomBySocketId(socketId);
        if (room == null) {
            return null;
        }
        return room.getPlayer(socketId);
    }

    @Override
    public Room getRoom(String roomId) {
        return null;
    }

    @Override
    public void removeSocket(String socketId) {
    }

    @Override
    public GameStatus getStatus() {
        GameStatus status = new GameStatus();
//        status.setRoomCount(roomMap.size());
//        status.setSocketCount(socketRoom.size());
//        status.setRoomList(roomMap.values().stream().toList());
        return status;
    }

    @Override
    public void setSocketAlive(String socketId) {
    }

}
