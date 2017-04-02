package com.hiczp.bilibili.live.danmuji.callback;

import com.hiczp.bilibili.live.api.callback.IOnlineCountCallback;

import javax.swing.*;

/**
 * Created by czp on 17-4-2.
 */
public class OnlineCountCallback implements IOnlineCountCallback {
    private JLabel jLabel;

    public OnlineCountCallback(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    @Override
    public void onOnlineCountPackage(int onlineCount) {
        jLabel.setText("Viewer " + onlineCount);
    }
}
