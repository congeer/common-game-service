package com.congeer.game.strategy.model;

import com.congeer.game.Launcher;
import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.util.Optional;

public abstract class GameContext {

    public abstract void addSocket(String socketId, String roomId);
    public abstract Future<Room> getRoomBySocketId(String socketId);

    public abstract Player getPlayerBySocketId(String socketId);

    public abstract Future<Room> getRoom(String roomId);

    public abstract Player getEmptyPlayer(String roomId, String playerId);

    public abstract void removeSocket(String socketId);

    public void radio(String senderSocketId, Message msg) {
        radio(senderSocketId, msg, true);
    }

    public void radio(String senderSocketId, Message msg, boolean excludeSelf) {
        getRoomBySocketId(senderSocketId).onSuccess(room -> {
            room.allPlayer().stream().filter(v -> v.getSocketId() != null).filter(v -> !excludeSelf || !v.getSocketId()
                .equals(senderSocketId)).forEach(v -> notice(v.getSocketId(), msg));
        });
    }

    public void radio(Room room, Message msg) {
        room.allPlayer().stream().filter(v -> v.getSocketId() != null).forEach(v -> notice(v.getSocketId(), msg));
    }

    public void notice(String socketId, Message data) {
        JsonObject log = new JsonObject();
        log.put("time", LocalDateTime.now());
        log.put("type", "SEND_TO_CLIENT");
        log.put("data", data);
        System.out.println(log);
        Launcher.getVert().eventBus().send(socketId, JsonObject.mapFrom(data).toBuffer());
    }

    public abstract GameStatus getStatus();

    public abstract void setSocketAlive(String socketId);

    public abstract void updateRoom(Room room);

}
