package com.hiczp.bilibili.live.danmuji.plugin.autoreply;

import com.hiczp.bilibili.live.danmuji.DanMuJi;
import com.hiczp.bilibili.live.danmuji.WindowManager;
import com.hiczp.bilibili.live.danmuji.plugin.autoreply.ui.AutoReplyConfigForm;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import javax.swing.*;

/**
 * Created by czp on 17-6-22.
 */
public class Main extends Plugin {
    private DanMuCallback danMuCallback;

    public Main(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
    }

    public void onLoad() {
        System.out.println("AutoReplyPlugin loaded!");
    }

    public void onUnload() {
        System.out.println("AutoReplyPlugin unloaded!");
    }

    public void onStart() {
        danMuCallback = new DanMuCallback(WindowManager.getMainForm());
        DanMuJi.getLiveDanMuReceiver().addCallback(danMuCallback);
    }

    public void onStop() {

    }

    public JMenuItem createPluginMenu() {
        JMenuItem autoReplyConfig = new JMenuItem("AutoReply Config");
        autoReplyConfig.addActionListener(actionEvent -> AutoReplyConfigForm.main());
        return autoReplyConfig;
    }
}
