package com.congeer.game.strategy;

import com.congeer.game.strategy.model.GameContext;
import io.vertx.codegen.annotations.Fluent;

public abstract class GameEvent extends AbstractEvent {

    protected GameContext gameContext;

    @Fluent
    public GameEvent context(GameContext gameContext) {
        this.gameContext = gameContext;
        return this;
    }

}
