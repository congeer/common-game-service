package com.congeer.game.strategy.request;

import com.congeer.game.bean.Result;
import com.congeer.game.strategy.GameRequest;
import com.congeer.game.strategy.model.GameStatus;

public class ServiceStatusRequest extends GameRequest<Void, GameStatus> {

    @Override
    protected Result<GameStatus> handleBody(Void body) {
        return new Result<>(gameContext.getStatus());
    }

}
