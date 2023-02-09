package com.congeer.game.model.storage;

import com.congeer.game.bean.Player;
import com.congeer.game.bean.Room;
import com.congeer.game.model.GameStatus;

public abstract class GameStorage {

    /**
     * 保存房间数据
     *
     * @param room
     * @return
     */
    public abstract void saveRoom(Room room);

    /**
     * 更新房间数据
     *
     * @param room
     */
    public abstract void updateRoom(Room room);

    /**
     * 保存socket
     *
     * @param socketId
     * @param player
     */
    public abstract void saveSocket(String socketId, Player player);

    public Room getRoomBySocketId(String socketId) {
        Player playerBySocketId = getPlayerBySocketId(socketId);
        if (playerBySocketId == null) {
            return null;
        }
        return getRoom(playerBySocketId.getWhere());
    }

    public abstract Player getPlayerBySocketId(String socketId);

    public abstract Room getRoom(String roomId);

    /**
     * 移除一个socket
     *
     * @param socketId
     */
    public abstract void removeSocket(String socketId);

    /**
     * 获取游戏服务器当前状态
     *
     * @return
     */
    public abstract GameStatus getStatus();

    public abstract void setSocketAlive(String socketId);

    /**
     * 清除过期数据
     */
    public abstract void clearExpire();


}
