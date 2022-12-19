package com.congeer.game.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapGameStorage extends GameStorage {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();

    private final Map<String, Player> playerMap = new ConcurrentHashMap<>();

    @Override
    public boolean containsRoom(String roomId) {
        return roomMap.containsKey(roomId);
    }

    @Override
    public void addSocket(String socketId, Player player) {
        playerMap.put(socketId, player);
    }

    @Override
    public void updateRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    @Override
    public Room getRoomBySocketId(String socketId) {
        Player player = playerMap.get(socketId);
        if (player == null) {
            return null;
        }
        String roomId = player.getWhere();
        if (roomId == null) {
            return null;
        }
        return roomMap.get(roomId);
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
        return roomMap.get(roomId);
    }

    @Override
    public void removeSocket(String socketId) {
        playerMap.remove(socketId);
    }

    @Override
    public GameStatus getStatus() {
        GameStatus status = new GameStatus();
        status.setRoomCount(roomMap.size());
        status.setPlayerCount(playerMap.size());
        status.setRoomList(roomMap.values().stream().toList());
        status.setPlayerList(playerMap.values().stream().toList());
        return status;
    }

    @Override
    public void setSocketAlive(String socketId) {
        Player player = playerMap.get(socketId);
        if (player != null) {
            player.setLastUpdateTime(System.currentTimeMillis());
        }
    }

}
