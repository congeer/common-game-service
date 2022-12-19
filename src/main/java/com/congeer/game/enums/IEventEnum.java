package com.congeer.game.enums;

import com.congeer.game.event.AbstractEvent;

public interface IEventEnum {

    String getName();

    String getCode();

    Class<? extends AbstractEvent<?>> getClz();

}
