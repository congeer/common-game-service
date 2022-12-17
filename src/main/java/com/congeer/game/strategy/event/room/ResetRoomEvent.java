package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.RoomEvent;

import static com.congeer.game.enums.ClientEventEnum.RESET_ROOM;

/**
 * 重置房间事件
 */
public class ResetRoomEvent extends RoomEvent<Void> {

    @Override
    protected void handleData(Void body) {
        Room room = getRoom();
        room.resetRoom();
        updateRoom();
        radio(new BaseMessage(RESET_ROOM), false);
    }

}
