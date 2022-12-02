package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.enums.ClientEventEnum;
import com.congeer.game.strategy.model.SeedData;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SeedAllotEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
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
        gameContext.radio(body.getSocketId(), new Message(ClientEventEnum.SEED_ALLOTTED), false);
    }

}
