package com.congeer.game.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.RoomContext;
import com.congeer.game.event.RoomEvent;

import static com.congeer.game.enums.ClientEventEnum.LEAVE_PLAYER;

/**
 * 离开房间事件
 */
public class LeaveRoomEvent extends RoomEvent<RoomContext> {

    @Override
    protected void handleRoom(RoomContext context) {
        Room room = context.getRoom();
        if (room == null) {
            return;
        }
        Player player = room.playerLeave(context.getSocketId());
        context.removeSocket();
        if (player != null) {
            context.radio(new BaseMessage(LEAVE_PLAYER, player.baseInfo()));
        }
    }

}
