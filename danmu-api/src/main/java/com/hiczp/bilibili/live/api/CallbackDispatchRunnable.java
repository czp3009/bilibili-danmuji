package com.hiczp.bilibili.live.api;

import com.hiczp.bilibili.live.api.callback.ILiveDanMuCallback;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by czp on 17-4-2.
 */
class CallbackDispatchRunnable implements Runnable {
    private Socket socket;
    private boolean isPrintDebugInfo;
    private ILiveDanMuCallback liveDanMuCallback;

    CallbackDispatchRunnable(Socket socket, boolean isPrintDebugInfo, ILiveDanMuCallback liveDanMuCallback) {
        this.socket = socket;
        this.isPrintDebugInfo = isPrintDebugInfo;
        this.liveDanMuCallback = liveDanMuCallback;
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
                        liveDanMuCallback.onOnlineCountPackage(PackageRepository.parseOnlineCountPackage(packageBytes));
                    }
                    break;
                    case DANMU_MSG: {
                        liveDanMuCallback.onDanMuMSGPackage(PackageRepository.parseDanMuMSGPackage(packageBytes));
                    }
                    break;
                    case SEND_GIFT: {
                        liveDanMuCallback.onSendGiftPackage(PackageRepository.parseSendGiftEntity(packageBytes));
                    }
                    break;
                    case WELCOME: {
                        liveDanMuCallback.onWelcomePackage(PackageRepository.parseWelcomeEntity(packageBytes));
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
