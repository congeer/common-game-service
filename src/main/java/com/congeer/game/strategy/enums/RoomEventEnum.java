package com.congeer.game.strategy.enums;

import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.event.AddRoomEvent;
import com.congeer.game.strategy.event.ConfigRoomEvent;
import com.congeer.game.strategy.event.HealthEvent;
import com.congeer.game.strategy.event.LeaveRoomEvent;
import com.congeer.game.strategy.event.LockPlayerEvent;
import com.congeer.game.strategy.event.RequestActionEvent;
import com.congeer.game.strategy.event.ResetActionEvent;
import com.congeer.game.strategy.event.SyncActionEvent;
import com.congeer.game.strategy.event.UnlockPlayerEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomEventEnum {

    HEALTH("h", "健康检查", HealthEvent.class),
    ADD_ROOM("ADD_ROOM", "进入房间", AddRoomEvent.class),
    LEAVE_ROOM("LEAVE_ROOM", "离开房间", LeaveRoomEvent.class),
    CONFIG_ROOM("CONFIG_ROOM", "设置房间", ConfigRoomEvent.class),
    RESET_ACTION("RESET_ACTION", "重置动作列表", ResetActionEvent.class),
    SYNC_ACTION("SYNC_ACTION", "同步动作", SyncActionEvent.class),
    REQUEST_ACTION("REQUEST_ACTION", "请求动作", RequestActionEvent.class),
    LOCK_PLAYER("LOCK_PLAYER", "锁定玩家位置", LockPlayerEvent.class),
    UNLOCK_PLAYER("UNLOCK_PLAYER", "解锁玩家位置", UnlockPlayerEvent.class),
    ;


    private final String code;

    private final String name;

    private final Class<? extends GameEvent> clz;

}
