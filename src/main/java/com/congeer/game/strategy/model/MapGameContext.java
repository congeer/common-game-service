package com.congeer.game.strategy.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapGameContext extends GameContext{

    private final Map<String, Room> roomMap = new ConcurrentHashMap<>();

    private final Map<String, String> socketRoom = new ConcurrentHashMap<>();

    private final Map<String, Long> socketAlive = new ConcurrentHashMap<>();

    public void addSocket(String socketId, String roomId) {
        socketRoom.put(socketId, roomId);
    }

    public void updateRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    public Future<Room> getRoomBySocketId(String socketId) {
        String roomId = socketRoom.get(socketId);
        if (roomId == null) {
            return null;
        }
        return getRoom(roomId);
    }

    public Player getPlayerBySocketId(String socketId) {
//        Room room = getRoomBySocketId(socketId);
//        if (room == null) {
//            return null;
//        }
//        return room.getPlayer(socketId);
        return null;
    }

    public Future<Room> getRoom(String roomId) {
        Promise<Room> promise = Promise.promise();
        if (roomMap.containsKey(roomId)) {
            promise.complete(roomMap.get(roomId));
        } else {
            promise.fail("not exist");
        }
        return promise.future();
    }

    public Player getEmptyPlayer(String roomId, String playerId) {
//        Room room = getRoom(roomId);
//        Optional<Player> has = room.getPlayers().stream().filter(v -> playerId.equals(v.getId())).findFirst();
//        if (has.isPresent()) {
//            return has.get();
//        }
//        Optional<Player> first = room.getPlayers().stream().filter(v -> (!v.isLock() && v.getSocketId() == null)
//            || v.getId() == null).findFirst();
//        return first.orElse(null);
        return null;
    }

    public void removeSocket(String socketId) {
        socketRoom.remove(socketId);
    }

    public GameStatus getStatus() {
        GameStatus status = new GameStatus();
        status.setRoomCount(roomMap.size());
        status.setSocketCount(socketRoom.size());
        status.setRoomList(roomMap.values().stream().toList());
        return status;
    }

    public void setSocketAlive(String socketId) {
        socketAlive.put(socketId, System.nanoTime());
    }

}
