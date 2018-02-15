package com.hiczp.bilibili.danmuji;

import com.hiczp.bilibili.danmuji.bundle.UTF8Control;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class DanMuJi extends Application {
    private static DanMuJi danMuJi;

    static void run(String[] args) {
        launch(args);
    }

    public static DanMuJi getDanMuJi() {
        return danMuJi;
    }

    @Override
    public void init() throws Exception {
        danMuJi = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainWindowFXMLLoader = new FXMLLoader(
                DanMuJi.class.getResource("/view/MainWindow.fxml"),
                ResourceBundle.getBundle("bundle.MainWindow", Locale.getDefault(), UTF8Control.getInstance())
        );

        primaryStage.setScene(new Scene(mainWindowFXMLLoader.load()));
        primaryStage.show();
    }
}
