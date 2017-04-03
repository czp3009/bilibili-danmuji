package com.hiczp.bilibili.live.danmuji.callback;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiczp.bilibili.live.api.LiveDanMuSDK;
import com.hiczp.bilibili.live.api.callback.ILiveDanMuCallback;
import com.hiczp.bilibili.live.api.entity.*;
import com.hiczp.bilibili.live.danmuji.Utils;

import javax.swing.*;
import java.io.Closeable;
import java.io.IOException;

/**
 * Created by czp on 17-4-2.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
    private JLabel jLabel;
    private JTextArea jTextArea;
    private LiveDanMuSDK liveDanMuSDK;
    private Closeable closeable;

    public LiveDanMuCallback(JLabel jLabel, JTextArea jTextArea, LiveDanMuSDK liveDanMuSDK, Closeable closeable) {
        this.jLabel = jLabel;
        this.jTextArea = jTextArea;
        this.liveDanMuSDK = liveDanMuSDK;
        this.closeable = closeable;
    }

    @Override
    public void onDisconnect() {
        jTextArea.append("Disconnect, trying reconnect...\n");
        try {
            liveDanMuSDK.reconnect();
        } catch (IOException e) {
            jTextArea.append("Reconnect failed");
            try {
                closeable.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void onOnlineCountPackage(int onlineCount) {
        jLabel.setText("Viewer " + onlineCount);
    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {
        JSONArray info = danMuMSGEntity.info;
        Utils.writeLineToJTextArea(jTextArea, "[%s] %s", info.getJSONArray(2).getString(1), info.getString(1));
    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {
        JSONObject data = sendGiftEntity.data;
        Utils.writeLineToJTextArea(jTextArea, "[SendGift] %s give %s X %d", data.getString("uname"), data.getString("giftName"), data.getIntValue("num"));
    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {
        Utils.writeLineToJTextArea(jTextArea, "[Welcome] %s entered room!", welcomeEntity.data.getString("uname"));
    }

    @Override
    public void onSYSMSGPackage(SysMSGEntity sysMSGEntity) {
        Utils.writeLineToJTextArea(jTextArea, "[SysMSG] %s %s", sysMSGEntity.msg, sysMSGEntity.url);
    }

    @Override
    public void onSysGiftPackage(SysGiftEntity sysGiftEntity) {
        Utils.writeLineToJTextArea(jTextArea, "[SysGift] %s", sysGiftEntity.msg);
    }

    @Override
    public void onLivePackage(LiveEntity liveEntity) {
        Utils.writeLineToJTextArea(jTextArea, "[Live] Room %d start live!", liveEntity.roomid);
    }

    @Override
    public void onPreparingPackage(PreparingEntity preparingEntity) {
        Utils.writeLineToJTextArea(jTextArea, "[Preparing] Room %d stop live!", preparingEntity.roomid);
    }
}
