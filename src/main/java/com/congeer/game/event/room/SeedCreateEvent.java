package com.congeer.game.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Room;
import com.congeer.game.model.context.SeedContext;
import com.congeer.game.enums.ClientEventEnum;
import com.congeer.game.event.RoomEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SeedCreateEvent extends RoomEvent<SeedContext> {

    @Override
    protected void handleRoom(SeedContext context) {
        List<Integer> result = getRandomArray(context.getStart(), context.getCount(), context.getStep());
        if (context.isResult()) {
            context.setData(result);
        }
        Room room = context.getRoom();
        room.setSeed(context.getCode(), result);
    }

    @Override
    protected void replyData(SeedContext context) {
        context.radio(new BaseMessage(ClientEventEnum.SYNC_SEED, context), false);
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
