package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmuji.ui.*;

/**
 * Created by czp on 17-6-20.
 */
public class WindowManager {
    private static MainForm mainForm;

    static MainForm createMainForm() {
        mainForm = new MainForm();
        return mainForm;
    }

    public static MainForm getMainForm() {
        return mainForm;
    }

    public static AboutDialog createAboutDialog() {
        return new AboutDialog();
    }

    public static AboutDialog createAndDisplayAboutDialog() {
        AboutDialog aboutDialog = createAboutDialog();
        aboutDialog.setVisible(true);
        return aboutDialog;
    }

    public static OutputSettingForm createOutputSettingForm() {
        return new OutputSettingForm();
    }

    public static OutputSettingForm createAndDisplayOutputSettingForm() {
        OutputSettingForm outputSettingForm = createOutputSettingForm();
        outputSettingForm.setVisible(true);
        return outputSettingForm;
    }

    public static LoginForm createLoginForm() {
        return new LoginForm();
    }

    public static LoginForm createAndDisplayLoginForm() {
        LoginForm loginForm = createLoginForm();
        loginForm.setVisible(true);
        return loginForm;
    }

    public static PluginListDialog createPluginListDialog() {
        return new PluginListDialog();
    }

    public static PluginListDialog createAndDisplayPluginListDialog() {
        PluginListDialog pluginListDialog = createPluginListDialog();
        pluginListDialog.setVisible(true);
        return pluginListDialog;
    }
}
