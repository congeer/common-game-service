package com.congeer.game.enums;

import com.congeer.game.event.AbstractEvent;
import com.congeer.game.event.RoomEvent;
import com.congeer.game.event.base.HealthEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseEventEnum implements IEventEnum {

    HEALTH("h", "健康检查", HealthEvent.class),
    ;

    private final String code;

    private final String name;

    private final Class<? extends AbstractEvent<?>> clz;

}
