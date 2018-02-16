package com.hiczp.bilibili.danmuji.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Configs {
    private static final Path CONFIG_FILE_PATH = Paths.get("config.json");
    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private static Config readConfigFromDisk() throws IOException {
        if (!Files.exists(CONFIG_FILE_PATH)) {
            writeConfigToDisk(new Config());
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(CONFIG_FILE_PATH, StandardCharsets.UTF_8)) {
            return GSON.fromJson(bufferedReader, Config.class);
        }
    }

    public static Config loadConfig() throws IOException {
        return readConfigFromDisk();
    }

    public static Config resetConfig() throws IOException {
        writeConfigToDisk(new Config());
        return readConfigFromDisk();
    }

    public static void writeConfigToDisk(@Nonnull Config config) throws IOException {
        Objects.requireNonNull(config);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(CONFIG_FILE_PATH, StandardCharsets.UTF_8)) {
            bufferedWriter.write(GSON.toJson(config));
        }
    }
}
