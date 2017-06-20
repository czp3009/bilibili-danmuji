package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmuji.ui.AboutDialog;
import com.hiczp.bilibili.live.danmuji.ui.MainForm;
import com.hiczp.bilibili.live.danmuji.ui.OutputSettingForm;

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

    public static OutputSettingForm createOutputSettingForm() {
        return new OutputSettingForm();
    }
}
