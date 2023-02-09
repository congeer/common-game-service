package com.congeer.game.model.storage;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.GameStatus;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapGameStorage extends GameStorage {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();

    private final Map<String, Player> playerMap = new ConcurrentHashMap<>();

    @Override
    public void saveSocket(String socketId, Player player) {
        player.setLastUpdateTime(System.currentTimeMillis());
        playerMap.put(socketId, player);
    }

    @Override
    public void saveRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    @Override
    public void updateRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    @Override
    public Player getPlayerBySocketId(String socketId) {
        return playerMap.get(socketId);
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

    @Override
    public void clearExpire() {
        Iterator<String> roomIter = roomMap.keySet().iterator();
        while (roomIter.hasNext()) {
            String key = roomIter.next();
            if (roomMap.get(key).expire()) {
                roomMap.remove(key);
            }
        }
        Iterator<String> socketIter = playerMap.keySet().iterator();
        while (socketIter.hasNext()) {
            String key = socketIter.next();
            if (playerMap.get(key).expire()) {
                playerMap.remove(key);
            }
        }
    }

}
