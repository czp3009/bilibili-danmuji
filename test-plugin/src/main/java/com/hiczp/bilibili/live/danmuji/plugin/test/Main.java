package com.hiczp.bilibili.live.danmuji.plugin.test;

import com.hiczp.bilibili.live.danmuji.extension.DanMuJiAction;
import com.hiczp.bilibili.live.danmuji.extension.PluginUI;
import com.hiczp.bilibili.live.danmuji.plugin.test.ui.TestForm;
import org.slf4j.Logger;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginException;
import ro.fortsoft.pf4j.PluginWrapper;

import javax.swing.*;

/**
 * Created by czp on 17-6-29.
 */
public class Main extends Plugin {
    private static String pluginId;
    private static Logger log;

    public Main(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
        pluginId = pluginWrapper.getPluginId();
        log = super.log;
    }

    @Override
    public void start() throws PluginException {
        log.info("{} started!", pluginId);
    }

    @Override
    public void stop() throws PluginException {
        log.info("{} stopped!", pluginId);
    }

    @Extension
    public static class TestDanMuJiAction implements DanMuJiAction {
        @Override
        public void connect() {
            log.debug("[{}] Connected!\n", pluginId);
        }

        @Override
        public void disconnect() {
            log.debug("[{}] Disconnected!\n", pluginId);
        }
    }

    @Extension
    public static class TestPluginUI implements PluginUI {
        @Override
        public JMenuItem getPluginConfigMenu() {
            JMenuItem testConfig = new JMenuItem("Test Config");
            testConfig.addActionListener(actionEvent -> new TestForm());
            return testConfig;
        }
    }
}
