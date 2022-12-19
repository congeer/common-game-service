package com.congeer.game.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;
import com.congeer.game.model.RoomStringContext;
import com.congeer.game.event.RoomEvent;
import io.vertx.core.json.JsonObject;

import static com.congeer.game.enums.ClientEventEnum.SYNC_FRAME;

/**
 * 同步帧
 */
public class SyncFrameEvent extends RoomEvent<RoomStringContext> {

    @Override
    protected void handleRoom(RoomStringContext context) {
        Room room = context.getRoom();
        room.appendFrame(new JsonObject(context.getData()));
        context.updateRoom();
        context.radio(new BaseMessage(SYNC_FRAME, context.getData()));
    }

}