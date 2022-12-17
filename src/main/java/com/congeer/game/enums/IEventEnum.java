package com.congeer.game.enums;

import com.congeer.game.strategy.AbstractEvent;

public interface IEventEnum {

    String getName();

    String getCode();

    Class<? extends AbstractEvent<?>> getClz();

}
