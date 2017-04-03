package com.hiczp.bilibili.live.danmuji.callback;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiczp.bilibili.live.api.LiveDanMuSDK;
import com.hiczp.bilibili.live.api.callback.ILiveDanMuCallback;
import com.hiczp.bilibili.live.api.entity.*;
import com.hiczp.bilibili.live.danmuji.Utils;
import com.hiczp.bilibili.live.danmuji.gui.MainForm;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by czp on 17-4-2.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
    private MainForm mainForm;
    private LiveDanMuSDK liveDanMuSDK;
    private Closeable closeable;

    public LiveDanMuCallback(MainForm mainForm, LiveDanMuSDK liveDanMuSDK, Closeable closeable) {
        this.mainForm = mainForm;
        this.liveDanMuSDK = liveDanMuSDK;
        this.closeable = closeable;
    }

    private void writeLine(String text, Object... objects) {
        Utils.writeLineToJTextArea(mainForm.getjTextArea(), text, objects);
    }

    @Override
    public void onDisconnect() {
        writeLine("Disconnect, trying reconnect...");
        try {
            liveDanMuSDK.reconnect();
        } catch (IOException e) {
            writeLine("Reconnect failed");
            try {
                closeable.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void onOnlineCountPackage(int onlineCount) {
        mainForm.setSuffixOnTitle("Viewers " + onlineCount);
    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {
        JSONArray info = danMuMSGEntity.info;
        writeLine("[DanMu] %s: %s", info.getJSONArray(2).getString(1), info.getString(1));
    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {
        JSONObject data = sendGiftEntity.data;
        writeLine("[SendGift] %s given %s X %d", data.getString("uname"), data.getString("giftName"), data.getIntValue("num"));
    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {
        writeLine("[Welcome] %s entered room!", welcomeEntity.data.getString("uname"));
    }

    @Override
    public void onSYSMSGPackage(SysMSGEntity sysMSGEntity) {
        writeLine("[SysMSG] %s %s", sysMSGEntity.msg, sysMSGEntity.url);
    }

    @Override
    public void onSysGiftPackage(SysGiftEntity sysGiftEntity) {
        writeLine("[SysGift] %s", sysGiftEntity.msg);
    }

    @Override
    public void onLivePackage(LiveEntity liveEntity) {
        writeLine("[Live] Room %d start live!", liveEntity.roomid);
    }

    @Override
    public void onPreparingPackage(PreparingEntity preparingEntity) {
        writeLine("[Preparing] Room %d stop live!", preparingEntity.roomid);
    }
}
