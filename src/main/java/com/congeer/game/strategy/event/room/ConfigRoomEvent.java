package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.RoomEvent;
import com.congeer.game.strategy.model.ConfigRoomData;
import io.vertx.core.json.JsonObject;

import java.util.List;

import static com.congeer.game.enums.ClientEventEnum.SYNC_CONFIG;

/**
 * 房间设置事件
 */
public class ConfigRoomEvent extends RoomEvent<ConfigRoomData> {

    @Override
    protected void handleData(ConfigRoomData data) {
        Room room = getRoom();
        room.clearConfig();
        room.setMaxPlayer(data.getMaxPlayer());
        room.configPlayer();
        List<List<JsonObject>> playerConfig = data.getPlayerConfig();
        for (int i = 0; i < playerConfig.size(); i++) {
            List<JsonObject> jsonObjects = playerConfig.get(i);
            Player player = room.getPlayers().get(i);
            for (JsonObject config : jsonObjects) {
                player.getConfigList().add(config);
                if (player.getSocketId() != null) {
                    sendTo(player.getSocketId(), new BaseMessage(SYNC_CONFIG, config));
                }
            }
        }
        List<JsonObject> baseConfig = data.getBaseConfig();
        for (int i = 0; i < baseConfig.size(); i++) {
            JsonObject config = baseConfig.get(i);
            room.getConfigList().add(config);
            radio(new BaseMessage(SYNC_CONFIG, config));
        }
        room.setConfig(true);
        updateRoom();
    }

}
