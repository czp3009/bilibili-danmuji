package com.hiczp.bilibili.danmuji.output;

import javafx.scene.paint.Color;

public class OutputFormat {
    public String text;
    public Color color;
    public boolean display = true;

    public OutputFormat(String text, Color color) {
        this.text = text;
        this.color = color;
    }

    public OutputFormat(String text, Color color, boolean display) {
        this.text = text;
        this.color = color;
        this.display = display;
    }
}
