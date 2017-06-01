package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmu.api.ILiveDanMuCallback;
import com.hiczp.bilibili.live.danmu.api.entity.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.util.Date;

/**
 * Created by czp on 17-6-1.
 */
public class LiveDanMuCallback implements ILiveDanMuCallback {
    private JFrame jFrame;
    private JTextPane jTextPane;
    private StyledDocument styledDocument;

    public LiveDanMuCallback(JFrame jFrame, JTextPane jTextPane) {
        this.jFrame = jFrame;
        this.jTextPane = jTextPane;
        styledDocument = jTextPane.getStyledDocument();
    }

    private void printMessage(String message, Object... objects) {
        try {
            boolean isInEnd = jTextPane.getCaretPosition() == jTextPane.getText().length();
            styledDocument.insertString(styledDocument.getLength(), String.format(message, objects) + "\n", null);
            if (isInEnd) {
                jTextPane.setCaretPosition(jTextPane.getText().length());
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect() {
        printMessage(String.format("[%s] Disconnected!", new Date()));
    }

    @Override
    public void onOnlineCountPackage(int i) {
        jFrame.setTitle("Viewers: " + i);
    }

    @Override
    public void onDanMuMSGPackage(DanMuMSGEntity danMuMSGEntity) {
        printMessage("[DanMu] [%s] %s", danMuMSGEntity.getSenderNick(), danMuMSGEntity.getDanMuContent());
    }

    @Override
    public void onSysMSGPackage(SysMSGEntity sysMSGEntity) {
        printMessage("[SysMSG] %s %s", sysMSGEntity.msg, sysMSGEntity.url);
    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {
        SendGiftEntity.SendGiftEntityData data = sendGiftEntity.data;
        printMessage("[SendGift] %s given %s * %d", data.uname, data.giftName, data.num);
    }

    @Override
    public void onSysGiftPackage(SysGiftEntity sysGiftEntity) {
        printMessage("[SysGift] %s", sysGiftEntity.msg);
    }

    @Override
    public void onWelcomePackage(WelcomeEntity welcomeEntity) {
        printMessage("[Welcome] %s entered room!", welcomeEntity.data.uname);
    }

    @Override
    public void onWelcomeGuardPackage(WelcomeGuardEntity welcomeGuardEntity) {
        WelcomeGuardEntity.WelcomeGuardEntityData data = welcomeGuardEntity.data;
        printMessage("[WelcomeGuard] level %d guard %s entered!", data.guard_level, data.username);
    }

    @Override
    public void onLivePackage(LiveEntity liveEntity) {
        printMessage("[Live] Room %d start live!", liveEntity.roomid);
    }

    @Override
    public void onPreparingPackage(PreparingEntity preparingEntity) {
        printMessage("[Preparing] Room %d stop live!", preparingEntity.roomid);
    }

    @Override
    public void onRoomAdminsPackage(RoomAdminsEntity roomAdminsEntity) {
        printMessage("There are %d room admins.", roomAdminsEntity.uids.length);
    }
}
