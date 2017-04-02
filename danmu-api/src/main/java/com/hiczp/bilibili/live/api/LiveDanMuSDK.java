package com.hiczp.bilibili.live.api;

import com.sun.istack.internal.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;

/**
 * Created by czp on 17-3-30.
 */
public class LiveDanMuSDK implements Closeable {
    private static final String CID_INFO_URL = "http://live.bilibili.com/api/player?id=cid:";
    private static final int LIVE_SERVER_PORT = 788;

    private Socket socket;

    public void connect(@NotNull int roomId) throws IOException, IllegalArgumentException {
        String serverAddress;
        try {
            serverAddress = Jsoup.parse(new URL(CID_INFO_URL + roomId).openStream(),
                    "UTF-8", "",
                    Parser.xmlParser())
                    .select("server").first()
                    .text();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid RoomID");
        } catch (NullPointerException e) {
            throw new SocketException("Network error");
        }

        socket = new Socket(serverAddress, LIVE_SERVER_PORT);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        //发送进房数据包
        outputStream.write(PackageRepository.getJoinPackage(roomId));
        outputStream.flush();
        if (!PackageRepository.validateJoinSuccessPackage(PackageRepository.readNextPackage(inputStream))) {
            socket.close();
            throw new SocketException("Join live channel failed");
        }

        //定时发送心跳包
        new Thread(new HeartBeatRunnable(socket)).start();

        //注册回调

    }

    @Override
    public void close() throws IOException {
        if (socket != null) {
            if (!socket.isClosed()) {
                socket.close();
            }
        }
    }
}
