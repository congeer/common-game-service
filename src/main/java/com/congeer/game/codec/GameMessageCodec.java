package com.congeer.game.codec;

import com.congeer.game.bean.Message;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class GameMessageCodec implements MessageCodec<Message, Message> {

    @Override
    public void encodeToWire(Buffer buffer, Message message) {
        Buffer encoded = message.toJson().toBuffer();
        buffer.appendInt(encoded.length());
        buffer.appendBuffer(encoded);
    }

    @Override
    public Message decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        pos += 4;
        return new Message(new JsonObject(buffer.slice(pos, pos + length)));
    }

    @Override
    public Message transform(Message jsonObject) {
        return new Message(jsonObject.toJson().copy());
    }

    @Override
    public String name() {
        return "game_message";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

}
