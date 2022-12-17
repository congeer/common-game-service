package com.congeer.game.strategy.model;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;

public abstract class GameContext {

    public abstract boolean containsRoom(String roomId);

    public abstract void updateRoom(Room room);

    public abstract void addSocket(String socketId, String roomId);

    public abstract Room getRoomBySocketId(String socketId);

    public abstract Player getPlayerBySocketId(String socketId);

    public abstract Room getRoom(String roomId);

    public abstract void removeSocket(String socketId);

    public abstract GameStatus getStatus();

    public abstract void setSocketAlive(String socketId);

}
