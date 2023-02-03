package com.congeer.game.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.model.context.RoomContext;
import com.congeer.game.enums.ClientEventEnum;
import com.congeer.game.event.RoomEvent;

public class SeedAllotEvent extends RoomEvent<RoomContext> {

    @Override
    protected void handleRoom(RoomContext context) {
//        SeedContext data = body.getData(SeedContext.class);
//        Room room = gameStorage.getRoomBySocketId(body.getSocketId());
//        int seat = data.getSeat();
//        for (int i = 0; i < data.getCount(); i++) {
//            data.setSeat(seat++);
//            if (seat >= room.getMaxPlayer()) {
//                seat = 0;
//            }
//            data.setIndex(room.pullSeed(data.getCode()));
//        }
        context.radio(new BaseMessage(ClientEventEnum.SEED_ALLOTTED), false);
    }

}
