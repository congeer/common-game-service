package com.congeer.game.enums;

import com.congeer.game.event.AbstractEvent;
import com.congeer.game.event.lobby.RandomMatchEvent;
import com.congeer.game.event.lobby.RoomListEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum LobbyEventEnum implements IEventEnum {

    ROOM_LIST("list.room", "房间列表", RoomListEvent.class),
    RANDOM_MATCH("match.random", "随机匹配", RandomMatchEvent.class),
    ;

    private final String code;

    private final String name;

    private final Class<? extends AbstractEvent<?>> clz;

}
