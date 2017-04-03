package com.hiczp.bilibili.live.danmuji;

import javax.swing.*;

/**
 * Created by czp on 17-4-3.
 */
public class Utils {
    public static void writeLineToJTextArea(JTextArea jTextArea, String s, Object... objects) {
        jTextArea.append(String.format(s, objects) + "\n");
        jTextArea.setCaretPosition(jTextArea.getText().length());
    }
}
