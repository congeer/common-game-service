package com.congeer.game.model;

import com.congeer.game.Application;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.impl.SerializableUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sync.Sync;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;

public class RedisGameStorage extends GameStorage {

    public RedisAPI getRedis() {
        return Application.getRedis();
    }

    public Response syncRedis(Command cmd, String ...key) {
        return Sync.awaitResult(h -> getRedis().send(cmd, key).onComplete(h));
    }

    @Override
    public boolean containsRoom(String roomId) {
        Response response = syncRedis(Command.GET, roomId);
        return response != null;
    }

    @Override
    public void updateRoom(Room room) {
        getRedis().send(Command.SET, new String(SerializableUtils.toBytes(room)));
    }

    @Override
    public void addSocket(String socketId, Player player) {
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
