package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.enums.ClientEventEnum;
import com.congeer.game.strategy.RoomEvent;
import com.congeer.game.strategy.model.SeedData;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SeedCreateEvent extends RoomEvent<SeedData> {

    @Override
    protected void handleData(SeedData data) {
        List<Integer> result = getRandomArray(data.getStart(), data.getCount(), data.getStep());
        if (data.isResult()) {
            data.setData(result);
        }
        Room room = getRoom();
        room.setSeed(data.getCode(), result);
        updateRoom();
        radio(new BaseMessage(ClientEventEnum.SYNC_SEED, data), false);
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
