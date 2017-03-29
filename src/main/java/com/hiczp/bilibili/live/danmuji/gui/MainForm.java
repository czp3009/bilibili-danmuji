package com.hiczp.bilibili.live.danmuji.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by czp on 17-3-30.
 */
public class MainForm {
    private JPanel mainFormJPanel;
    private JTextArea textArea;
    private JTextField textField;
    private JButton startButton;
    private JButton stopButton;
    private JPanel barJPanel;

    public MainForm() {
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    if (barJPanel.isVisible()) {
                        barJPanel.setVisible(false);
                    } else {
                        barJPanel.setVisible(true);
                    }
                }
            }
        });
    }

    public static void main() {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainFormJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
