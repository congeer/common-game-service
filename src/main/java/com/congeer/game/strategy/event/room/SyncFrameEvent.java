package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;

import static com.congeer.game.strategy.enums.ClientEventEnum.SYNC_FRAME;

/**
 * 同步帧
 */
public class SyncFrameEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        gameContext.getRoomBySocketId(body.getSocketId()).onSuccess(room -> {
            room.getFrameList().add(body.getData());
            gameContext.radio(body.getSocketId(), new Message(SYNC_FRAME, body.getData()));
        });
    }

}
