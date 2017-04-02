package com.hiczp.bilibili.live.danmuji.callback;

import com.hiczp.bilibili.live.api.callback.ILiveDanMuCallback;
import com.hiczp.bilibili.live.api.entity.DanMuMSGEntity;
import com.hiczp.bilibili.live.api.entity.SendGiftEntity;
import com.hiczp.bilibili.live.api.entity.WelcomeEntity;

import javax.swing.*;

/**
 * Created by czp on 17-4-2.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
    private JLabel jLabel;
    private JTextArea jTextArea;

    public LiveDanMuCallback(JLabel jLabel, JTextArea jTextArea) {
        this.jLabel = jLabel;
        this.jTextArea = jTextArea;
    }

    @Override
    public void onOnlineCountPackage(int onlineCount) {
        jLabel.setText("Viewer " + onlineCount);
    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {

    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {

    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {

    }
}
