package com.hiczp.bilibili.danmuji.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hiczp.bilibili.danmuji.DanMuJi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Configs {
    private static final String CONFIG_FILE_NAME = "config.json";
    private static final Path CONFIG_FILE_PATH = Paths.get(CONFIG_FILE_NAME);
    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private static void releaseConfigFileToDisk(boolean force) throws IOException {
        //如果非强制并且文件存在则直接返回
        if (!force && Files.exists(CONFIG_FILE_PATH)) {
            return;
        }
        try {
            Files.copy(
                    Paths.get(DanMuJi.class.getResource("/" + CONFIG_FILE_NAME).toURI()),
                    CONFIG_FILE_PATH
            );
        } catch (URISyntaxException e) {
            throw new Error(e);
        }
    }

    private static Config readConfigFromDisk() throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(CONFIG_FILE_PATH)) {
            return GSON.fromJson(bufferedReader, Config.class);
        }
    }

    public static Config loadConfig() throws IOException {
        releaseConfigFileToDisk(false);
        return readConfigFromDisk();
    }

    public static Config resetConfig() throws IOException {
        releaseConfigFileToDisk(true);
        return readConfigFromDisk();
    }

    public static void writeConfigToDisk(Config config) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(CONFIG_FILE_PATH)) {
            bufferedWriter.write(GSON.toJson(config));
        }
    }
}
