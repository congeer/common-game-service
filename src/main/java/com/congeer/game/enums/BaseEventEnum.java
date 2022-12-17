package com.congeer.game.enums;

import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.event.HealthEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseEventEnum implements IEventEnum {

    HEALTH("h", "健康检查", HealthEvent.class),
    ;

    private final String code;

    private final String name;

    private final Class<? extends GameEvent<?>> clz;

}
