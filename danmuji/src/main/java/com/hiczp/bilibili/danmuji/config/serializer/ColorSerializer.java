package com.hiczp.bilibili.danmuji.config.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

public class ColorSerializer implements JsonSerializer<Color> {
    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("red", src.getRed());
        jsonObject.addProperty("green", src.getGreen());
        jsonObject.addProperty("blue", src.getBlue());
        jsonObject.addProperty("opacity", src.getOpacity());
        return jsonObject;
    }
}
