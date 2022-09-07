package com.congeer.game.strategy.enums;

import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.event.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomEventEnum {

    HEALTH("h", "健康检查", HealthEvent.class),
    ADD_ROOM("ADD_ROOM", "进入房间", AddRoomEvent.class),
    LEAVE_ROOM("LEAVE_ROOM", "离开房间", LeaveRoomEvent.class),
    CONFIG_ROOM("CONFIG_ROOM", "设置房间", ConfigRoomEvent.class),
    RESET_ROOM("RESET_ROOM", "重置房间", ResetRoomEvent.class),
    SYNC_ACTION("SYNC_ACTION", "同步动作", SyncActionEvent.class),
    REQUEST_ACTION("REQUEST_ACTION", "请求动作", RequestActionEvent.class),
    LOCK_PLAYER("LOCK_PLAYER", "锁定玩家位置", LockPlayerEvent.class),
    UNLOCK_PLAYER("UNLOCK_PLAYER", "解锁玩家位置", UnlockPlayerEvent.class),
    SEED_CREATE("SEED_CREATE", "生成种子", SeedCreateEvent.class),
    SEED_ALLOT("SEED_ALLOT", "发牌", SeedAllotEvent.class),
    ;


    private final String code;

    private final String name;

    private final Class<? extends GameEvent> clz;

}
