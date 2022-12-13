package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;

import static com.congeer.game.strategy.enums.ClientEventEnum.RESET_ROOM;

/**
 * 重置房间事件
 */
public class ResetRoomEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        gameContext.getRoomBySocketId(body.getSocketId()).onSuccess(room -> {
            room.resetRoom();
            gameContext.radio(body.getSocketId(), new Message(RESET_ROOM), false);
        });
    }

}
