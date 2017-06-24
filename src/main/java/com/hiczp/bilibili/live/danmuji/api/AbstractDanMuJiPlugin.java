package com.hiczp.bilibili.live.danmuji.api;

import com.hiczp.bilibili.live.danmuji.WindowManager;
import com.hiczp.bilibili.live.danmuji.annotation.DanMuJiPlugin;
import com.hiczp.bilibili.live.danmuji.ui.MainForm;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

/**
 * Created by czp on 17-6-21.
 */
public abstract class AbstractDanMuJiPlugin {
    private MainForm mainForm = WindowManager.getMainForm();

    public abstract void onLoad();

    public abstract void onUnload();

    public abstract void onStart();

    public abstract void onStop();

    public abstract JMenuItem createPluginMenu();

    public void printMessage(String message, Object... objects) {
        mainForm.printMessage(message, objects);
    }

    public void printMessage(Style style, String message, Object... objects) {
        mainForm.printMessage(style, message, objects);
    }

    public MainForm getMainForm() {
        return mainForm;
    }

    public StyledDocument getStyledDocument() {
        return mainForm.getStyledDocument();
    }

    public void reloadStyle() {
        mainForm.reloadStyle();
    }

    public String getName() {
        return this.getClass().getAnnotation(DanMuJiPlugin.class).name();
    }

    public String getVersion() {
        return this.getClass().getAnnotation(DanMuJiPlugin.class).version();
    }
}
