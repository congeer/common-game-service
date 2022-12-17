package com.congeer.game.strategy;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;

import static com.congeer.game.enums.ClientEventEnum.ERROR;

public abstract class RoomEvent<T> extends GameEvent<T> {

    private Room room;

    protected Room getRoom() {
        if (room == null) {
            room = gameContext.getRoomBySocketId(socketId);
        }
        return room;
    }

    protected void radio(BaseMessage msg) {
        radio(msg, true);
    }

    protected void radio(BaseMessage msg, boolean excludeSelf) {
        if (getRoom() != null) {
            room.allPlayer().stream().filter(v -> v.getSocketId() != null).filter(v -> !excludeSelf || !v.getSocketId()
                .equals(socketId)).forEach(v -> notice(v.getSocketId(), msg));
        }
    }

    protected void updateRoom() {
        getRoom();
        gameContext.addSocket(socketId, room.getId());
        gameContext.setSocketAlive(socketId);
        room.setLastUpdateTime(System.currentTimeMillis());
        gameContext.updateRoom(room);
    }

    protected void createRoom(Room room) {
        this.room = room;
        if (!gameContext.containsRoom(room.getId())) {
            gameContext.addSocket(socketId, room.getId());
            gameContext.setSocketAlive(socketId);
            gameContext.updateRoom(room);
        } else {
            reply(new BaseMessage(ERROR));
        }
    }

}
