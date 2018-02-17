package com.hiczp.bilibili.danmuji;

import com.google.common.eventbus.EventBus;
import com.hiczp.bilibili.danmuji.bundle.UTF8Control;
import com.hiczp.bilibili.danmuji.config.Config;
import com.hiczp.bilibili.danmuji.config.Configs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class DanMuJi extends Application {
    private static final String DANMUJI_TITLE = "Bilibili DanMuJi";
    private static DanMuJi danMuJi;
    private EventBus eventBus = new EventBus("DanMuJiEventBus");
    private Status status = Status.STARTING;
    private Config config;

    public static DanMuJi getDanMuJi() {
        return danMuJi;
    }

    @Override
    public void init() throws Exception {
        danMuJi = this;
        config = Configs.loadConfig();

        status = Status.READY;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(DANMUJI_TITLE);
        FXMLLoader fxmlLoader = new FXMLLoader(
                DanMuJi.class.getResource("/view/MainWindow.fxml"),
                ResourceBundle.getBundle("bundle.MainWindow", Locale.getDefault(), UTF8Control.getInstance())
        );
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        status = Status.CLOSING;

        Configs.writeConfigToDisk(config);
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

    //弹幕姬状态
    public enum Status {
        STARTING,
        READY,
        CONNECTING,
        CONNECTED,
        CLOSING
    }
}
