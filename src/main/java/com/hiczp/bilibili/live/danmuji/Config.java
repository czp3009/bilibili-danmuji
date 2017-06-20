package com.hiczp.bilibili.live.danmuji;

import com.alibaba.fastjson.JSON;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by czp on 17-6-2.
 */
public class Config {
    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String GITHUB_REPOSITORY = "https://github.com/czp3009/danmuji";
    public static final int DANMU_MAX_LENGTH = 20;

    public static boolean userWantDisconnect = false;

    public boolean debug = false;
    public String roomId;
    public String cookies;

    public OutputOptions Connect = new OutputOptions();
    public OutputOptions Disconnect = new OutputOptions();
    public OutputOptions DanMu = new OutputOptions();
    public OutputOptions SysMSG = new OutputOptions();
    public OutputOptions SendGift = new OutputOptions();
    public OutputOptions SysGift = new OutputOptions();
    public OutputOptions Welcome = new OutputOptions();
    public OutputOptions WelcomeGuard = new OutputOptions();
    public OutputOptions Live = new OutputOptions();
    public OutputOptions Preparing = new OutputOptions();
    public OutputOptions RoomAdmins = new OutputOptions();

    {
        Connect.color = Color.RED;
        Disconnect.color = Color.RED;
        SysMSG.color = Color.BLUE;
        SendGift.color = new Color(0, 128, 0);
        SysGift.color = Color.BLUE;
        WelcomeGuard.color = Color.LIGHT_GRAY;
        Live.color = Color.GREEN;
        Preparing.color = Color.GREEN;
        RoomAdmins.color = Color.BLUE;
    }

    public void storeToFile() {
        File file = new File(CONFIG_FILE_NAME);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println("Cannot write config file to disk!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(JSON.toJSONBytes(this));
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class OutputOptions {
        public int size = 16;
        public Color color = Color.BLACK;
        public boolean on = true;
    }
}
