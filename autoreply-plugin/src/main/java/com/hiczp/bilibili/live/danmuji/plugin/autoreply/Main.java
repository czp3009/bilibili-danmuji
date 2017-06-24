package com.hiczp.bilibili.live.danmuji.plugin.autoreply;

import com.hiczp.bilibili.live.danmuji.DanMuJi;
import com.hiczp.bilibili.live.danmuji.annotation.DanMuJiPlugin;
import com.hiczp.bilibili.live.danmuji.api.AbstractDanMuJiPlugin;
import com.hiczp.bilibili.live.danmuji.plugin.autoreply.ui.AutoReplyConfigForm;

import javax.swing.*;

/**
 * Created by czp on 17-6-22.
 */
@DanMuJiPlugin(name = "AutoReplyPlugin", version = "0.0.1")
public class Main extends AbstractDanMuJiPlugin {
    private DanMuCallback danMuCallback;

    public void onLoad() {
        System.out.println("AutoReplyPlugin loaded!");
    }

    public void onUnload() {
        System.out.println("AutoReplyPlugin unloaded!");
    }

    public void onStart() {
        danMuCallback = new DanMuCallback(this);
        DanMuJi.getLiveDanMuReceiver().addCallback(danMuCallback);
    }

    public void onStop() {
        danMuCallback.stopDanMuSendingThreads();
    }

    public JMenuItem createPluginMenu() {
        JMenuItem autoReplyConfig = new JMenuItem("AutoReply Config");
        autoReplyConfig.addActionListener(actionEvent -> AutoReplyConfigForm.main());
        return autoReplyConfig;
    }
}
