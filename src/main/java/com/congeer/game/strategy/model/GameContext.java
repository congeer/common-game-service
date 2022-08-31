package com.congeer.game.strategy.model;

import com.congeer.game.Launcher;
import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GameContext {

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();

    private final Map<String, String> socketRoom = new ConcurrentHashMap<>();

    public boolean containsRoom(String roomId) {
        return roomMap.containsKey(roomId);
    }

    public void addSocket(String socketId, String roomId) {
        socketRoom.put(socketId, roomId);
    }

    public Room addEmptyRoom(String roomId) {
        Room room = new Room(roomId);
        roomMap.put(room.getId(), room);
        return room;
    }

    public Room getRoomBySocketId(String socketId) {
        String roomId = socketRoom.get(socketId);
        if (roomId == null) {
            return null;
        }
        return roomMap.get(roomId);
    }

    public Player getPlayerBySocketId(String socketId) {
        Room room = getRoomBySocketId(socketId);
        if (room == null) {
            return null;
        }
        return room.getPlayer(socketId);
    }

    public Room getRoom(String roomId) {
        return roomMap.get(roomId);
    }

    public Player getEmptyPlayer(String roomId, String playerId) {
        Room room = getRoom(roomId);
        Optional<Player> has = room.getPlayers().stream().filter(v -> playerId.equals(v.getId())).findFirst();
        if (has.isPresent()) {
            return has.get();
        }
        Optional<Player> first = room.getPlayers().stream().filter(v -> (!v.isLock() && v.getSocketId() == null)
            || v.getId() == null).findFirst();
        return first.orElse(null);
    }

    public void removeSocket(String socketId) {
        socketRoom.remove(socketId);
    }

    public void radio(String senderSocketId, Message msg) {
        Room room = getRoomBySocketId(senderSocketId);
        room.getAll().stream().filter(v -> v.getSocketId() != null).filter(v -> !v.getSocketId().equals(senderSocketId))
            .forEach(v -> notice(v.getSocketId(), msg));
    }

    public void notice(String socketId, Message data) {
        System.out.println(data);
        Launcher.getVert().eventBus().send(socketId, JsonObject.mapFrom(data).toBuffer());
    }

    public GameStatus getStatus() {
        GameStatus status = new GameStatus();
        status.setRoomCount(roomMap.size());
        status.setSocketCount(socketRoom.size());
        return status;
    }

}
