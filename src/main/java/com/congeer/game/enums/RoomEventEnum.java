package com.congeer.game.enums;

import com.congeer.game.strategy.RoomEvent;
import com.congeer.game.strategy.event.HealthEvent;
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
public enum RoomEventEnum implements IEventEnum {

    HEALTH("h", "健康检查", HealthEvent.class),
    ADD_ROOM("ADD_ROOM", "进入房间", AddRoomEvent.class),
    LEAVE_ROOM("LEAVE_ROOM", "离开房间", LeaveRoomEvent.class),
    CONFIG_ROOM("CONFIG_ROOM", "设置房间", ConfigRoomEvent.class),
    RESET_ROOM("RESET_ROOM", "重置房间", ResetRoomEvent.class),
    SYNC_FRAME("SYNC_FRAME", "帧同步", SyncFrameEvent.class),
    LOCK_SEAT("LOCK_SEAT", "锁定座位", LockSeatEvent.class),
    UNLOCK_SEAT("UNLOCK_SEAT", "解锁座位", UnlockSeatEvent.class),
    SEED_CREATE("SEED_CREATE", "生成种子", SeedCreateEvent.class),
    SEED_ALLOT("SEED_ALLOT", "分配种子", SeedAllotEvent.class),
    ;

    private final String code;

    private final String name;

    private final Class<? extends RoomEvent<?>> clz;

}
