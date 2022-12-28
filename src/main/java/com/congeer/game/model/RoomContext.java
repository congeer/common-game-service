package com.congeer.game.model;

import com.congeer.game.Application;
import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;

import static com.congeer.game.enums.ClientEventEnum.ERROR;

public class RoomContext extends EventContext {

    protected Room room;

    public Room getRoom() {
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
        room.allPlayer().stream().filter(v -> v.getSocketId() != null).filter(v -> !excludeSelf
            || !v.getSocketId()
            .equals(socketId)).forEach(v -> notice(v.getSocketId(), msg));
    }

    private void radioRoom(Room room, BaseMessage msg, boolean excludeSelf) {

    }

    public void updateRoom() {
        GameStorage gameStorage = Application.getGame();
        Player player = room.getPlayer(socketId);
        if (player != null) {
            gameStorage.addSocket(socketId, player);
        }
        room.setLastUpdateTime(System.currentTimeMillis());
        gameStorage.updateRoom(room);
    }

    public void createRoom(Room room) {
        GameStorage gameStorage = Application.getGame();
        this.room = room;
        gameStorage.getRoom(room.getId()).onFailure(cause -> {
            Player player = room.getPlayer(socketId);
            if (player != null) {
                gameStorage.addSocket(socketId, player);
            }
            room.setLastUpdateTime(System.currentTimeMillis());
            gameStorage.updateRoom(room);
        }).onSuccess(r -> {
            reply(new BaseMessage(ERROR));
        });
    }

}
