package com.congeer.game.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.event.RoomEvent;
import com.congeer.game.model.context.ConfigRoomContext;

import java.util.List;

import static com.congeer.game.enums.ClientEventEnum.SYNC_CONFIG;

/**
 * 房间设置事件
 */
public class ConfigRoomEvent extends RoomEvent<ConfigRoomContext> {

    @Override
    protected void handleRoom(ConfigRoomContext context) {
        Room room = context.getRoom();
        room.clearConfig();
        room.setMaxPlayer(context.getMaxPlayer());
        List<String> baseConfig = context.getBaseConfig();
        for (int i = 0; i < baseConfig.size(); i++) {
            String config = baseConfig.get(i);
            room.getConfigList().add(config);
            context.radio(new BaseMessage(SYNC_CONFIG, config), false);
        }
        room.configPlayer();
        List<List<String>> playerConfig = context.getPlayerConfig();
        for (int i = 0; i < playerConfig.size(); i++) {
            List<String> jsonObjects = playerConfig.get(i);
            Player player = room.getPlayers().get(i);
            for (String config : jsonObjects) {
                player.getConfigList().add(config);
                if (player.getSocketId() != null) {
                    context.sendTo(player.getSocketId(), new BaseMessage(SYNC_CONFIG, config));
                }
            }
        }
        room.setConfig(true);
    }

}
