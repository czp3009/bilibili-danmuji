package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.api.LiveDanMuSDK;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by czp on 17-3-30.
 */
public class NetThread implements Runnable {
    private JTextArea jTextArea;
    private Callable callable;
    private LiveDanMuSDK liveDanMuSDK;

    public NetThread(JTextArea jTextArea, Callable callable) {
        this.jTextArea = jTextArea;
        this.callable = callable;
    }

    @Override
    public void run() {
        liveDanMuSDK = new LiveDanMuSDK();
        try {
            liveDanMuSDK.connect(null);
        } catch (IOException e) {
            jTextArea.append("Connect to live server failed: " + e.getMessage() + "\n");
            e.printStackTrace();
            onExit();
        }
    }

    //回调主窗体, 进行 netThread 退出后的工作
    private void onExit() {
        try {
            callable.call();
        } catch (Exception e) {
            jTextArea.append("Exit netThread failed: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}
