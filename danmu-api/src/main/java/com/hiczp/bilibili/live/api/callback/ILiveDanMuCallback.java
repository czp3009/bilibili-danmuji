package com.hiczp.bilibili.live.api.callback;

import com.hiczp.bilibili.live.api.entity.*;

/**
 * Created by czp on 17-4-2.
 */
public interface ILiveDanMuCallback {
    void onDisconnect();

    void onOnlineCountPackage(int onlineCount);

    void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity);

    void onSendGiftPackage(SendGiftEntity sendGiftEntity);

    void onWelcomePackage(WelcomeEntity welcomeEntity);

    void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity);

    void onSYSMSGPackage(SysMSGEntity sysMSGEntity);

    void onSysGiftPackage(SysGiftEntity sysGiftEntity);

    void onLivePackage(LiveEntity liveEntity);

    void onPreparingPackage(PreparingEntity preparingEntity);
}
