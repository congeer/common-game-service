package com.congeer.game.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapGameStorage extends GameStorage {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();

    private final Map<String, String> socketRoom = new ConcurrentHashMap<>();

    private final Map<String, Long> socketAlive = new ConcurrentHashMap<>();

    @Override
    public boolean containsRoom(String roomId) {
        return roomMap.containsKey(roomId);
    }

    @Override
    public void addSocket(String socketId, String roomId) {
        socketRoom.put(socketId, roomId);
    }

    @Override
    public void updateRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    @Override
    public Room getRoomBySocketId(String socketId) {
        String roomId = socketRoom.get(socketId);
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
        socketRoom.remove(socketId);
        socketAlive.remove(socketId);
    }

    @Override
    public GameStatus getStatus() {
        GameStatus status = new GameStatus();
        status.setRoomCount(roomMap.size());
        status.setSocketCount(socketRoom.size());
        status.setRoomList(roomMap.values().stream().toList());
        return status;
    }

    @Override
    public void setSocketAlive(String socketId) {
        socketAlive.put(socketId, System.nanoTime());
    }

}
