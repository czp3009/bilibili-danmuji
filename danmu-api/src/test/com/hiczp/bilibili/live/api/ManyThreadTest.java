package com.hiczp.bilibili.live.api;

/**
 * Created by czp on 17-4-4.
 */
public class ManyThreadTest {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            try {
                new LiveDanMuSDK(1110317).connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
