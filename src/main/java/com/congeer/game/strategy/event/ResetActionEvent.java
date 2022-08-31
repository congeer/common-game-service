package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;

import static com.congeer.game.strategy.enums.ClientEventEnum.RESET_ACTION;

public class ResetActionEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        Room room = gameContext.getRoom(body.getSocketId());
        room.clearConfig();
        gameContext.radio(body.getSocketId(), new Message(RESET_ACTION));
    }

}
