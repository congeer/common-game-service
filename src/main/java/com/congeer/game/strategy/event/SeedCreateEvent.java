package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.enums.ClientEventEnum;
import com.congeer.game.strategy.model.SeedData;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SeedCreateEvent extends GameEvent {

    @Override
    protected void handle(Message body) {
        SeedData data = body.getData(SeedData.class);
        List<Integer> result = getRandomArray(data.getStart(), data.getCount(), data.getStep());
        if (data.isResult()) {
            data.setData(result);
        }
        Room room = gameContext.getRoomBySocketId(body.getSocketId());
        if (room != null) {
            room.setSeed(data.getCode(), result);
        }
        gameContext.radio(body.getSocketId(), new Message(ClientEventEnum.SYNC_SEED, data), false);
    }

    public List<Integer> getRandomArray(int start, int length, int step) {
        int[] source = new int[length];
        for (int i = 0; i < length; i++) {
            source[i] = i;
        }
        Integer[] result = new Integer[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int bound = length - 1 - i;
            int randomIndex = bound;
            if (bound != 0) {
                randomIndex = random.nextInt(bound);
            }
            int randomRes = source[randomIndex];
            result[i] = randomRes * step + start;
            int temp = source[randomIndex];
            source[randomIndex] = source[bound];
            source[bound] = temp;
        }
        return Arrays.asList(result);
    }

}
