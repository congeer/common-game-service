package com.congeer.game.enums;

import com.congeer.game.bean.BaseMessage;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum RouteEnum {

    STATUS(HttpMethod.GET, "/game/status", context -> null)
    ;

    private final HttpMethod method;

    private final String path;

    private final Function<RoutingContext, BaseMessage> data;


}
