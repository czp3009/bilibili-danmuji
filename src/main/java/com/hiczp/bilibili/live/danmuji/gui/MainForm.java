package com.hiczp.bilibili.live.danmuji.gui;

import com.hiczp.bilibili.live.danmuji.NetRunnable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Callable;

/**
 * Created by czp on 17-3-30.
 */
public class MainForm implements Callable {
    private static final String mainFormName = "DanMuJi";

    private JPanel mainFormJPanel;
    private JTextArea textArea;
    private JTextField textField;
    private JButton startButton;
    private JButton stopButton;
    private JPanel barJPanel;
    private JLabel statusJLabel;

    private NetRunnable netRunnable;

    private MainForm() {
        //按 Enter 时点下 Start 按钮
        AWTEventListener awtEventListener = (AWTEvent awtEvent) -> {
            if (awtEvent instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) awtEvent;
                if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER && keyEvent.getID() == KeyEvent.KEY_RELEASED) {
                    if (startButton.isEnabled()) {
                        startButton.doClick();
                    }
                }
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(awtEventListener, AWTEvent.KEY_EVENT_MASK);

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
            textField.setEnabled(false);
            startButton.setEnabled(false);

            int roomId;
            try {
                roomId = Integer.valueOf(textField.getText());
            } catch (NumberFormatException e) {
                textArea.append("RoomID not a number, input again!\n");
                e.printStackTrace();
                textField.setEnabled(true);
                startButton.setEnabled(true);
                return;
            }

            netRunnable = new NetRunnable(roomId, textArea, statusJLabel, this);
            new Thread(netRunnable).start();

            stopButton.setEnabled(true);
            statusJLabel.setText("Connected");
        });

        //单击 Stop 按钮
        stopButton.addActionListener(actionEvent -> {
            stopButton.setEnabled(false);
            netRunnable.exit();
            statusJLabel.setText("Disconnect");
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
        JFrame frame = new JFrame(mainFormName);
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
