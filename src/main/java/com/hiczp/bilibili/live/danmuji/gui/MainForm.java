package com.hiczp.bilibili.live.danmuji.gui;

import com.hiczp.bilibili.live.danmuji.NetRunnable;
import com.hiczp.bilibili.live.danmuji.Utils;
import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Callable;

/**
 * Created by czp on 17-3-30.
 */
public class MainForm extends JFrame implements Callable {
    private static final String mainFormName = "DanMuJi";

    private JPanel mainFormJPanel;
    private JTextArea jTextArea;
    private JTextField textField;
    private JButton startButton;
    private JButton stopButton;
    private JPanel barJPanel;

    private NetRunnable netRunnable;

    public MainForm() {
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
                jTextArea.append("RoomID not a number, input again!\n");
                e.printStackTrace();
                textField.setEnabled(true);
                startButton.setEnabled(true);
                return;
            }

            netRunnable = new NetRunnable(roomId, this);
            new Thread(netRunnable).start();

            stopButton.setEnabled(true);
            setSuffixOnTitle("Connected");
        });

        //单击 Stop 按钮
        stopButton.addActionListener(actionEvent -> {
            stopButton.setEnabled(false);
            netRunnable.close();
        });

        //双击 jTextArea 隐藏 barPanel
        jTextArea.addMouseListener(new MouseAdapter() {
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

        //设置右键菜单
        attachJPopupMenu(jTextArea);

        setTitle(mainFormName);
        setContentPane(mainFormJPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void writeLine(String text, Object... objects) {
        Utils.writeLineToJTextArea(jTextArea, true, text, objects);
    }

    private void attachJPopupMenu(JComponent jComponent) {
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem clear = new JMenuItem("Clear screen");
        JMenuItem hideBar = new JMenuItem("Hide bar");
        JMenuItem showBar = new JMenuItem("Show bar");
        showBar.setVisible(false);
        JMenu theme = new JMenu("Theme");
        JMenuItem gtk = new JMenuItem("gtk");
        JMenuItem windows = new JMenuItem("windows");
        theme.add(gtk);
        theme.add(windows);
        jPopupMenu.add(clear);
        jPopupMenu.add(hideBar);
        jPopupMenu.add(showBar);
        jPopupMenu.add(theme);

        jComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                    jPopupMenu.show(jComponent, mouseEvent.getX(), mouseEvent.getY());
                }
            }
        });

        clear.addActionListener(actionEvent -> jTextArea.setText(""));

        hideBar.addActionListener(actionEvent -> {
            barJPanel.setVisible(false);
            hideBar.setVisible(false);
            showBar.setVisible(true);
        });

        showBar.addActionListener(actionEvent -> {
            barJPanel.setVisible(true);
            showBar.setVisible(false);
            hideBar.setVisible(true);
        });

        gtk.addActionListener(actionEvent -> {
            try {
                UIManager.setLookAndFeel(GTKLookAndFeel.class.getName());
            } catch (Exception e) {
                writeLine(e.getMessage());
                e.printStackTrace();
            }
        });

        windows.addActionListener(actionEvent -> {
            try {
                UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
            } catch (Exception e) {
                writeLine(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    //网络线程退出时调用
    @Override
    public Object call() throws Exception {
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        textField.setEnabled(true);
        textField.requestFocus();
        setSuffixOnTitle("Disconnect");
        return null;
    }

    public void setSuffixOnTitle(String suffix) {
        setTitle(String.format("%s - %s", mainFormName, suffix));
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }
}
