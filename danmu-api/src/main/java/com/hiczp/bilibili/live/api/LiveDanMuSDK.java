package com.hiczp.bilibili.live.api;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by czp on 17-3-30.
 */
public class LiveDanMuSDK {
    private Socket socket;

    public void connect(String serverAddress) throws IOException {
        socket = new Socket(serverAddress, 0);
    }
}
