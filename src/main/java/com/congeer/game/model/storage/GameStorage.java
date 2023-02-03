package com.congeer.game.model.storage;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.GameStatus;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.redis.client.Response;

public abstract class GameStorage {

    /**
     * 保存房间数据
     * @param room
     * @return
     */
    public abstract Future<@Nullable Response> saveRoom(Room room);

    /**
     * 更新房间数据
     * @param room
     */
    public abstract void updateRoom(Room room);

    /**
     * 保存socket
     * @param socketId
     * @param player
     */
    public abstract void saveSocket(String socketId, Player player);

    public Future<Room> getRoomBySocketId(String socketId) {
        return getPlayerBySocketId(socketId).compose(player -> getRoom(player.getWhere()));
    }

    public abstract Future<Player> getPlayerBySocketId(String socketId);

    public abstract Future<Room> getRoom(String roomId);

    /**
     * 移除一个socket
     * @param socketId
     */
    public abstract void removeSocket(String socketId);

    /**
     * 获取游戏服务器当前状态
     * @return
     */
    public abstract Future<GameStatus> getStatus();

    public abstract void setSocketAlive(String socketId);

    /**
     * 清除过期数据
     */
    public abstract void clearExpire();


}
