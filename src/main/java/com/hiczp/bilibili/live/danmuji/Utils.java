package com.hiczp.bilibili.live.danmuji;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by czp on 17-4-3.
 */
public class Utils {
    public static void writeLineToJTextArea(JTextArea jTextArea, String s, Object... objects) {
        writeLineToJTextArea(jTextArea, false, s, objects);
    }

    public static void writeLineToJTextArea(JTextArea jTextArea, boolean printTime, String s, Object... objects) {
        StringBuilder stringBuilder = new StringBuilder();
        if (printTime) {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            stringBuilder.append(String.format("[%s] ", time));
        }
        stringBuilder.append(String.format(s, objects));
        stringBuilder.append("\n");
        jTextArea.append(stringBuilder.toString());
        jTextArea.setCaretPosition(jTextArea.getText().length());
    }
}
