package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmu.api.ILiveDanMuCallback;
import com.hiczp.bilibili.live.danmu.api.entity.*;
import com.hiczp.bilibili.live.danmuji.ui.MainForm;

import java.io.IOException;
import java.util.Date;

/**
 * Created by czp on 17-6-1.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
    private MainForm mainForm = WindowManager.getMainForm();
    private Config config = DanMuJi.getConfig();
    private boolean isReconnection = false;

    @Override
    public void onConnect() {
        if (!isReconnection) {
            mainForm.onConnect();
        }
        if (config.Connect.on) {
            mainForm.printMessage("ConnectStyle",
                    "[%s] Connect succeed!", new Date());
        }
    }

    @Override
    public void onDisconnect() {
        if (DanMuJi.isUserWantDisconnect()) {
            mainForm.onDisconnect();
            if (config.Disconnect.on) {
                mainForm.printMessage("DisconnectStyle",
                        "[%s] Disconnected!", new Date());
            }
        } else {
            mainForm.printInfo("Reconnecting...");
            isReconnection = true;
            try {
                DanMuJi.getLiveDanMuReceiver().connect();
            } catch (IOException e) {
                DanMuJi.setUserWantDisconnect(true);
                mainForm.printInfo("%s: %s", e.getClass().getName(), e.getMessage());
                mainForm.printInfo("Abort operation.");
                e.printStackTrace();
            } finally {
                isReconnection = false;
            }
        }
    }

    @Override
    public void onOnlineCountPackage(int i) {
        mainForm.setTitle("Viewers: " + i);
    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {
        if (config.DanMu.on) {
            mainForm.printMessage("DanMuStyle",
                    "[DanMu] [%s] %s", danMuMSGEntity.getSenderNick(), danMuMSGEntity.getDanMuContent());
        }
    }

    @Override
    public void onSysMSGPackage(SysMSGEntity sysMSGEntity) {
        if (config.SysMSG.on) {
            mainForm.printMessage("SysMSGStyle",
                    "[SysMSG] %s %s", sysMSGEntity.msg, sysMSGEntity.url);
        }
    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {
        if (config.SendGift.on) {
            SendGiftEntity.SendGiftEntityData data = sendGiftEntity.data;
            mainForm.printMessage("SendGiftStyle",
                    "[SendGift] %s given %s * %d", data.uname, data.giftName, data.num);
        }
    }

    @Override
    public void onSysGiftPackage(SysGiftEntity sysGiftEntity) {
        if (config.SysGift.on) {
            mainForm.printMessage("SysGiftStyle",
                    "[SysGift] %s", sysGiftEntity.msg);
        }
    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {
        if (config.Welcome.on) {
            mainForm.printMessage("WelcomeStyle",
                    "[Welcome] %s entered room!", welcomeEntity.data.uname);
        }
    }

    @Override
    public void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity) {
        if (config.WelcomeGuard.on) {
            WelcomeGuardEntity.WelcomeGuardEntityData data = welcomeGuardEntity.data;
            mainForm.printMessage("WelcomeGuardStyle",
                    "[WelcomeGuard] level %d guard %s entered!", data.guard_level, data.username);
        }
    }

    @Override
    public void onLivePackage(LiveEntity liveEntity) {
        if (config.Live.on) {
            mainForm.printMessage("LiveStyle",
                    "[Live] Room %d start live!", liveEntity.roomid);
        }
    }

    @Override
    public void onPreparingPackage(PreparingEntity preparingEntity) {
        if (config.Preparing.on) {
            mainForm.printMessage("PreparingStyle",
                    "[Preparing] Room %d stop live!", preparingEntity.roomid);
        }
    }

    @Override
    public void onRoomAdminsPackage(RoomAdminsEntity roomAdminsEntity) {
        if (config.RoomAdmins.on) {
            mainForm.printMessage("RoomAdminsStyle",
                    "There are %d room admins.", roomAdminsEntity.uids.length);
        }
    }
}
