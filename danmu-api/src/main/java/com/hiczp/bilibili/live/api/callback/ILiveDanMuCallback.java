package com.hiczp.bilibili.live.api.callback;

import com.hiczp.bilibili.live.api.entity.DanMuMSGEntity;
import com.hiczp.bilibili.live.api.entity.SendGiftEntity;
import com.hiczp.bilibili.live.api.entity.WelcomeEntity;

/**
 * Created by czp on 17-4-2.
 */
public interface ILiveDanMuCallback {
    void onOnlineCountPackage(int onlineCount);

    void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity);

    void onSendGiftPackage(SendGiftEntity sendGiftEntity);

    void onWelcomePackage(WelcomeEntity welcomeEntity);
}
