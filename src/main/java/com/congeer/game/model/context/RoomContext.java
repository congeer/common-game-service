package com.congeer.game.model.context;

import com.congeer.game.Application;
import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.storage.GameStorage;
import io.vertx.core.Promise;

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

    public void updateRoom() {
        GameStorage gameStorage = Application.getGame();
        Player player = room.getPlayer(socketId);
        if (player != null) {
            gameStorage.saveSocket(socketId, player);
        }
        room.setLastUpdateTime(System.currentTimeMillis());
        gameStorage.updateRoom(room);
    }

    public void createRoom(Room room) {
        GameStorage gameStorage = Application.getGame();
        this.room = room;
        Promise<Void> promise = Promise.promise();
        Room tmp = gameStorage.getRoom(room.getId());
        if (tmp == null) {
            Player player = room.getPlayer(socketId);
            if (player != null) {
                gameStorage.saveSocket(socketId, player);
            }
            room.setLastUpdateTime(System.currentTimeMillis());
            gameStorage.saveRoom(room);
        } else {
            reply(new BaseMessage(ERROR));
            promise.fail("has room");
        }
    }

}
