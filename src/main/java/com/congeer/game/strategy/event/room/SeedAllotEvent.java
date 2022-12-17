package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.enums.ClientEventEnum;
import com.congeer.game.strategy.RoomEvent;

public class SeedAllotEvent extends RoomEvent<Void> {

    @Override
    protected void handleData(Void body) {
//        SeedData data = body.getData(SeedData.class);
//        Room room = gameContext.getRoomBySocketId(body.getSocketId());
//        int seat = data.getSeat();
//        for (int i = 0; i < data.getCount(); i++) {
//            data.setSeat(seat++);
//            if (seat >= room.getMaxPlayer()) {
//                seat = 0;
//            }
//            data.setIndex(room.pullSeed(data.getCode()));
//        }
        radio(new BaseMessage(ClientEventEnum.SEED_ALLOTTED), false);
    }

}
