package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.model.ConfigRoomData;
import io.vertx.core.json.JsonObject;

import java.util.List;

import static com.congeer.game.strategy.enums.ClientEventEnum.SYNC_CONFIG;

/**
 * 房间设置事件
 */
public class ConfigRoomEvent extends GameEvent {

    @Override
    protected void handle(Message message) {
        String socketId = message.getSocketId();
        Room room = gameContext.getRoomBySocketId(socketId);
        room.clearConfig();
        ConfigRoomData data = message.getData(ConfigRoomData.class);
        room.setMaxPlayer(data.getMaxPlayer());
        room.configPlayer();
        List<List<JsonObject>> playerConfig = data.getPlayerConfig();
        for (int i = 0; i < playerConfig.size(); i++) {
            List<JsonObject> jsonObjects = playerConfig.get(i);
            Player player = room.getPlayers().get(i);
            for (JsonObject config : jsonObjects) {
                player.getConfigList().add(config);
                if (player.getSocketId() != null) {
                    gameContext.notice(player.getSocketId(), new Message(SYNC_CONFIG, config));
                }
            }
        }
        List<JsonObject> baseConfig = data.getBaseConfig();
        for (int i = 0; i < baseConfig.size(); i++) {
            JsonObject config = baseConfig.get(i);
            room.getConfigList().add(config);
            gameContext.radio(socketId, new Message(SYNC_CONFIG, config));
        }
        room.setConfig(true);
    }

}
