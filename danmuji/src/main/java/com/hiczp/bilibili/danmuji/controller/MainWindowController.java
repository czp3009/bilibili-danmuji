package com.hiczp.bilibili.danmuji.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private TextFlow textFlow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFlow.getChildren().add(new Text("弹幕姬初始化完毕, 版本 V0.0.1"));
    }
}
