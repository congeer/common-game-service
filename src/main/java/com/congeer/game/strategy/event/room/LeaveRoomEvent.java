package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;

import static com.congeer.game.strategy.enums.ClientEventEnum.LEAVE_PLAYER;

/**
 * 离开房间事件
 */
public class LeaveRoomEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        String socketId = body.getSocketId();
        gameContext.getRoomBySocketId(socketId).onSuccess(room ->{
            Player player = room.playerLeave(socketId);
            gameContext.removeSocket(socketId);
            if (player != null) {
                gameContext.radio(room, new Message(LEAVE_PLAYER, player.baseInfo()));
            }
        });
    }

}
