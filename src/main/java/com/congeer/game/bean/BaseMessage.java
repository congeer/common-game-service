package com.congeer.game.bean;

import com.congeer.game.model.context.StringContext;
import com.congeer.game.enums.ClientEventEnum;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;

public class BaseMessage {

    public BaseMessage() {
    }

    public BaseMessage(ClientEventEnum type, Object data) {
        this.type = type.getCode();
        if (data instanceof JsonObject) {
            this.data = (JsonObject) data;
        } else if (data instanceof String) {
            this.data = new JsonObject((String) data);
        } else {
            this.data = JsonObject.mapFrom(data);
        }
    }

    public BaseMessage(ClientEventEnum type) {
        this.type = type.getCode();
    }

    private final long timestamp = System.currentTimeMillis();

    private String type;

    private JsonObject data;

    private String socketId;

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public BaseMessage setType(String type) {
        this.type = type;
        return this;
    }

    public String getData() {
        if (data != null) {
            return data.toString();
        } else {
            return null;
        }
    }

    public BaseMessage setData(String data) {
        this.data = new JsonObject(data);
        return this;
    }

    public <E> E getData(Class<E> clz) {
        if (data == null) {
            try {
                return clz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (Arrays.stream(clz.getInterfaces()).anyMatch(v -> v == StringContext.class)) {
            try {
                E e = clz.getDeclaredConstructor().newInstance();
                ((StringContext) e).setData(data.toString());
                return e;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return data.mapTo(clz);
    }

    public String getSocketId() {
        return socketId;
    }

    public BaseMessage setSocketId(String socketId) {
        this.socketId = socketId;
        return this;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }

}
