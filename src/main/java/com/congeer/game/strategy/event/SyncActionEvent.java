package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;

import static com.congeer.game.strategy.enums.ClientEventEnum.*;

public class SyncActionEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        Room room = gameContext.getRoomBySocketId(body.getSocketId());
        room.getActionList().add(body.getData());
        gameContext.radio(body.getSocketId(), new Message(SYNC_ACTION, body.getData()));
    }

}
