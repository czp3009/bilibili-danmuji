package com.hiczp.bilibili.live.danmuji.plugin.autoreply;

import com.hiczp.bilibili.live.danmuji.DanMuJi;
import com.hiczp.bilibili.live.danmuji.WindowManager;
import com.hiczp.bilibili.live.danmuji.extension.DanMuJiAction;
import com.hiczp.bilibili.live.danmuji.extension.PluginUI;
import com.hiczp.bilibili.live.danmuji.plugin.autoreply.ui.AutoReplyConfigForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginException;
import ro.fortsoft.pf4j.PluginWrapper;

import javax.swing.*;

/**
 * Created by czp on 17-6-22.
 */
public class Main extends Plugin {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static String pluginId;

    public Main(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
        pluginId = pluginWrapper.getPluginId();
    }

    @Override
    public void start() throws PluginException {
        log.debug("{} loaded!", pluginId);
    }

    @Override
    public void stop() throws PluginException {
        log.debug("{} unloaded!", pluginId);
    }

    @Extension
    public static class AutoReplyDanMuJiAction implements DanMuJiAction {
        @Override
        public void connect() {
            DanMuCallback danMuCallback = new DanMuCallback(WindowManager.getMainForm());
            DanMuJi.getLiveDanMuReceiver().addCallback(danMuCallback);
            log.debug("Callback added.");
        }

        @Override
        public void disconnect() {

        }
    }

    @Extension
    public static class AutoReplyPluginUI implements PluginUI {
        @Override
        public JMenuItem getPluginConfigMenu() {
            JMenuItem autoReplyConfig = new JMenuItem("AutoReply Config");
            autoReplyConfig.addActionListener(actionEvent -> AutoReplyConfigForm.main());
            return autoReplyConfig;
        }
    }
}
