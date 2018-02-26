package com.hiczp.bilibili.danmuji.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.hiczp.bilibili.api.BilibiliAPI;
import com.hiczp.bilibili.api.live.socket.LiveClient;
import com.hiczp.bilibili.api.live.socket.event.ConnectSucceedEvent;
import com.hiczp.bilibili.api.live.socket.event.ConnectionCloseEvent;
import com.hiczp.bilibili.api.live.socket.event.ReceiveRoomStatusPackageEvent;
import com.hiczp.bilibili.api.live.socket.event.ViewerCountPackageEvent;
import com.hiczp.bilibili.danmuji.DanMuJi;
import com.hiczp.bilibili.danmuji.Printer;
import com.hiczp.bilibili.danmuji.event.DanMuJiConnectingEvent;
import com.hiczp.bilibili.danmuji.event.DanMuJiReadyEvent;
import com.hiczp.bilibili.danmuji.event.EventBusBridge;
import com.hiczp.bilibili.danmuji.event.UserPressDisconnectButtonEvent;
import com.hiczp.bilibili.danmuji.listener.DataEntityListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowController.class);

    private final DanMuJi danMuJi = DanMuJi.getDanMuJi();
    private final BilibiliAPI bilibiliAPI = danMuJi.getBilibiliAPI();
    private final EventBus danMuJiEventBus = danMuJi.getEventBus();

    @FXML
    private JFXTextField roomIdTextField;
    @FXML
    private HBox connectionButtonsHBox;
    @FXML
    private JFXButton connectButton;
    @FXML
    private JFXButton disconnectButton;
    @FXML
    private Label roomStatusLabel;
    @FXML
    private Label popularityLabel;
    @FXML
    private Label followCountLabel;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextFlow textFlow;
    @FXML
    private HBox sendingBulletScreenHBox;
    @FXML
    private JFXTextField inputBulletScreenTextField;
    @FXML
    private JFXButton sendButton;

    private Thread connectionThread;
    private LiveClient liveClient;
    private EventBusBridge eventBusBridge;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //注册事件监听
        danMuJiEventBus.register(this);
        danMuJiEventBus.register(new DataEntityListener());

        //未输入房间号时禁用 connectButton 与 disconnectButton
        roomIdTextField.textProperty().addListener((observable, oldValue, newValue) ->
                connectionButtonsHBox.setDisable(newValue.isEmpty())
        );

        //房间号输入框只能输入数字
        roomIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                roomIdTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        //房间号输入框快捷键
        roomIdTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                connectButton.fire();
            }
        });

        //TODO TextFlow 的自动滚动是有问题的
        //自动滚动
        textFlow.getChildren().addListener((ListChangeListener<Node>) change -> {
            scrollPane.requestLayout();
            scrollPane.setVvalue(scrollPane.getVmax());
        });

        //弹幕输入框为空时禁用发送按钮
        inputBulletScreenTextField.textProperty().addListener((observable, oldValue, newValue) ->
                sendButton.setDisable(newValue.isEmpty())
        );

        //弹幕输入框快捷键
        inputBulletScreenTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendButton.fire();
            }
        });
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void connect() {
        LOGGER.debug("User press connectButton");
        //UI 变动
        roomIdTextField.setDisable(true);
        connectButton.setDisable(true);
        disconnectButton.setDisable(false);
        Printer.printDanMuJiMessage("danMuJi.connecting");

        //弹幕姬状态改变
        danMuJiEventBus.post(new DanMuJiConnectingEvent(this));

        //连接
        connectionThread = new Thread(() -> {
            liveClient = bilibiliAPI.getLiveClient(Integer.valueOf(roomIdTextField.getText()));
            eventBusBridge = new EventBusBridge(danMuJiEventBus);
            liveClient.registerListener(eventBusBridge);
            try {
                liveClient.connect();
            } catch (IOException e) {
                if (e instanceof InterruptedIOException) {
                    LOGGER.warn("User press disconnect button while connecting");
                } else {
                    LOGGER.error("Connect failed: " + e.getMessage());
                    e.printStackTrace();
                }
                disconnectButton.fire();
            } catch (IllegalArgumentException e) {
                LOGGER.error("Invalid room id");
                LOGGER.error(e.getMessage());
                disconnectButton.fire();
            }
        });
        connectionThread.setDaemon(true);
        connectionThread.start();
    }

    @FXML
    private void disconnect() {
        LOGGER.debug("User press disconnectButton");
        //关闭连接直播间用的后台线程
        if (connectionThread.isAlive()) {
            connectionThread.interrupt();
        }
        //关闭 LiveClient
        new Thread(() -> liveClient.close()).start();
        //发布事件
        danMuJiEventBus.post(new UserPressDisconnectButtonEvent(disconnectButton));
        //改变 UI
        roomIdTextField.setDisable(false);
        connectButton.setDisable(false);
        disconnectButton.setDisable(true);
        sendingBulletScreenHBox.setDisable(true);
        inputBulletScreenTextField.setText("");
    }

    @Subscribe
    private void onDanMuJiReady(DanMuJiReadyEvent danMuJiReadyEvent) {
        Printer.printDanMuJiMessage("danMuJi.danMuJiReady.regexp", DanMuJi.getVersion());
    }

    @Subscribe
    private void onClientConnectSucceed(ConnectSucceedEvent connectSucceedEvent) {
        LOGGER.info("Connect succeed");
        Printer.printDanMuJiMessage("danMuJi.connectSucceed");
        liveClient.getRoomInfo().ifPresent(liveRoomEntity ->
                Platform.runLater(() -> {
                    roomStatusLabel.setText(liveRoomEntity.getStatus());
                    popularityLabel.setText(Integer.toString(liveRoomEntity.getOnline()));
                    followCountLabel.setText(Integer.toString(liveRoomEntity.getAttention()));
                    sendingBulletScreenHBox.setDisable(false);
                })
        );
    }

    @Subscribe
    private void onClientDisconnect(ConnectionCloseEvent connectionCloseEvent) {
        Printer.printDanMuJiMessage("danMuJi.disconnect");
        //断开事件传递
        liveClient.unregisterListeners(eventBusBridge);
        Platform.runLater(() -> {
            roomStatusLabel.setText("null");
            popularityLabel.setText("null");
            followCountLabel.setText("null");
        });
    }

    @Subscribe
    private void onRoomStatusChange(ReceiveRoomStatusPackageEvent receiveRoomStatusPackageEvent) {
        Platform.runLater(() ->
                roomStatusLabel.setText(receiveRoomStatusPackageEvent.getEntity0().getCmd())
        );
    }

    @Subscribe
    private void onViewerCountChange(ViewerCountPackageEvent viewerCountPackageEvent) {
        Platform.runLater(() ->
                popularityLabel.setText(Integer.toString(viewerCountPackageEvent.getViewerCount()))
        );
    }

    public void printText(Text text) {
        Platform.runLater(() -> {
            text.setText(text.getText() + "\n");
            textFlow.getChildren().add(text);
        });
    }

    public void closeConnection() throws IOException {
        if (liveClient != null) {
            new Thread(() -> liveClient.close()).start();
        }
    }
}
