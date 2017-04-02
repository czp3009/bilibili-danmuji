package com.hiczp.bilibili.live.api;

import com.alibaba.fastjson.JSON;
import com.hiczp.bilibili.live.api.entity.DanMuMSGEntity;
import com.hiczp.bilibili.live.api.entity.JoinEntity;
import com.hiczp.bilibili.live.api.entity.SendGiftEntity;
import com.hiczp.bilibili.live.api.entity.WelcomeEntity;
import com.hiczp.bilibili.live.api.exception.PackageLengthUnexpectedException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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

    static byte[] getJoinPackage(int roomId) {
        byte[] jsonBytes = JSON.toJSONBytes(new JoinEntity(roomId));
        int packageLength = 16 + jsonBytes.length;
        byte[] packageBytes = new byte[packageLength];
        System.arraycopy(getPackageLengthBytes(packageLength), 0, packageBytes, 0, 4);
        System.arraycopy(joinPackageProtocolBytes, 0, packageBytes, 4, 12);
        System.arraycopy(jsonBytes, 0, packageBytes, 16, jsonBytes.length);
        return packageBytes;
    }

    static byte[] getHeartBeatPackage() {
        return heartBeatPackage;
    }

    static byte[] readNextPackage(Socket socket) throws IOException {
        byte[] buffer = new byte[socket.getReceiveBufferSize()];
        InputStream inputStream = socket.getInputStream();
        inputStream.read(buffer, 0, 4);   //数据包长度
        int packageLength = new BigInteger(1, Arrays.copyOfRange(buffer, 0, 4)).intValue();
        if (packageLength < 16) {
            throw new PackageLengthUnexpectedException("Unexpected package length: " + packageLength);
        }
        inputStream.read(buffer, 4, packageLength - 4);
        return Arrays.copyOfRange(buffer, 0, packageLength);
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
            String json = new String(Arrays.copyOfRange(packageBytes, 4, packageBytes.length), StandardCharsets.UTF_8);
            return PackageType.getByName((String) JSON.parseObject(json).get("cmd"));
        }

        return null;
    }

    static int parseOnlineCountPackage(byte[] packageBytes) {
        return new BigInteger(1, Arrays.copyOfRange(packageBytes, 16, 20)).intValue();
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
