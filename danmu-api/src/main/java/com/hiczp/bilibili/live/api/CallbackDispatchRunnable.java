package com.hiczp.bilibili.live.api;

import com.hiczp.bilibili.live.api.callback.IOnlineCountCallback;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by czp on 17-4-2.
 */
public class CallbackDispatchRunnable implements Runnable {
    private Socket socket;
    private boolean isPrintDebugInfo;
    private IOnlineCountCallback iOnlineCountCallback;

    public CallbackDispatchRunnable(Socket socket, boolean isPrintDebugInfo, IOnlineCountCallback iOnlineCountCallback) {
        this.socket = socket;
        this.isPrintDebugInfo = isPrintDebugInfo;
        this.iOnlineCountCallback = iOnlineCountCallback;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                byte[] packageBytes = PackageRepository.readNextPackage(socket);
                if (isPrintDebugInfo) {
                    System.out.println(new String(Arrays.copyOfRange(packageBytes, 4, packageBytes.length), StandardCharsets.UTF_8));
                }
                PackageType packageType = PackageRepository.getPackageType(packageBytes);
                if (packageType == null) {
                    if (isPrintDebugInfo) {
                        System.out.println("Unknown package type");
                    }
                    continue;
                }
                switch (packageType) {
                    case ONLINE_COUNT: {
                        iOnlineCountCallback.onOnlineCountPackage(PackageRepository.parseOnlineCountPackage(packageBytes));
                    }
                    break;
                }
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
