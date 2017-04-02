package com.hiczp.bilibili.live.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by czp on 17-4-1.
 */
public class HeartBeatRunnable implements Runnable {
    private Socket socket;

    public HeartBeatRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (socket.isConnected()) {
            try {
                outputStream.write(PackageRepository.getHeartBeatPackage());
                outputStream.flush();
                Thread.sleep(30000);
            } catch (Exception e) {
                break;
            }
        }
    }
}
