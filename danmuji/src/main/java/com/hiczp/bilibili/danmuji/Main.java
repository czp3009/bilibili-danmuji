package com.hiczp.bilibili.danmuji;

import javafx.application.Application;
import org.apache.log4j.BasicConfigurator;

public class Main {
    public static void main(String[] args) {
        //检查命令行参数
        checkArgs(args);

        //配置 slf4j
        BasicConfigurator.configure();

        //启动 JavaFX
        Application.launch(DanMuJi.class);
    }

    private static void checkArgs(String[] args) {

    }
}
