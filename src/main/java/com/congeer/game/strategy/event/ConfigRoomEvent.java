package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.model.ConfigRoomData;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import static com.congeer.game.strategy.enums.ClientEventEnum.SYNC_CONFIG;

public class ConfigRoomEvent extends GameEvent {

    @Override
    protected void handle(Message message) {
        String socketId = message.getSocketId();
        Room room = gameContext.getRoomBySocketId(socketId);
        room.clearConfig();
        ConfigRoomData data = message.getData(ConfigRoomData.class);
        room.setMaxPlayer(data.getMaxPlayer());
        room.configPlayer();
        JsonArray playerConfig = data.getPlayerConfig();
        for (int i = 0; i < playerConfig.size(); i++) {
            JsonArray jsonArray = playerConfig.getJsonArray(i);
            Player player = room.getPlayers().get(i);
            for (int j = 0; j < jsonArray.size(); j++) {
                JsonObject config = jsonArray.getJsonObject(j);
                player.getConfigList().add(config);
                if (player.getSocketId() != null) {
                    gameContext.notice(player.getSocketId(), new Message(SYNC_CONFIG, config));
                }
            }
        }
        JsonArray baseConfig = data.getBaseConfig();
        for (int i = 0; i < baseConfig.size(); i++) {
            JsonObject config = baseConfig.getJsonObject(i);
            room.getConfigList().add(config);
            gameContext.radio(socketId, new Message(SYNC_CONFIG, config));
        }
    }

}
