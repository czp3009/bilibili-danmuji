package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmuji.ui.MainForm;

import javax.swing.*;

/**
 * Created by czp on 17-5-31.
 */
public class Main {
    public static void main(String[] args) {
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

        //启动主窗体
        new MainForm();
    }
}
