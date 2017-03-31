package com.hiczp.bilibili.live.danmuji;


import com.hiczp.bilibili.live.api.LiveDanMuSDK;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by czp on 17-3-30.
 */
public class NetRunnable implements Runnable {
    private String roomId;
    private JTextArea jTextArea;
    private Callable callable;
    private Thread thread;
    private LiveDanMuSDK liveDanMuSDK;

    public NetRunnable(String roomId, JTextArea jTextArea, Callable callable) {
        this.roomId = roomId;
        this.jTextArea = jTextArea;
        this.callable = callable;
    }

    @Override
    public void run() {
        writeLine("Starting net-thread...");
        thread = Thread.currentThread();
        liveDanMuSDK = new LiveDanMuSDK();
        try {
            liveDanMuSDK.connect(roomId);
            writeLine("Connect to live server success");
        } catch (IOException e) {
            writeLine("Connect to live server failed: " + e.getMessage());
            e.printStackTrace();
            exit();
        }
    }

    private void writeLine(String text, Object... objects) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        jTextArea.append(String.format("[%s] ", time) + String.format(text, objects) + "\n");
        jTextArea.setCaretPosition(jTextArea.getText().length());
    }

    //退出线程并回调主窗体
    public void exit() {
        //关闭连接
        try {
            liveDanMuSDK.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread.interrupt();

        //回调主窗体
        try {
            callable.call();
            writeLine("Stopped net-thread");
        } catch (Exception e) {
            writeLine("Exit net-thread failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
