package com.congeer.game.bean;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Message {

    public Message(JsonObject json) {
        MessageConverter.fromJson(json, this);
    }

    public Message() {

    }

    private String type;

    private String data;

    private String socketId;

    public String getType() {
        return type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }

    public String getData() {
        return data;
    }

    public Message setData(String data) {
        this.data = data;
        return this;
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
        JsonObject json = new JsonObject();
        MessageConverter.toJson(this, json);
        return json.toString();
    }

}
