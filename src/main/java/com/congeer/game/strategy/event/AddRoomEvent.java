package com.congeer.game.strategy.event;

import com.congeer.game.bean.Message;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.strategy.GameEvent;
import com.congeer.game.strategy.model.AddRoomData;
import com.congeer.game.strategy.model.SeedData;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

import static com.congeer.game.strategy.enums.ClientEventEnum.CONNECTED;
import static com.congeer.game.strategy.enums.ClientEventEnum.JOIN_PLAYER;
import static com.congeer.game.strategy.enums.ClientEventEnum.ROOM_INFO;
import static com.congeer.game.strategy.enums.ClientEventEnum.SYNC_SEED;
import static com.congeer.game.strategy.enums.ClientEventEnum.SYNC_FRAME;
import static com.congeer.game.strategy.enums.ClientEventEnum.SYNC_CONFIG;

public class AddRoomEvent extends GameEvent {

    @Override
    protected void handle(Message message) {
        String socketId = message.getSocketId();
        AddRoomData data = message.getData(AddRoomData.class);
        String playerId = data.getPlayerId();
        String roomId = data.getRoomId();
        gameContext.addSocket(socketId, roomId);
        if (!gameContext.containsRoom(roomId)) {
            Room room = gameContext.addEmptyRoom(roomId);
            Player player = createOwnerPlayer(socketId, playerId);
            room.setOwner(player).getPlayers().add(player);
            // 通知玩家自己的属性
            Message ret = new Message(CONNECTED, player.baseInfo().setCreate(true));
            gameContext.notice(socketId, ret);
            return;
        }
        Player player = gameContext.getEmptyPlayer(roomId, playerId);
        Room room = gameContext.getRoomBySocketId(socketId);
        if (player != null) {
            player.setId(playerId).setSocketId(socketId).setLock(true);
        } else {
            player = new Player(playerId, socketId);
            room.getViewers().add(player);
        }
        // 告诉玩家自己的状态
        gameContext.notice(socketId, new Message(CONNECTED, player.baseInfo()
            .setCreate(player.isOwner() && !room.isConfig())));
        // 告诉玩家其他玩家状态
        gameContext.notice(socketId, new Message(ROOM_INFO, room.baseInfo()));
        // 通知其他人有玩家加入
        gameContext.radio(socketId, new Message(JOIN_PLAYER, player.baseInfo()));

        // 1、同步全局配置
        for (JsonObject config : room.getConfigList()) {
            gameContext.notice(socketId, new Message(SYNC_CONFIG, config));
        }
        // 2、同步玩家配置
        if (!player.getConfigList().isEmpty()) {
            for (JsonObject config : player.getConfigList()) {
                gameContext.notice(socketId, new Message(SYNC_CONFIG, config));
            }
        }
        // 3、同步所有随机种子给玩家
        for (Map.Entry<String, List<Integer>> entry : room.getSeedMap().entrySet()) {
            SeedData seed = new SeedData();
            seed.setCode(entry.getKey());
            seed.setData(entry.getValue());
            gameContext.notice(socketId, new Message(SYNC_SEED, seed));
        }
        // 4、同步所有操作
        for (JsonObject action : room.getActionList()) {
            gameContext.notice(socketId, new Message(SYNC_FRAME, action));
        }
        gameContext.setSocketAlive(socketId);
    }

    private static Player createOwnerPlayer(String socketId, String playerId) {
        Player player = new Player(playerId, socketId);
        return player.setOwner(true).setPlayer(true).setIndex(0).setLock(true);
    }

}
