package com.hiczp.bilibili.live.danmuji;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hiczp.bilibili.live.danmu.api.LiveDanMuReceiver;
import com.hiczp.bilibili.live.danmuji.ui.MainForm;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by czp on 17-5-31.
 */
public class Main {
    public static void main(String[] args) {
        //读取配置
        Config config = new Config();  //默认配置
        File configFile = Config.CONFIG_FILE;
        if (configFile.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(configFile)) {
                config = JSON.parseObject(fileInputStream, Config.class);
                System.out.println("Read configuration from file: " + configFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("Config file invalid!");
            }
        } else {
            config.storeToFile();
        }
        DanMuJi.setConfig(config);

        //设定主题
        String lookAndFeelName = null;
        String osName = System.getProperty("os.name");
        if (osName.contains("Linux")) {
            lookAndFeelName = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"; //使用字符串是为了防止Windows Oracle JDK无法编译
        } else if (osName.contains("Windows")) {
            lookAndFeelName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        }
        if (lookAndFeelName != null) {
            try {
                UIManager.setLookAndFeel(lookAndFeelName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //设定程序退出时执行的步骤
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing DanMuJi...");
            DanMuJi.setUserWantDisconnect(true);
            LiveDanMuReceiver liveDanMuReceiver = DanMuJi.getLiveDanMuReceiver();
            if (liveDanMuReceiver != null) {
                System.out.println("Closing connection!");
                try {
                    liveDanMuReceiver.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    liveDanMuReceiver.waitUntilCallbackDispatchThreadExit();
                }
            }
            DanMuJi.stopPlugins();
            DanMuJi.getConfig().storeToFile();
        }));

        //加载插件
        DanMuJi.reloadPlugins();

        //创建主窗体
        MainForm mainForm = WindowManager.createMainForm();
        //向主窗体添加插件菜单
        mainForm.addPluginConfigMenuItems(DanMuJi.generatePluginConfigMenuItems());
        //显示主窗体
        mainForm.setVisible(true);
    }
}
