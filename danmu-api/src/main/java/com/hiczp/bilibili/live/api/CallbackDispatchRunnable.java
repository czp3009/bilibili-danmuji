package com.hiczp.bilibili.live.api;

import com.hiczp.bilibili.live.api.callback.ILiveDanMuCallback;

import java.io.IOException;
import java.math.BigInteger;
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
                PackageType packageType = PackageRepository.getPackageType(packageBytes);
                if (packageType == null) {
                    if (isPrintDebugInfo) {
                        System.out.println("Unknown package type");
                        Utils.printBytes(packageBytes);
                        System.out.println();
                    }
                    continue;
                }
                if (isPrintDebugInfo) {
                    if (packageType == PackageType.ONLINE_COUNT) {
                        System.out.println("Viewers: " + new BigInteger(Arrays.copyOfRange(packageBytes, 16, packageBytes.length)).intValue());
                    } else {
                        System.out.println(new String(Arrays.copyOfRange(packageBytes, 16, packageBytes.length), StandardCharsets.UTF_8));
                    }
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
                        liveDanMuCallback.onSendGiftPackage(PackageRepository.parseSendGiftPackage(packageBytes));
                    }
                    break;
                    case WELCOME: {
                        liveDanMuCallback.onWelcomePackage(PackageRepository.parseWelcomePackage(packageBytes));
                    }
                    break;
                    case SYS_MSG: {
                        liveDanMuCallback.onSYSMSGPackage(PackageRepository.parseSysMSGPackage(packageBytes));
                    }
                    break;
                    case SYS_GIFT: {
                        liveDanMuCallback.onSysGiftPackage(PackageRepository.parseSysGiftPackage(packageBytes));
                    }
                    break;
                    case LIVE: {
                        liveDanMuCallback.onLivePackage(PackageRepository.parseLivePackage(packageBytes));
                    }
                    break;
                    case PREPARING: {
                        liveDanMuCallback.onPreparingPackage(PackageRepository.parsePreparingPackage(packageBytes));
                    }
                }
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
