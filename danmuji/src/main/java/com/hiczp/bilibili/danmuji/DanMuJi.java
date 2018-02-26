package com.hiczp.bilibili.danmuji;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.hiczp.bilibili.api.BilibiliAPI;
import com.hiczp.bilibili.api.live.socket.event.ConnectSucceedEvent;
import com.hiczp.bilibili.danmuji.bundle.UTF8Control;
import com.hiczp.bilibili.danmuji.config.Config;
import com.hiczp.bilibili.danmuji.config.Configs;
import com.hiczp.bilibili.danmuji.controller.MainWindowController;
import com.hiczp.bilibili.danmuji.event.DanMuJiConnectingEvent;
import com.hiczp.bilibili.danmuji.event.DanMuJiReadyEvent;
import com.hiczp.bilibili.danmuji.event.UserPressDisconnectButtonEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class DanMuJi extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(DanMuJi.class);

    private static DanMuJi danMuJi;
    private EventBus eventBus = new EventBus("DanMuJiEventBus");
    private Status status = Status.STARTING;
    private Config config;
    private BilibiliAPI bilibiliAPI;

    private MainWindowController mainWindowController;

    public static DanMuJi getDanMuJi() {
        return danMuJi;
    }

    public static String getVersion() {
        String versionInManifest = DanMuJi.class.getPackage().getImplementationVersion();
        return versionInManifest != null ? versionInManifest : "developmentVersion";
    }

    @Override
    public void init() throws Exception {
        LOGGER.info("Init DanMuJi");
        danMuJi = this;
        config = Configs.loadConfig();
        bilibiliAPI = new BilibiliAPI();
        eventBus.register(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Preparing DanMuJi GUI");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle.MainWindow", Locale.getDefault(), UTF8Control.getInstance());
        FXMLLoader fxmlLoader = new FXMLLoader(
                DanMuJi.class.getResource("/view/MainWindow.fxml"),
                resourceBundle
        );
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        mainWindowController = fxmlLoader.getController();
        primaryStage.setTitle(resourceBundle.getString("title"));
        primaryStage.show();
        LOGGER.info("DanMuJi main window opened");

        status = Status.READY;
        Printer.init(this);
        LOGGER.info("DanMuJi init finished");
        eventBus.post(new DanMuJiReadyEvent(this));
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("Closing DanMuJi");
        status = Status.CLOSING;

        mainWindowController.closeConnection();
        Configs.writeConfigToDisk(config);
    }

    @Subscribe
    private void onConnecting(DanMuJiConnectingEvent danMuJiConnectingEvent) {
        status = Status.CONNECTING;
    }

    @Subscribe
    private void onConnect(ConnectSucceedEvent connectSucceedEvent) {
        status = Status.CONNECTED;
    }

    @Subscribe
    private void onDisconnect(UserPressDisconnectButtonEvent userPressDisconnectButtonEvent) {
        status = Status.READY;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Status getStatus() {
        return status;
    }

    public Config getConfig() {
        return config;
    }

    public BilibiliAPI getBilibiliAPI() {
        return bilibiliAPI;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    //弹幕姬状态
    public enum Status {
        STARTING,
        READY,
        CONNECTING,
        CONNECTED,
        CLOSING
    }
}
