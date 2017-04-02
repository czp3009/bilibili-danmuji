package com.hiczp.bilibili.live.api;

import com.alibaba.fastjson.JSON;
import com.hiczp.bilibili.live.api.entity.DanMuMSGEntity;
import com.hiczp.bilibili.live.api.entity.JoinEntity;
import com.hiczp.bilibili.live.api.entity.SendGiftEntity;
import com.hiczp.bilibili.live.api.entity.WelcomeEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by czp on 17-3-31.
 */
class PackageRepository {
    private static final byte[] joinPackageProtocolBytes = new byte[]{0x00, 0x10, 0x00, 0x01, 0x00, 0x00, 0x00, 0x07, 0x00, 0x00, 0x00, 0x01};
    private static final byte[] joinSuccessPackageProtocolBytes = new byte[]{0x00, 0x10, 0x00, 0x01, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x01};
    private static final byte[] danMuDataPackageProtocolBytes = new byte[]{0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x00, 0x00};
    private static final byte[] onlineCountPackageProtocolBytes = new byte[]{0x00, 0x10, 0x00, 0x01, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x01};
    private static final byte[] heartBeatPackage = new byte[]{0x00, 0x00, 0x00, 0x10, 0x00, 0x10, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x01};

    private static byte[] getPackageLengthBytes(int packageLength) {
        byte[] bytes = new byte[4];
        for (int i = 3; i >= 0; i--) {
            bytes[i] = (byte) (packageLength % 256);
            packageLength = packageLength / 256;
        }
        return bytes;
    }

    private static int valueOf(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += bytes[i] * Math.pow(256, (bytes.length - 1 - i));
        }
        return value;
    }

    private static byte[] getEmptyPackageBytes(int packageLength) {
        byte[] packageBytes = new byte[packageLength];
        System.arraycopy(getPackageLengthBytes(packageLength), 0, packageBytes, 0, 4);
        return packageBytes;
    }

    private static byte[] getEmptyPackageBytes(byte[] packageLengthBytes) {
        int packageLength = valueOf(packageLengthBytes);
        byte[] packageBytes = new byte[packageLength];
        System.arraycopy(packageLengthBytes, 0, packageBytes, 0, 4);
        return packageBytes;
    }

    static byte[] getJoinPackage(int roomId) {
        byte[] jsonBytes = JSON.toJSONBytes(new JoinEntity(roomId));
        byte[] packageBytes = getEmptyPackageBytes(jsonBytes.length + 16);
        System.arraycopy(joinPackageProtocolBytes, 0, packageBytes, 4, 12);
        System.arraycopy(jsonBytes, 0, packageBytes, 16, jsonBytes.length);
        return packageBytes;
    }

    static byte[] getHeartBeatPackage() {
        return heartBeatPackage;
    }

    static byte[] readNextPackage(InputStream inputStream) throws IOException {
        byte[] packageLengthBytes = new byte[4];
        byte[] nextPackage;
        inputStream.read(packageLengthBytes);
        nextPackage = getEmptyPackageBytes(packageLengthBytes);
        inputStream.read(nextPackage, 4, nextPackage.length - 4);
        return nextPackage;
    }

    static boolean validateJoinSuccessPackage(byte[] packageBytes) {
        return getPackageType(packageBytes) == PackageType.JOIN_SUCCESS;
    }

    static PackageType getPackageType(byte[] packageBytes) {
        byte[] protocolBytes = Arrays.copyOfRange(packageBytes, 4, 16);
        if (Arrays.equals(protocolBytes, joinSuccessPackageProtocolBytes)) {
            return PackageType.JOIN_SUCCESS;
        }
        if (Arrays.equals(protocolBytes, onlineCountPackageProtocolBytes)) {
            return PackageType.ONLINE_COUNT;
        }
        if (Arrays.equals(protocolBytes, danMuDataPackageProtocolBytes)) {

        }

        return null;
    }

    static int parseOnlineCountPackage(byte[] packageBytes) {
        return valueOf(Arrays.copyOfRange(packageBytes, 16, 20));
    }

    static DanMuMSGEntity parseDanMuMSGPackage(byte[] packageBytes) {
        return null;
    }

    static SendGiftEntity parseSendGiftEntity(byte[] packageBytes) {
        return null;
    }

    static WelcomeEntity parseWelcomeEntity(byte[] packageBytes) {
        return null;
    }
}
