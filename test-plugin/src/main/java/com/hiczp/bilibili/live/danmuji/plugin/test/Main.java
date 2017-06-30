package com.hiczp.bilibili.live.danmuji.plugin.test;

import com.hiczp.bilibili.live.danmuji.extension.DanMuJiAction;
import com.hiczp.bilibili.live.danmuji.extension.PluginUI;
import com.hiczp.bilibili.live.danmuji.plugin.test.ui.TestForm;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginException;
import ro.fortsoft.pf4j.PluginWrapper;

import javax.swing.*;

/**
 * Created by czp on 17-6-29.
 */
public class Main extends Plugin {
    public Main(PluginWrapper pluginWrapper) {
        super(pluginWrapper);
    }

    @Override
    public void start() throws PluginException {
        System.out.println("Test plugin started!");
    }

    @Override
    public void stop() throws PluginException {
        System.out.println("Test plugin stopped!");
    }

    @Extension
    public static class TestDanMuJiAction implements DanMuJiAction {
        @Override
        public void connect() {
            System.out.println("[TestPlugin] Connected!");
        }

        @Override
        public void disconnect() {
            System.out.println("[TestPlugin] Disconnected!");
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
