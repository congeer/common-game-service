package com.congeer.game.codec;

import com.congeer.game.bean.BaseMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class BaseMessageCodec implements MessageCodec<BaseMessage, BaseMessage> {

    @Override
    public void encodeToWire(Buffer buffer, BaseMessage message) {
        Buffer encoded = message.toJson().toBuffer();
        buffer.appendInt(encoded.length());
        buffer.appendBuffer(encoded);
    }

    @Override
    public BaseMessage decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        pos += 4;
        return new JsonObject(buffer.slice(pos, pos + length)).mapTo(BaseMessage.class);
    }

    @Override
    public BaseMessage transform(BaseMessage source) {
        return source.toJson().copy().mapTo(BaseMessage.class);
    }

    @Override
    public String name() {
        return "base_message";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

}
