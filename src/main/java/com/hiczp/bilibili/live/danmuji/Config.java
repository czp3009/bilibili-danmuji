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
    public static final File CONFIG_FILE = new File("config.json");
    public static final String GITHUB_REPOSITORY = "https://github.com/czp3009/danmuji";
    public static final int DANMU_MAX_LENGTH = 20;

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

    public boolean storeToFile() {
        if (!CONFIG_FILE.exists()) {
            try {
                if (!CONFIG_FILE.createNewFile()) {
                    System.out.println("File already exists: " + CONFIG_FILE.getAbsolutePath());
                    return false;
                }
            } catch (IOException e) {
                System.out.println("Cannot create config file on disk: " + CONFIG_FILE.getAbsolutePath());
                e.printStackTrace();
                return false;
            }
        }
        if (!CONFIG_FILE.canWrite()) {
            System.out.println("Cannot write config file: " + CONFIG_FILE.getAbsolutePath());
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(CONFIG_FILE);
            fileOutputStream.write(JSON.toJSONBytes(this));
            fileOutputStream.flush();
            System.out.println("Write configuration to file: " + CONFIG_FILE.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public class OutputOptions {
        public int size = 16;
        public Color color = Color.BLACK;
        public boolean on = true;
    }
}
