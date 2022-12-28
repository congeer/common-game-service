package com.congeer.game.request.base;

import com.congeer.game.Application;
import com.congeer.game.bean.Result;
import com.congeer.game.model.GameStatus;
import com.congeer.game.request.AbstractRequest;
import io.vertx.core.Future;

public class ServiceStatusRequest extends AbstractRequest<Void, GameStatus> {

    @Override
    protected Future<GameStatus> handleBody(Void body) {
        return Application.getGame().getStatus();
    }

}
