package com.congeer.game.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;
import com.congeer.game.model.context.RoomContext;
import com.congeer.game.event.RoomEvent;

import static com.congeer.game.enums.ClientEventEnum.RESET_ROOM;

/**
 * 重置房间事件
 */
public class ResetRoomEvent extends RoomEvent<RoomContext> {

    @Override
    protected void handleRoom(RoomContext context) {
        Room room = context.getRoom();
        room.resetRoom();
        context.radio(new BaseMessage(RESET_ROOM), false);
    }

}
