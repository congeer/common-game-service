package com.congeer.game.enums;

import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.event.lobby.RandomMatchEvent;
import com.congeer.game.strategy.event.lobby.RoomListEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LobbyEventEnum implements IEventEnum {

    ROOM_LIST("ROOM_LIST", "房间列表", RoomListEvent.class),
    RANDOM_MATCH("RANDOM_MATCH", "随机匹配", RandomMatchEvent.class),
    ;

    private final String code;

    private final String name;

    private final Class<? extends GameEvent<?>> clz;

}
