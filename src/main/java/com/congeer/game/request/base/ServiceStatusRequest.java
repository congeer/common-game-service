package com.congeer.game.request.base;

import com.congeer.game.Application;
import com.congeer.game.model.GameStatus;
import com.congeer.game.request.AbstractRequest;

public class ServiceStatusRequest extends AbstractRequest<Void, GameStatus> {

    @Override
    protected GameStatus handleBody(Void body) {
        return Application.getGame().getStatus();
    }

}
