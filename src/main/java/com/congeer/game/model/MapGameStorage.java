package com.congeer.game.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.Future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapGameStorage extends GameStorage {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();

    private final Map<String, Player> playerMap = new ConcurrentHashMap<>();

    @Override
    public void addSocket(String socketId, Player player) {
        player.setLastUpdateTime(System.currentTimeMillis());
        playerMap.put(socketId, player);
    }

    @Override
    public void updateRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    @Override
    public Future<Player> getPlayerBySocketId(String socketId) {
        Player player = playerMap.get(socketId);
        if (player == null) {
            return Future.failedFuture("not exist");
        }
        return Future.succeededFuture(player);
    }

    @Override
    public Future<Room> getRoom(String roomId) {
        Room result = roomMap.get(roomId);
        if (result !=null) {
            return Future.succeededFuture(result);
        } else {
            return Future.failedFuture("not exist");
        }
    }

    @Override
    public void removeSocket(String socketId) {
        playerMap.remove(socketId);
    }

    @Override
    public Future<GameStatus> getStatus() {
        GameStatus status = new GameStatus();
        status.setRoomCount(roomMap.size());
        status.setPlayerCount(playerMap.size());
        status.setRoomList(roomMap.values().stream().toList());
        status.setPlayerList(playerMap.values().stream().toList());
        return Future.succeededFuture(status);
    }

    @Override
    public void setSocketAlive(String socketId) {
        Player player = playerMap.get(socketId);
        if (player != null) {
            player.setLastUpdateTime(System.currentTimeMillis());
        }
    }

}
