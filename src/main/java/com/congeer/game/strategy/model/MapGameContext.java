package com.congeer.game.strategy.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MapGameContext extends GameContext {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();

    private final Map<String, String> socketRoom = new ConcurrentHashMap<>();

    private final Map<String, Long> socketAlive = new ConcurrentHashMap<>();

    public void addSocket(String socketId, String roomId) {
        socketRoom.put(socketId, roomId);
    }

    public void updateRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    @Override
    public void createRoom(Room room) {
        if (!roomMap.containsKey(room)) {
            roomMap.put(room.getId(), room);
        } else {
            
        }
    }

    public Future<Room> getRoomBySocketId(String socketId) {
        String roomId = socketRoom.get(socketId);
        if (roomId == null) {
            return null;
        }
        return getRoom(roomId);
    }

    @Override
    public Future<Player> getPlayerBySocketId(String socketId) {
        Promise<Player> promise = Promise.promise();
        getRoomBySocketId(socketId).onSuccess(room -> {
            Player player = room.getPlayer(socketId);
            if (player != null) {
                promise.complete(player);
            } else {
                promise.fail("not exist");
            }
        }).onFailure(promise::fail);
        return promise.future();
    }

    public Future<Room> getRoom(String roomId) {
        if (roomMap.containsKey(roomId)) {
            return Future.succeededFuture(roomMap.get(roomId));
        } else {
            return Future.failedFuture("not exist");
        }
    }

    @Override
    public Future<Player> getEmptyPlayer(String roomId, String playerId) {
        Promise<Player> promise = Promise.promise();
        getRoom(roomId).onSuccess(room -> {
            Optional<Player> has = room.getPlayers().stream().filter(v -> playerId.equals(v.getId())).findFirst();
            if (has.isPresent()) {
                promise.complete(has.get());
                return;
            }
            Optional<Player> first = room.getPlayers().stream().filter(v -> (!v.isLock() && v.getSocketId() == null)
                || v.getId() == null).findFirst();
            if (first.isPresent()) {
                promise.complete(first.get());
            } else {
                promise.fail("not exist");
            }
        }).onFailure(promise::fail);
        return promise.future();
    }

    public void removeSocket(String socketId) {
        socketRoom.remove(socketId);
    }

    public Future<GameStatus> getStatus() {
        GameStatus status = new GameStatus();
        status.setRoomCount(roomMap.size());
        status.setSocketCount(socketRoom.size());
        status.setRoomList(roomMap.values().stream().toList());
        return Future.succeededFuture(status);
    }

    public void setSocketAlive(String socketId) {
        socketAlive.put(socketId, System.nanoTime());
    }

}
