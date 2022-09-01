package com.congeer.game.strategy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientEventEnum {

    SYNC_ACTION("SYNC_ACTION", "同步动作"),
    SYNC_CONFIG("SYNC_CONFIG", "同步配置"),
    CONNECTED("CONNECTED", "已连接"),
    ROOM_INFO("ROOM_INFO", "房间信息"),
    LEAVE_PLAYER("LEAVE_PLAYER", "离开玩家"),
    JOIN_PLAYER("JOIN_PLAYER", "加入玩家"),
    RESET_ACTION("RESET_ACTION", "重置动作列表"),
    SHUFFLE_RESULT("SHUFFLE_RESULT", "洗牌结果"),
    ;

    private final String code;

    private final String name;


}
