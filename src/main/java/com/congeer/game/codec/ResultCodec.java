package com.congeer.game.codec;

import com.congeer.game.bean.Result;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class ResultCodec implements MessageCodec<Result, JsonObject> {

    @Override
    public void encodeToWire(Buffer buffer, Result result) {
        Buffer encoded = JsonObject.mapFrom(result).toBuffer();
        buffer.appendInt(encoded.length());
        buffer.appendBuffer(encoded);
    }

    @Override
    public JsonObject decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        pos += 4;
        return new JsonObject(buffer.slice(pos, pos + length));
    }

    @Override
    public JsonObject transform(Result result) {
        return JsonObject.mapFrom(result);
    }

    @Override
    public String name() {
        return "req_result";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

}
