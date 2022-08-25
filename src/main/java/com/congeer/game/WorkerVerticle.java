package com.congeer.game;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorkerVerticle extends AbstractVerticle {

    private final Map<String, Room> roomMap = new HashMap<>();

    private final Map<String, String> socketRoom = new HashMap<>();

    @Override
    public void start() throws Exception {
        System.out.println(Thread.currentThread().getName() + ", Start Worker...");
        EventBus eventBus = getVertx().eventBus();
        eventBus.<JsonObject>consumer("websocket.handler", handler -> {
            JsonObject entries = handler.body();
            Message message = new Message(entries);
            String socketId = message.getSocketId();
            Room room = getRoomBySocket(socketId);
            switch (message.getType()) {
                case "addRoom":
                    addRoom(message);
                    break;
                case "configRoom":
                    configRoom(message, socketId, room);
                    break;
                case "resetRoom":
                    room.reset();
                    break;
                case "syncAction":
                    syncAction(room, entries, socketId);
                    break;
            }
        });
        eventBus.<String>consumer("websocket.end", handler -> {
            String socketId = handler.body();
            Room room = getRoomBySocket(socketId);
            int type = room.playerLeave(socketId);
            if ((type | 2) == 2) {
                room.getAll().stream().filter(v -> v.getSocketId() != null).filter(v -> !v.getSocketId()
                    .equals(socketId)).forEach(v -> {
                    JsonObject entries = new JsonObject();
                    entries.put("type", "leavePlayer");
                    entries.put("info", type);
                    sendData(v.getSocketId(), entries);
                });
            }
            socketRoom.remove(socketId);
        });
    }

    private void syncAction(Room room, JsonObject entries, String socketId) {
        room.getActionList().add(entries);
        room.getAll().stream().filter(v -> v.getSocketId() != null).filter(v -> !v.getSocketId().equals(socketId))
            .forEach(v -> sendData(v.getSocketId(), entries));
    }

    private void configRoom(Message message, String socketId, Room room) {
        JsonObject config = new JsonObject(message.getData());
        room.clearConfig();
        Integer maxPlayer = config.getInteger("maxPlayer");
        room.setMaxPlayer(maxPlayer);
        JsonArray playerConfig = config.getJsonArray("playerConfig");
        for (int i = 0; i < playerConfig.size(); i++) {
            JsonArray jsonArray = playerConfig.getJsonArray(i);
            Player player = room.getPlayers().get(i);
            for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                JsonObject jsonObject = jsonArray.getJsonObject(i1);
                player.getConfigList().add(jsonObject);
                if (player.getSocketId() != null) {
                    sendConfig(player.getSocketId(), jsonObject);
                }
            }
        }
        JsonArray baseConfig = config.getJsonArray("baseConfig");
        for (int i = 0; i < baseConfig.size(); i++) {
            JsonObject jsonObject = baseConfig.getJsonObject(i);
            room.getConfigList().add(jsonObject);
            room.getAll().stream().filter(v -> v.getSocketId() != null).filter(v -> !v.getSocketId().equals(socketId))
                .forEach(v -> sendConfig(v.getSocketId(), jsonObject));
        }
    }

    private Room getRoomBySocket(String socketId) {
        String roomId = socketRoom.get(socketId);
        return roomMap.get(roomId);
    }


    private void addRoom(Message message) {
        String socketId = message.getSocketId();
        JsonObject info = new JsonObject(message.getData());
        String roomId = info.getString("roomId");
        String playerId = info.getString("playerId");
        socketRoom.put(socketId, roomId);
        if (!roomMap.containsKey(roomId)) {
            JsonObject type = new JsonObject();
            Room room = new Room(roomId);
            type.put("type", "isOwner");
            roomMap.put(roomId, room);
            Player player = new Player(playerId, socketId);
            room.setOwner(player);
            room.getPlayers().add(player);
            // 通知玩家所属类型
            sendData(message.getSocketId(), type);
            return;
        }
        Room room = roomMap.get(roomId);
        Optional<Player> first = room.getPlayers().stream().filter(v -> v.getSocketId() == null).findFirst();
        JsonObject type = new JsonObject();
        if (first.isPresent()) {
            Player player = first.get();
            type.put("type", "isPlayer");
            type.put("index", room.getPlayers().indexOf(player));
            player.setId(playerId);
            player.setSocketId(socketId);
            type.put("players", room.getPlayers().stream().filter(v -> v.getSocketId() != null).count());
            // 通知其他人有玩家加入
            room.getAll().stream().filter(v -> v.getSocketId() != null).filter(v -> !socketId.equals(v.getSocketId()))
                .forEach(v -> {
                    JsonObject entries = new JsonObject();
                    entries.put("type", "joinPlayer");
                    sendData(v.getSocketId(), entries);
                });
        } else {
            type.put("type", "isAudience");
            Player player = new Player(playerId, socketId);
            room.getViewers().add(player);
        }
        // 通知玩家所属类型
        sendData(message.getSocketId(), type);

        // 同步全局配置
        for (JsonObject config : room.getConfigList()) {
            sendConfig(message.getSocketId(), config);
        }
        // 同步玩家配置
        if (first.isPresent()) {
            for (JsonObject entries : first.get().getConfigList()) {
                sendConfig(first.get().getSocketId(), entries);
            }
        }
        // 同步所有操作
        for (JsonObject action : room.getActionList()) {
            sendData(message.getSocketId(), action);
        }
    }

    private void sendData(String socketId, JsonObject data) {
        System.out.println(data);
        getVertx().eventBus().send(socketId, data.toBuffer());
    }

    private void sendConfig(String socketId, JsonObject data) {
        JsonObject config = new JsonObject();
        config.put("type", "syncAction");
        config.put("data", data.toString());
        getVertx().eventBus().send(socketId, config.toBuffer());
    }

}
