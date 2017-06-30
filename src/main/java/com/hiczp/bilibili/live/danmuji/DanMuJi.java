package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmu.api.LiveDanMuReceiver;
import com.hiczp.bilibili.live.danmu.api.LiveDanMuSender;
import com.hiczp.bilibili.live.danmuji.extension.PluginUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.JarPluginManager;
import ro.fortsoft.pf4j.PluginManager;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czp on 17-6-20.
 */
public class DanMuJi {
    private static final Logger log = LoggerFactory.getLogger(Config.class);

    private static Config config;

    private static LiveDanMuReceiver liveDanMuReceiver;

    private static LiveDanMuSender liveDanMuSender;

    private static boolean userWantDisconnect = false;

    private static PluginManager pluginManager;

    public static Config getConfig() {
        return config;
    }

    static void setConfig(Config config) {
        DanMuJi.config = config;
    }

    public static LiveDanMuReceiver getLiveDanMuReceiver() {
        return liveDanMuReceiver;
    }

    public static void setLiveDanMuReceiver(LiveDanMuReceiver liveDanMuReceiver) {
        DanMuJi.liveDanMuReceiver = liveDanMuReceiver;
    }

    public static LiveDanMuSender getLiveDanMuSender() {
        return liveDanMuSender;
    }

    public static void setLiveDanMuSender(LiveDanMuSender liveDanMuSender) {
        DanMuJi.liveDanMuSender = liveDanMuSender;
    }

    public static boolean isUserWantDisconnect() {
        return userWantDisconnect;
    }

    public static void setUserWantDisconnect(boolean userWantDisconnect) {
        DanMuJi.userWantDisconnect = userWantDisconnect;
    }

    public static boolean isLiveDanMuSenderAvailable() {
        if (liveDanMuSender == null) {
            return false;
        }
        if (!liveDanMuSender.isCookiesSet()) {
            String cookies = DanMuJi.getConfig().cookies;
            if (cookies != null && !cookies.equals("")) {
                liveDanMuSender.setCookies(cookies);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    public static boolean reloadPlugins() {
        stopPlugins();
        return loadPlugins();
    }

    private static boolean createPluginsDirectory() {
        String pluginDirectory = System.getProperty("pf4j.pluginsDir", "plugins");
        File file = new File(pluginDirectory);
        if (file.exists()) {
            if (file.isFile()) {
                log.error("Please delete file named %s in work directory before plugins can be loaded!\n", pluginDirectory);
                return false;
            }
        } else {
            if (file.mkdirs()) {
                log.debug("Create plugin directory: " + file.getAbsolutePath());
            } else {
                log.error("Cannot create plugin directory in " + file.getAbsolutePath());
                return false;
            }
        }
        return true;
    }

    private static boolean loadPlugins() {
        if (!createPluginsDirectory()) {
            return false;
        }
        pluginManager = new JarPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        return true;
    }

    public static void stopPlugins() {
        if (pluginManager != null) {
            pluginManager.stopPlugins();
        }
    }

    public static List<JMenuItem> generatePluginConfigMenuItems() {
        List<JMenuItem> jMenuItems = new ArrayList<>();
        if (pluginManager != null) {
            pluginManager.getExtensions(PluginUI.class).forEach(pluginUI -> {
                try {
                    jMenuItems.add(pluginUI.getPluginConfigMenu());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return jMenuItems;
    }
}
