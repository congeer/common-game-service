package com.congeer.game.model.context;

import com.congeer.game.bean.Player;

public class LeaveRoomContext extends RoomContext{

    private Player leavePlayer;

    public Player getLeavePlayer() {
        return leavePlayer;
    }

    public LeaveRoomContext setLeavePlayer(Player leavePlayer) {
        this.leavePlayer = leavePlayer;
        return this;
    }

}
