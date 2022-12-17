package com.congeer.game.strategy.event.room;

import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.RoomEvent;
import com.congeer.game.strategy.model.AddRoomData;
import com.congeer.game.strategy.model.SeedData;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

import static com.congeer.game.enums.ClientEventEnum.CONNECTED;
import static com.congeer.game.enums.ClientEventEnum.JOIN_PLAYER;
import static com.congeer.game.enums.ClientEventEnum.ROOM_INFO;
import static com.congeer.game.enums.ClientEventEnum.SYNC_CONFIG;
import static com.congeer.game.enums.ClientEventEnum.SYNC_FRAME;
import static com.congeer.game.enums.ClientEventEnum.SYNC_SEED;

/**
 * 加入房间事件
 */
public class AddRoomEvent extends RoomEvent<AddRoomData> {

    @Override
    protected void handleData(AddRoomData data) {
        String playerId = data.getPlayerId();
        String roomId = data.getRoomId();
        if (!gameContext.containsRoom(roomId)) {
            Room room = new Room(roomId);
            Player player = createOwnerPlayer(socketId, playerId).setWhere(room);
            room.setOwner(player).getPlayers().add(player);
            createRoom(room);
            // 通知玩家自己的属性
            reply(new BaseMessage(CONNECTED, player.baseInfo().setCreate(true)));
        } else {
            Room room = getRoom();
            Player player = room.getEmptySeat(playerId);
            if (player != null) {
                player.setId(playerId).setSocketId(socketId).setLock(true);
            } else {
                player = new Player(playerId, socketId).setWhere(room);
                room.getViewers().add(player);
            }
            // 告诉玩家自己的状态
            reply(new BaseMessage(CONNECTED, player.baseInfo()
                .setCreate(player.isOwner() && !room.isConfig())));
            // 告诉玩家其他玩家状态
            reply(new BaseMessage(ROOM_INFO, room.baseInfo()));
            // 通知其他人有玩家加入
            radio(new BaseMessage(JOIN_PLAYER, player.baseInfo()));

            // 1、同步全局配置
            for (JsonObject config : room.getConfigList()) {
                reply(new BaseMessage(SYNC_CONFIG, config));
            }
            // 2、同步玩家配置
            if (!player.getConfigList().isEmpty()) {
                for (JsonObject config : player.getConfigList()) {
                    reply(new BaseMessage(SYNC_CONFIG, config));
                }
            }
            // 3、同步所有随机种子给玩家
            for (Map.Entry<String, List<Integer>> entry : room.getSeedMap().entrySet()) {
                SeedData seed = new SeedData();
                seed.setCode(entry.getKey());
                seed.setData(entry.getValue());
                reply(new BaseMessage(SYNC_SEED, seed));
            }
            // 4、同步所有操作
            for (JsonObject action : room.getFrameList()) {
                reply(new BaseMessage(SYNC_FRAME, action));
            }
            updateRoom();
        }
    }

    private static Player createOwnerPlayer(String socketId, String playerId) {
        Player player = new Player(playerId, socketId);
        return player.setOwner(true).setPlayer(true).setIndex(0).setLock(true);
    }

}
