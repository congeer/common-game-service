package com.congeer.game.model.storage;

import com.congeer.game.Application;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.GameStatus;
import com.congeer.game.model.PlayerWrapper;
import com.congeer.game.model.RoomWrapper;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.congeer.game.bean.Constant.ROOM_KEY;
import static com.congeer.game.bean.Constant.SOCKET_KEY;

public class RedisGameStorage extends GameStorage {

    private final Map<String, RoomWrapper> roomTmp = new ConcurrentHashMap<>();

    private final Map<String, PlayerWrapper> playerTmp = new ConcurrentHashMap<>();

    public RedisAPI getRedis() {
        return Application.getRedis();
    }

    public Future<Response> saveRoom(Room room) {
        byte[] serialize = SerializationUtils.serialize(room);
        String s = Base64.getEncoder().encodeToString(serialize);
        System.out.println("SAVE ROOM:" + JsonObject.mapFrom(room));
        return getRedis().send(Command.HSET, ROOM_KEY, room.getId(), s);
    }

    @Override
    public void updateRoom(Room room) {
        String roomId = room.getId();
        RoomWrapper roomWrapper = roomTmp.get(roomId);
        if (roomWrapper == null) {
            return;
        }
        getRedis().send(Command.HGET, ROOM_KEY, roomId).onSuccess(resp -> {
            roomWrapper.setCount(roomWrapper.getCount() - 1);
            if (roomWrapper.getCount() == 0) {
                roomTmp.remove(roomId);
                saveRoom(room);
                System.out.println("REMOVE ROOM TEMP:" + roomId);
            }
        });
    }

    @Override
    public void saveSocket(String socketId, Player player) {
        player.setLastUpdateTime(System.currentTimeMillis());
        byte[] serialize = SerializationUtils.serialize(player);
        String s = Base64.getEncoder().encodeToString(serialize);
        PlayerWrapper playerWrapper = playerTmp.get(socketId);
        if (playerWrapper == null) {
            return;
        }
        getRedis().send(Command.HGET, SOCKET_KEY, socketId).onSuccess(resp->{
            playerWrapper.setCount(playerWrapper.getCount() - 1);
            if (playerWrapper.getCount() == 0) {
                playerTmp.remove(socketId);
                System.out.println("REMOVE PLAYER TEMP:" + socketId);
                getRedis().send(Command.HSET, SOCKET_KEY, socketId, s);
            }
        });
    }

    @Override
    public Future<Player> getPlayerBySocketId(String socketId) {
        if (playerTmp.containsKey(socketId)) {
            return getTmpPlayer(socketId);
        }
        return getRedis().send(Command.HGET, SOCKET_KEY, socketId)
            .compose(response -> {
                if (playerTmp.containsKey(socketId)) {
                    return getTmpPlayer(socketId);
                }
                Player player = decode(response.toBytes());
                PlayerWrapper playerWrapper = new PlayerWrapper();
                playerWrapper.setCount(1);
                playerWrapper.setPlayer(player);
                playerTmp.put(socketId, playerWrapper);
                return Future.succeededFuture(player);
            });
    }

    private static <T> T decode(byte[] bytes) {
        return SerializationUtils.deserialize(Base64.getDecoder().decode(bytes));
    }

    @Override
    public Future<Room> getRoom(String roomId) {
        if (roomTmp.containsKey(roomId)) {
            return getTmpRoom(roomId);
        }
        return getRedis().send(Command.HGET, ROOM_KEY, roomId)
            .compose(response -> {
                if (roomTmp.containsKey(roomId)) {
                    return getTmpRoom(roomId);
                }
                Room room = decode(response.toBytes());
                RoomWrapper roomWrapper = new RoomWrapper();
                roomWrapper.setCount(1);
                roomWrapper.setRoom(room);
                roomTmp.put(roomId, roomWrapper);
                return Future.succeededFuture(room);
            });
    }

    public Future<Room> getTmpRoom(String roomId) {
        RoomWrapper roomWrapper = roomTmp.get(roomId);
        roomWrapper.setCount(roomWrapper.getCount() + 1);
        return Future.succeededFuture(roomWrapper.getRoom());
    }

    public Future<Player> getTmpPlayer(String socketId) {
        PlayerWrapper playerWrapper = playerTmp.get(socketId);
        playerWrapper.setCount(playerWrapper.getCount() + 1);
        return Future.succeededFuture(playerWrapper.getPlayer());
    }

    @Override
    public void removeSocket(String socketId) {
        playerTmp.remove(socketId);
        getRedis().send(Command.HDEL, SOCKET_KEY, socketId);
    }

    @Override
    public Future<GameStatus> getStatus() {
        GameStatus status = new GameStatus();
        Future<GameStatus> compose1 = getRedis().hgetall(ROOM_KEY).compose(response -> {
            status.setRoomCount(response.getKeys().size());
            status.setRoomList(new ArrayList<>());
            for (String key : response.getKeys()) {
                Room room = decode(response.get(key).toBytes());
                status.getRoomList().add(room);
            }
            return Future.succeededFuture(status);
        });
        Future<GameStatus> compose2 = getRedis().hgetall(SOCKET_KEY).compose(response -> {
            status.setPlayerCount(response.getKeys().size());
            status.setPlayerList(new ArrayList<>());
            for (String key : response.getKeys()) {
                Player player = decode(response.get(key).toBytes());
                status.getPlayerList().add(player);
            }
            return Future.succeededFuture(status);
        });
        return CompositeFuture.all(compose1, compose2).compose(resp -> Future.succeededFuture(status));
    }

    @Override
    public void setSocketAlive(String socketId) {
        getPlayerBySocketId(socketId).compose(player -> {
            saveSocket(socketId, player);
            return Future.succeededFuture();
        });
    }

    @Override
    public void clearExpire() {
        getRedis().hgetall(ROOM_KEY).compose(response -> {
            for (String key : response.getKeys()) {
                Room room = decode(response.get(key).toBytes());
                if (room.expire()) {
                    roomTmp.remove(room.getId());
                    getRedis().send(Command.HDEL, ROOM_KEY, room.getId());
                    System.out.println("REMOVE ROOM:" + room.getId());
                }
            }
            return Future.succeededFuture();
        });
        getRedis().hgetall(SOCKET_KEY).compose(response -> {
            for (String key : response.getKeys()) {
                Player player = decode(response.get(key).toBytes());
                if (player.expire()) {
                    playerTmp.remove(player.getSocketId());
                    getRedis().send(Command.HDEL, SOCKET_KEY, player.getSocketId());
                    System.out.println("REMOVE PLAYER:" + player.getSocketId());
                }
            }
            return Future.succeededFuture();
        });
    }

}
