package com.congeer.game.request.base;

import com.congeer.game.Application;
import com.congeer.game.bean.Result;
import com.congeer.game.model.GameStatus;
import com.congeer.game.request.AbstractRequest;

public class ServiceStatusRequest extends AbstractRequest<Void, GameStatus> {

    @Override
    protected Result<GameStatus> handleBody(Void body) {
        return new Result<>(Application.getGame().getStatus());
    }

}
