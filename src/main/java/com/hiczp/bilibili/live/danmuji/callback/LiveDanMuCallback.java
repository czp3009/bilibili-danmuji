package com.hiczp.bilibili.live.danmuji.callback;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiczp.bilibili.live.api.callback.ILiveDanMuCallback;
import com.hiczp.bilibili.live.api.entity.*;
import com.hiczp.bilibili.live.danmuji.Utils;
import com.hiczp.bilibili.live.danmuji.gui.MainForm;

/**
 * Created by czp on 17-4-2.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
    private MainForm mainForm;

    public LiveDanMuCallback(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    private void writeLine(String text, Object... objects) {
        Utils.writeLineToJTextArea(mainForm.getjTextArea(), text, objects);
    }

    @Override
    public void onDisconnect() {
        writeLine("Disconnect!");
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
    public void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity) {
        JSONObject data = welcomeGuardEntity.data;
        writeLine("[WelcomeGuard] level %d guard %s entered!", data.getIntValue("guard_level"), data.getString("username"));
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
