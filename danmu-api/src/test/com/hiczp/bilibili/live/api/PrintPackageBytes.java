package com.hiczp.bilibili.live.api;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by czp on 17-3-31.
 */
public class PrintPackageBytes {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("livecmt-2.bilibili.com", 788);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(PackageRepository.getJoinPackage(39189));
            outputStream.flush();
            new Thread(new HeartBeatRunnable(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Utils.printBytes(PackageRepository.readNextPackage(socket));
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
