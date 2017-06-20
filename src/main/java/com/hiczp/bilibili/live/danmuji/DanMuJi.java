package com.hiczp.bilibili.live.danmuji;

/**
 * Created by czp on 17-6-20.
 */
public class DanMuJi {
    private static Config config;

    public static Config getConfig() {
        return config;
    }

    static void setConfig(Config config) {
        DanMuJi.config = config;
    }
}
