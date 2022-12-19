package com.congeer.game.model;

import com.congeer.game.Application;
import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;

import static com.congeer.game.enums.ClientEventEnum.ERROR;

public abstract class RoomContext extends EventContext {

    protected Room room;

    public Room getRoom() {
        if (room == null) {
            room = Application.getGame().getRoomBySocketId(socketId);
        }
        return room;
    }

    public RoomContext setRoom(Room room) {
        this.room = room;
        return this;
    }

    public void radio(BaseMessage msg) {
        radio(msg, true);
    }

    public void radio(BaseMessage msg, boolean excludeSelf) {
        if (getRoom() != null) {
            room.allPlayer().stream().filter(v -> v.getSocketId() != null).filter(v -> !excludeSelf || !v.getSocketId()
                .equals(socketId)).forEach(v -> notice(v.getSocketId(), msg));
        }
    }

    public void updateRoom() {
        GameStorage gameStorage = Application.getGame();
        getRoom();
        gameStorage.addSocket(socketId, room.getId());
        gameStorage.setSocketAlive(socketId);
        room.setLastUpdateTime(System.currentTimeMillis());
        gameStorage.updateRoom(room);
    }

    public void createRoom(Room room) {
        GameStorage gameStorage = Application.getGame();
        this.room = room;
        if (!gameStorage.containsRoom(room.getId())) {
            gameStorage.addSocket(socketId, room.getId());
            gameStorage.setSocketAlive(socketId);
            gameStorage.updateRoom(room);
        } else {
            reply(new BaseMessage(ERROR));
        }
    }

}
