package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmu.api.LiveDanMuSender;

/**
 * Created by czp on 17-6-20.
 */
public class LiveDanMuSenderHolder {
    private static LiveDanMuSender liveDanMuSender;

    public static LiveDanMuSender getLiveDanMuSender() {
        return liveDanMuSender;
    }

    public static void setLiveDanMuSender(LiveDanMuSender liveDanMuSender) {
        LiveDanMuSenderHolder.liveDanMuSender = liveDanMuSender;
    }
}
