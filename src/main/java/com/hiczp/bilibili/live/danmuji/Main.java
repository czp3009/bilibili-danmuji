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
        switch (System.getProperty("os.name")) {
            case "Linux": {
                lookAndFeelName = GTKLookAndFeel.class.getName();
            }
            break;
            case "Windows": {
                lookAndFeelName = WindowsLookAndFeel.class.getName();
            }
            break;
        }
        if (lookAndFeelName != null) {
            try {
                UIManager.setLookAndFeel(lookAndFeelName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //启动主窗体
        MainForm.main();
    }
}
