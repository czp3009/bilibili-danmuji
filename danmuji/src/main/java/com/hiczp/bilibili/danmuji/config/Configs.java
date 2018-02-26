package com.hiczp.bilibili.danmuji.config;

import com.google.gson.Gson;
import com.hiczp.bilibili.danmuji.config.deserializer.ColorDeserializer;
import com.hiczp.bilibili.danmuji.config.serializer.ColorSerializer;
import javafx.scene.paint.Color;
import org.hildan.fxgson.FxGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Configs {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configs.class);

    private static final Path CONFIG_FILE_PATH = Paths.get("config.json");
    private static final Gson GSON =
            new FxGsonBuilder()
                    .acceptNullPrimitives()
                    .acceptNullProperties()
                    .builder()
                    .serializeNulls()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Color.class, new ColorSerializer())
                    .registerTypeAdapter(Color.class, new ColorDeserializer())
                    .create();

    private static Config readConfigFromDisk() throws IOException {
        if (!Files.exists(CONFIG_FILE_PATH)) {
            LOGGER.info("Config file not exists, create new one");
            writeConfigToDisk(new Config());
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(CONFIG_FILE_PATH, StandardCharsets.UTF_8)) {
            return GSON.fromJson(bufferedReader, Config.class);
        }
    }

    public static Config loadConfig() throws IOException {
        LOGGER.info("Loading config file");
        Config config = readConfigFromDisk();
        if (config == null) {
            LOGGER.warn("Config file is empty, use default configuration");
            config = new Config();
        }
        return config;
    }

    public static Config resetConfig() throws IOException {
        Config config = new Config();
        LOGGER.info("Covering config file with default configuration");
        writeConfigToDisk(config);
        return config;
    }

    public static void writeConfigToDisk(@Nonnull Config config) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(CONFIG_FILE_PATH, StandardCharsets.UTF_8)) {
            bufferedWriter.write(GSON.toJson(config));
        }
    }
}
