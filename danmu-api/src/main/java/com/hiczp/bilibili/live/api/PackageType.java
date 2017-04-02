package com.hiczp.bilibili.live.api;

/**
 * Created by czp on 17-4-1.
 */
enum PackageType {
    JOIN_SUCCESS,
    ONLINE_COUNT,
    LIVE,
    PREPARING,
    DANMU_MSG,
    SEND_GIFT,
    WELCOME;

    static PackageType getByName(String s) {
        for (PackageType packageType : PackageType.values()) {
            if (packageType.toString().equals(s)) {
                return packageType;
            }
        }
        return null;
    }
}
