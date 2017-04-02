package com.hiczp.bilibili.live.api.entity;

import java.util.Random;

/**
 * Created by czp on 17-4-1.
 */
public class JoinEntity {
    private int roomid;
    private long uid;

    public JoinEntity(int roomid) {
        this.roomid = roomid;
        uid = (long) (1e14 + 2e14 * new Random().nextDouble());
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
