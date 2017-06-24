package com.hiczp.bilibili.live.danmuji;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

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

        //创建主窗体
        WindowManager.createMainForm();

        //加载插件
        PluginManager.loadPlugins();

        //显示主窗体
        WindowManager.getMainForm().setVisible(true);
    }
}
