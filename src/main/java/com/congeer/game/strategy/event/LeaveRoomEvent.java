package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;

import static com.congeer.game.strategy.enums.ClientEventEnum.LEAVE_PLAYER;

public class LeaveRoomEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        String socketId = body.getSocketId();
        Room room = gameContext.getRoomBySocketId(socketId);
        if (room == null) {
            return;
        }
        Player player = room.playerLeave(socketId);
        gameContext.removeSocket(socketId);
        if (player != null) {
            gameContext.radio(socketId, new Message(LEAVE_PLAYER, player.baseInfo()));
        }
    }

}
