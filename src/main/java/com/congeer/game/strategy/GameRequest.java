package com.congeer.game.strategy;

import com.congeer.game.strategy.model.GameContext;
import io.vertx.codegen.annotations.Fluent;

public abstract class GameRequest<T, R> extends AbstractRequest<T, R> {

    protected GameContext gameContext;

    @Fluent
    public GameRequest<T, R> context(GameContext gameContext) {
        this.gameContext = gameContext;
        return this;
    }

}
