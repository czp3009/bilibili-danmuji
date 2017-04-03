package com.hiczp.bilibili.live.danmuji;


import com.hiczp.bilibili.live.api.LiveDanMuSDK;
import com.hiczp.bilibili.live.danmuji.callback.LiveDanMuCallback;
import com.hiczp.bilibili.live.danmuji.gui.MainForm;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by czp on 17-3-30.
 */
public class NetRunnable implements Runnable, Closeable {
    private int roomId;
    private MainForm mainForm;
    private Thread thread;
    private LiveDanMuSDK liveDanMuSDK;

    public NetRunnable(int roomId, MainForm mainForm) {
        this.roomId = roomId;
        this.mainForm = mainForm;
    }

    private void writeLine(String text, Object... objects) {
        Utils.writeLineToJTextArea(mainForm.getjTextArea(), true, text, objects);
    }

    @Override
    public void run() {
        writeLine("Starting net-thread...");
        thread = Thread.currentThread();
        liveDanMuSDK = new LiveDanMuSDK(roomId);
        liveDanMuSDK.setPrintDebugInfo(true);
        //回调函数
        liveDanMuSDK.setLiveDanMuCallback(new LiveDanMuCallback(mainForm, liveDanMuSDK, this));
        try {
            liveDanMuSDK.connect();
            writeLine("Connect to live server success");
        } catch (IOException e) {
            writeLine("Connect to live server failed: " + e.getMessage());
            e.printStackTrace();
            close();
        } catch (IllegalArgumentException e) {
            writeLine("Illegal argument: " + e.getMessage());
            e.printStackTrace();
            close();
        }
    }

    //回调主窗体并中断线程
    @Override
    public void close() {
        writeLine("Stopping net-thread");
        //关闭连接
        try {
            liveDanMuSDK.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //回调主窗体
        try {
            mainForm.call();
            writeLine("Stopped net-thread");
            thread.interrupt();
        } catch (Exception e) {
            writeLine("Exit net-thread failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
