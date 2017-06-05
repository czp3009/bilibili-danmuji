package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmu.api.ILiveDanMuCallback;
import com.hiczp.bilibili.live.danmu.api.entity.*;
import com.hiczp.bilibili.live.danmuji.ui.MainForm;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.util.Date;

/**
 * Created by czp on 17-6-1.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
    private MainForm mainForm;
    private JTextPane jTextPane;
    private StyledDocument styledDocument;
    private Config config = Main.getConfig();

    public LiveDanMuCallback(MainForm mainForm, JTextPane jTextPane) {
        this.mainForm = mainForm;
        this.jTextPane = jTextPane;
        this.styledDocument = jTextPane.getStyledDocument();
    }

    private void printMessage(String message, Style style) {
        try {
            boolean isInEnd = jTextPane.getCaretPosition() == jTextPane.getText().length();
            styledDocument.insertString(styledDocument.getLength(), message + "\n", style);
            if (isInEnd) {
                jTextPane.setCaretPosition(jTextPane.getText().length());   //如果光标在最后则自动滚屏
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnect() {
        mainForm.onConnect();
        if (config.Connect.on) {
            printMessage(String.format("[%s] Connect succeed!", new Date()),
                    styledDocument.getStyle("ConnectStyle"));
        }
    }

    @Override
    public void onDisconnect() {
        mainForm.onDisconnect();
        if (config.Disconnect.on) {
            printMessage(String.format("[%s] Disconnected!", new Date()),
                    styledDocument.getStyle("DisconnectStyle"));
        }
    }

    @Override
    public void onOnlineCountPackage(int i) {
        mainForm.setTitle("Viewers: " + i);
    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {
        if (config.DanMu.on) {
            printMessage(String.format("[DanMu] [%s] %s", danMuMSGEntity.getSenderNick(), danMuMSGEntity.getDanMuContent()),
                    styledDocument.getStyle("DanMuStyle"));
        }
    }

    @Override
    public void onSysMSGPackage(SysMSGEntity sysMSGEntity) {
        if (config.SysMSG.on) {
            printMessage(String.format("[SysMSG] %s %s", sysMSGEntity.msg, sysMSGEntity.url),
                    styledDocument.getStyle("SysMSGStyle"));
        }
    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {
        if (config.SendGift.on) {
            SendGiftEntity.SendGiftEntityData data = sendGiftEntity.data;
            printMessage(String.format("[SendGift] %s given %s * %d", data.uname, data.giftName, data.num),
                    styledDocument.getStyle("SendGiftStyle"));
        }
    }

    @Override
    public void onSysGiftPackage(SysGiftEntity sysGiftEntity) {
        if (config.SysGift.on) {
            printMessage(String.format("[SysGift] %s", sysGiftEntity.msg),
                    styledDocument.getStyle("SysGiftStyle"));
        }
    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {
        if (config.Welcome.on) {
            printMessage(String.format("[Welcome] %s entered room!", welcomeEntity.data.uname),
                    styledDocument.getStyle("WelcomeStyle"));
        }
    }

    @Override
    public void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity) {
        if (config.WelcomeGuard.on) {
            WelcomeGuardEntity.WelcomeGuardEntityData data = welcomeGuardEntity.data;
            printMessage(String.format("[WelcomeGuard] level %d guard %s entered!", data.guard_level, data.username),
                    styledDocument.getStyle("WelcomeGuardStyle"));
        }
    }

    @Override
    public void onLivePackage(LiveEntity liveEntity) {
        if (config.Live.on) {
            printMessage(String.format("[Live] Room %d start live!", liveEntity.roomid),
                    styledDocument.getStyle("LiveStyle"));
        }
    }

    @Override
    public void onPreparingPackage(PreparingEntity preparingEntity) {
        if (config.Preparing.on) {
            printMessage(String.format("[Preparing] Room %d stop live!", preparingEntity.roomid),
                    styledDocument.getStyle("PreparingStyle"));
        }
    }

    @Override
    public void onRoomAdminsPackage(RoomAdminsEntity roomAdminsEntity) {
        if (config.RoomAdmins.on) {
            printMessage(String.format("There are %d room admins.", roomAdminsEntity.uids.length),
                    styledDocument.getStyle("RoomAdminsStyle"));
        }
    }
}
