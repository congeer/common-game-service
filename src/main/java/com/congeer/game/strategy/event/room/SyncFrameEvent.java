package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.RoomEvent;
import io.vertx.core.json.JsonObject;

import static com.congeer.game.enums.ClientEventEnum.SYNC_FRAME;

/**
 * 同步帧
 */
public class SyncFrameEvent extends RoomEvent<String> {

    @Override
    protected void handleData(String data) {
        Room room = getRoom();
        room.appendFrame(new JsonObject(data));
        updateRoom();
        radio(new BaseMessage(SYNC_FRAME, data));
    }

}
