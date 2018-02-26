package com.hiczp.bilibili.danmuji.config.deserializer;

import com.google.gson.*;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

public class ColorDeserializer implements JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Color(
                jsonObject.get("red").getAsDouble(),
                jsonObject.get("green").getAsDouble(),
                jsonObject.get("blue").getAsDouble(),
                jsonObject.get("opacity").getAsDouble()
        );
    }
}
