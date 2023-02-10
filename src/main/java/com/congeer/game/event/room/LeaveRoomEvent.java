package com.congeer.game.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.context.LeaveRoomContext;
import com.congeer.game.model.context.RoomContext;
import com.congeer.game.event.RoomEvent;

import static com.congeer.game.enums.ClientEventEnum.LEAVE_PLAYER;

/**
 * 离开房间事件
 */
public class LeaveRoomEvent extends RoomEvent<LeaveRoomContext> {

    @Override
    protected void handleRoom(LeaveRoomContext context) {
        context.removeSocket();
        Room room = context.getRoom();
        if (room == null) {
            return;
        }
        Player player = room.playerLeave(context.getSocketId());
        context.setLeavePlayer(player);
    }

    @Override
    protected void replyData(LeaveRoomContext context) {
        Player player = context.getLeavePlayer();
        if (player != null) {
            context.radio(new BaseMessage(LEAVE_PLAYER, player.baseInfo()));
        }
    }

}
