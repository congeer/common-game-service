package com.congeer.game.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.Future;

public abstract class GameStorage {

    public abstract void updateRoom(Room room);

    public abstract void addSocket(String socketId, Player player);

    public Future<Room> getRoomBySocketId(String socketId) {
        return getPlayerBySocketId(socketId).compose(player -> getRoom(player.getWhere()));
    }

    public abstract Future<Player> getPlayerBySocketId(String socketId);

    public abstract Future<Room> getRoom(String roomId);

    public abstract void removeSocket(String socketId);

    public abstract Future<GameStatus> getStatus();

    public abstract void setSocketAlive(String socketId);

}
