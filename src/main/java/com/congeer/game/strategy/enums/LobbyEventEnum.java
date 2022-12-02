package com.congeer.game.strategy.enums;

import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.event.HealthEvent;
import com.congeer.game.strategy.event.lobby.RandomMatchEvent;
import com.congeer.game.strategy.event.lobby.RoomListEvent;
import com.congeer.game.strategy.event.room.AddRoomEvent;
import com.congeer.game.strategy.event.room.ConfigRoomEvent;
import com.congeer.game.strategy.event.room.LeaveRoomEvent;
import com.congeer.game.strategy.event.room.LockSeatEvent;
import com.congeer.game.strategy.event.room.ResetRoomEvent;
import com.congeer.game.strategy.event.room.SeedAllotEvent;
import com.congeer.game.strategy.event.room.SeedCreateEvent;
import com.congeer.game.strategy.event.room.SyncFrameEvent;
import com.congeer.game.strategy.event.room.UnlockSeatEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LobbyEventEnum {

    ROOM_LIST("ROOM_LIST", "房间列表", RoomListEvent.class),
    RANDOM_MATCH("RANDOM_MATCH", "随机匹配", RandomMatchEvent.class),
    ;


    private final String code;

    private final String name;

    private final Class<? extends GameEvent> clz;

}
