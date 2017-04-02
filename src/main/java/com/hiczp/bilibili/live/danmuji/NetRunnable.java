package com.hiczp.bilibili.live.danmuji;


import com.hiczp.bilibili.live.api.LiveDanMuSDK;
import com.hiczp.bilibili.live.danmuji.callback.LiveDanMuCallback;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by czp on 17-3-30.
 */
public class NetRunnable implements Runnable {
    private int roomId;
    private JTextArea jTextArea;
    private JLabel jLabel;
    private Callable callable;
    private Thread thread;
    private LiveDanMuSDK liveDanMuSDK;

    public NetRunnable(int roomId, JTextArea jTextArea, JLabel jLabel, Callable callable) {
        this.roomId = roomId;
        this.jTextArea = jTextArea;
        this.jLabel = jLabel;
        this.callable = callable;
    }

    @Override
    public void run() {
        writeLine("Starting net-thread...");
        thread = Thread.currentThread();
        liveDanMuSDK = new LiveDanMuSDK();
        liveDanMuSDK.setPrintDebugInfo(true);
        //回调函数
        liveDanMuSDK.setLiveDanMuCallback(new LiveDanMuCallback(jLabel, jTextArea));
        try {
            liveDanMuSDK.connect(roomId);
            writeLine("Connect to live server success");
        } catch (IOException e) {
            writeLine("Connect to live server failed: " + e.getMessage());
            e.printStackTrace();
            exit();
        } catch (IllegalArgumentException e) {
            writeLine("Illegal argument: " + e.getMessage());
            e.printStackTrace();
            exit();
        }
    }

    private void writeLine(String text, Object... objects) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        jTextArea.append(String.format("[%s] ", time) + String.format(text, objects) + "\n");
        jTextArea.setCaretPosition(jTextArea.getText().length());
    }

    //回调主窗体并中断线程
    public void exit() {
        writeLine("Stopping net-thread");
        //关闭连接
        try {
            liveDanMuSDK.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //回调主窗体
        try {
            callable.call();
            writeLine("Stopped net-thread");
            thread.interrupt();
        } catch (Exception e) {
            writeLine("Exit net-thread failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
