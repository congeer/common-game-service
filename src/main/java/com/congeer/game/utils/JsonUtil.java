package com.congeer.game.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static ObjectMapper objectMapper =new ObjectMapper();
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String val, Class<T> clz) {
        try {
            return objectMapper.readValue(val, clz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
