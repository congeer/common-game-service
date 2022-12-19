package com.congeer.game.enums;

import com.congeer.game.event.RoomEvent;
import com.congeer.game.event.base.HealthEvent;
import com.congeer.game.event.room.AddRoomEvent;
import com.congeer.game.event.room.ConfigRoomEvent;
import com.congeer.game.event.room.LeaveRoomEvent;
import com.congeer.game.event.room.LockSeatEvent;
import com.congeer.game.event.room.ResetRoomEvent;
import com.congeer.game.event.room.SeedAllotEvent;
import com.congeer.game.event.room.SeedCreateEvent;
import com.congeer.game.event.room.SyncFrameEvent;
import com.congeer.game.event.room.UnlockSeatEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomEventEnum implements IEventEnum {

    ADD_ROOM("add", "进入房间", AddRoomEvent.class),
    LEAVE_ROOM("leave", "离开房间", LeaveRoomEvent.class),
    CONFIG_ROOM("config", "设置房间", ConfigRoomEvent.class),
    RESET_ROOM("reset", "重置房间", ResetRoomEvent.class),
    SYNC_FRAME("frame.sync", "帧同步", SyncFrameEvent.class),
    LOCK_SEAT("seat.lock", "锁定座位", LockSeatEvent.class),
    UNLOCK_SEAT("seat.unlock", "解锁座位", UnlockSeatEvent.class),
    SEED_CREATE("seed.create", "生成种子", SeedCreateEvent.class),
    SEED_ALLOT("seed.allot", "分配种子", SeedAllotEvent.class),
    ;

    private final String code;

    private final String name;

    private final Class<? extends RoomEvent<?>> clz;

}
