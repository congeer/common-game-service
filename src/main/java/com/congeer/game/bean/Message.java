package com.congeer.game.bean;

import com.congeer.game.strategy.enums.ClientEventEnum;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Message {

    public Message(JsonObject json) {
        MessageConverter.fromJson(json, this);
    }

    public Message() {
    }

    public Message(ClientEventEnum type, Object data) {
        this.type = type.getCode();
        if (data instanceof JsonObject) {
            this.data = (JsonObject) data;
        } else {
            this.data = JsonObject.mapFrom(data);
        }
    }

    public Message(ClientEventEnum type) {
        this.type = type.getCode();
    }

    private String type;

    private JsonObject data;

    private String socketId;

    public String getType() {
        return type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }

    public JsonObject getData() {
        return data;
    }

    public Message setData(JsonObject data) {
        this.data = data;
        return this;
    }

    public <E> E getData(Class<E> clz) {
        return data.mapTo(clz);
    }

    public String getSocketId() {
        return socketId;
    }

    public Message setSocketId(String socketId) {
        this.socketId = socketId;
        return this;
    }

    @Override
    public String toString() {
        return JsonObject.mapFrom(this).toString();
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        MessageConverter.toJson(this, json);
        return json;
    }

}
