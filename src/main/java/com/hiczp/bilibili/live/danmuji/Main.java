package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmuji.gui.MainForm;
import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;

/**
 * Created by czp on 17-3-29.
 */
public class Main {
    public static void main(String[] args) {
        //设定主题
        String lookAndFeelName = null;
        String osName = System.getProperty("os.name");
        if (osName.contains("Linux")) {
            lookAndFeelName = GTKLookAndFeel.class.getName();
        } else if (osName.contains("Windows")) {
            lookAndFeelName = WindowsLookAndFeel.class.getName();
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

