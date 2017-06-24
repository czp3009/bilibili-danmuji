package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmu.api.LiveDanMuReceiver;
import com.hiczp.bilibili.live.danmu.api.LiveDanMuSender;

/**
 * Created by czp on 17-6-20.
 */
public class DanMuJi {
    private static Config config;

    private static LiveDanMuReceiver liveDanMuReceiver;

    private static LiveDanMuSender liveDanMuSender;

    private static boolean userWantDisconnect = false;

    public static Config getConfig() {
        return config;
    }

    static void setConfig(Config config) {
        DanMuJi.config = config;
    }

    public static LiveDanMuReceiver getLiveDanMuReceiver() {
        return liveDanMuReceiver;
    }

    public static void setLiveDanMuReceiver(LiveDanMuReceiver liveDanMuReceiver) {
        DanMuJi.liveDanMuReceiver = liveDanMuReceiver;
    }

    public static LiveDanMuSender getLiveDanMuSender() {
        return liveDanMuSender;
    }

    public static void setLiveDanMuSender(LiveDanMuSender liveDanMuSender) {
        DanMuJi.liveDanMuSender = liveDanMuSender;
    }

    public static boolean isUserWantDisconnect() {
        return userWantDisconnect;
    }

    public static void setUserWantDisconnect(boolean userWantDisconnect) {
        DanMuJi.userWantDisconnect = userWantDisconnect;
    }

    public static boolean isLiveDanMuSenderAvailable() {
        if (liveDanMuSender == null) {
            return false;
        }
        if (!liveDanMuSender.isCookiesSet()) {
            String cookies = DanMuJi.getConfig().cookies;
            if (cookies != null && !cookies.equals("")) {
                liveDanMuSender.setCookies(cookies);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
