package com.congeer.game.event.room;

import com.congeer.game.Application;
import com.congeer.game.bean.BaseMessage;
import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.event.RoomEvent;
import com.congeer.game.model.context.AddRoomContext;
import com.congeer.game.model.context.SeedContext;
import io.vertx.core.Future;

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
public class AddRoomEvent extends RoomEvent<AddRoomContext> {

    @Override
    protected Future<Room> getRoom(AddRoomContext context) {
        return Application.getGame().getRoom(context.getRoomId());
    }

    @Override
    protected void handleRoom(AddRoomContext context) {
        String socketId = context.getSocketId();
        String playerId = context.getPlayerId();
        Room room = context.getRoom();
        Player player = room.getEmptySeat(playerId);
        if (player != null) {
            player.setId(playerId).setSocketId(socketId).setLock(true);
        } else {
            player = new Player(playerId, socketId).setWhere(room);
            room.getViewers().add(player);
        }
        // 告诉玩家自己的状态
        context.reply(new BaseMessage(CONNECTED, player.baseInfo()
            .setCreate(player.isOwner() && !room.isConfig())));
        // 告诉玩家其他玩家状态
        context.reply(new BaseMessage(ROOM_INFO, room.baseInfo()));
        // 通知其他人有玩家加入
        context.radio(new BaseMessage(JOIN_PLAYER, player.baseInfo()));

        // 1、同步全局配置
        for (String config : room.getConfigList()) {
            context.reply(new BaseMessage(SYNC_CONFIG, config));
        }
        // 2、同步玩家配置
        if (!player.getConfigList().isEmpty()) {
            for (String config : player.getConfigList()) {
                context.reply(new BaseMessage(SYNC_CONFIG, config));
            }
        }
        // 3、同步所有随机种子给玩家
        for (Map.Entry<String, List<Integer>> entry : room.getSeedMap().entrySet()) {
            SeedContext seed = new SeedContext();
            seed.setCode(entry.getKey());
            seed.setData(entry.getValue());
            context.reply(new BaseMessage(SYNC_SEED, seed));
        }
        // 4、同步所有操作
        for (String action : room.getFrameList()) {
            context.reply(new BaseMessage(SYNC_FRAME, action));
        }
    }

    @Override
    protected void handleEmpty(AddRoomContext context) {
        String socketId = context.getSocketId();
        String playerId = context.getPlayerId();
        String roomId = context.getRoomId();
        Room room = new Room(roomId);
        Player player = createOwnerPlayer(socketId, playerId).setWhere(room);
        room.setOwner(player).getPlayers().add(player);
        context.createRoom(room);
        // 通知玩家自己的属性
        context.reply(new BaseMessage(CONNECTED, player.baseInfo().setCreate(true)));
    }

    private static Player createOwnerPlayer(String socketId, String playerId) {
        Player player = new Player(playerId, socketId);
        return player.setOwner(true).setPlayer(true).setIndex(0).setLock(true);
    }

}
