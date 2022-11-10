package com.congeer.game.strategy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientEventEnum {

    SYNC_FRAME("SYNC_FRAME", "帧同步"),
    SYNC_CONFIG("SYNC_CONFIG", "同步配置"),
    CONNECTED("CONNECTED", "已连接"),
    ROOM_INFO("ROOM_INFO", "房间信息"),
    LEAVE_PLAYER("LEAVE_PLAYER", "离开玩家"),
    JOIN_PLAYER("JOIN_PLAYER", "加入玩家"),
    RESET_ROOM("RESET_ROOM", "重置房间"),
    SYNC_SEED("SYNC_SEED", "种子生成完成"),
    SEED_ALLOTTED("SEED_ALLOTTED", "种子返回结果"),
    ;

    private final String code;

    private final String name;


}
