package com.congeer.game.model.context;

public class LockContext extends RoomContext {

    boolean all;

    public boolean isAll() {
        return all;
    }

    public LockContext setAll(boolean all) {
        this.all = all;
        return this;
    }

}
