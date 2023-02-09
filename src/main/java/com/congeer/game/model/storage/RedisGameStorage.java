package com.congeer.game.model.storage;

import com.congeer.game.Application;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.GameStatus;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

import static com.congeer.game.bean.Constant.ROOM_KEY;
import static com.congeer.game.bean.Constant.SOCKET_KEY;

public class RedisGameStorage extends GameStorage {

    public void saveRoom(Room room) {
        byte[] serialize = SerializationUtils.serialize(room);
        String s = Base64.getEncoder().encodeToString(serialize);
        System.out.println("SAVE ROOM:" + JsonObject.mapFrom(room));
        try (Jedis redis = Application.getRedis()) {
            redis.hset(ROOM_KEY, room.getId(), s);
        }
    }

    @Override
    public void updateRoom(Room room) {
        saveRoom(room);
    }

    @Override
    public void saveSocket(String socketId, Player player) {
        player.setLastUpdateTime(System.currentTimeMillis());
        byte[] serialize = SerializationUtils.serialize(player);
        String s = Base64.getEncoder().encodeToString(serialize);
        try (Jedis redis = Application.getRedis()) {
            redis.hset(SOCKET_KEY, socketId, s);
        }
    }

    @Override
    public Player getPlayerBySocketId(String socketId) {
        try (Jedis redis = Application.getRedis()) {
            String resp = redis.hget(SOCKET_KEY, socketId);
            if (resp == null) {
                return null;
            }
            return decode(resp.getBytes());
        }
    }

    private static <T> T decode(byte[] bytes) {
        return SerializationUtils.deserialize(Base64.getDecoder().decode(bytes));
    }

    @Override
    public Room getRoom(String roomId) {
        try (Jedis redis = Application.getRedis()) {
            String resp = redis.hget(ROOM_KEY, roomId);
            if (resp == null) {
                return null;
            }
            return decode(resp.getBytes());
        }
    }

    @Override
    public void removeSocket(String socketId) {
        try (Jedis redis = Application.getRedis()) {
            redis.hdel(SOCKET_KEY, socketId);
        }
    }

    @Override
    public GameStatus getStatus() {
        GameStatus status = new GameStatus();
        try (Jedis redis = Application.getRedis()) {
            Map<String, String> allRoom = redis.hgetAll(ROOM_KEY);
            status.setRoomCount(allRoom.size());
            status.setRoomList(new ArrayList<>());
            for (String key : allRoom.keySet()) {
                Room room = decode(allRoom.get(key).getBytes());
                status.getRoomList().add(room);
            }
            Map<String, String> allPlayer = redis.hgetAll(SOCKET_KEY);
            status.setPlayerCount(allPlayer.size());
            status.setPlayerList(new ArrayList<>());
            for (String key : allPlayer.keySet()) {
                Player player = decode(allPlayer.get(key).getBytes());
                status.getPlayerList().add(player);
            }
        }
        return status;
    }

    @Override
    public void setSocketAlive(String socketId) {
        Player player = getPlayerBySocketId(socketId);
        saveSocket(socketId, player);
    }

    @Override
    public void clearExpire() {
        try (Jedis redis = Application.getRedis()) {
            Map<String, String> allRoom = redis.hgetAll(ROOM_KEY);
            for (String key : allRoom.keySet()) {
                Room room = decode(allRoom.get(key).getBytes());
                if (room.expire()) {
                    redis.hdel(ROOM_KEY, room.getId());
                    System.out.println("REMOVE ROOM:" + room.getId());
                }
            }
            Map<String, String> allPlayer = redis.hgetAll(SOCKET_KEY);
            for (String key : allPlayer.keySet()) {
                Player player = decode(allPlayer.get(key).getBytes());
                if (player.expire()) {
                    redis.hdel(SOCKET_KEY, player.getSocketId());
                    System.out.println("REMOVE PLAYER:" + player.getSocketId());
                }
            }
        }
    }

}
