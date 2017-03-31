package com.hiczp.bilibili.live.danmuji.gui;

import com.hiczp.bilibili.live.danmuji.NetRunnable;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;

/**
 * Created by czp on 17-3-30.
 */
public class MainForm implements Callable {
    private JPanel mainFormJPanel;
    private JTextArea textArea;
    private JTextField textField;
    private JButton startButton;
    private JButton stopButton;
    private JPanel barJPanel;

    private NetRunnable netRunnable;

    private MainForm() {
        //textField 无字时禁用 Start 按钮
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (textField.getText().isEmpty()) {
                    startButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                }
            }
        });

        //单击 Start 按钮
        startButton.addActionListener(actionEvent -> {
            startButton.setEnabled(false);
            textField.setEnabled(false);

            netRunnable = new NetRunnable(textField.getText(), textArea, this);
            new Thread(netRunnable).start();

            stopButton.setEnabled(true);
        });

        //单击 Stop 按钮
        stopButton.addActionListener(actionEvent -> {
            stopButton.setEnabled(false);
            netRunnable.exit();
        });

        //双击 textArea 隐藏 barPanel
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

    //网络线程退出时调用
    @Override
    public Object call() throws Exception {
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        textField.setEnabled(true);
        textField.requestFocus();
        return null;
    }
}
