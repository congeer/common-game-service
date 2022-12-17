package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.RoomEvent;

import static com.congeer.game.enums.ClientEventEnum.LEAVE_PLAYER;

/**
 * 离开房间事件
 */
public class LeaveRoomEvent extends RoomEvent<Void> {

    @Override
    protected void handleData(Void body) {
        Room room = getRoom();
        if (room == null) {
            return;
        }
        Player player = room.playerLeave(socketId);
        removeSocket();
        if (player != null) {
            radio(new BaseMessage(LEAVE_PLAYER, player.baseInfo()));
        }
    }

}
