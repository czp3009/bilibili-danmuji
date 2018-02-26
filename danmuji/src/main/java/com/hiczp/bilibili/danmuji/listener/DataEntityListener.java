package com.hiczp.bilibili.danmuji.listener;

import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.hiczp.bilibili.api.live.socket.event.DanMuMsgPackageEvent;
import com.hiczp.bilibili.api.live.socket.event.ReceiveDataPackageEvent;
import com.hiczp.bilibili.api.live.socket.event.SendGiftPackageEvent;
import com.hiczp.bilibili.danmuji.Printer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEntityListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataEntityListener.class);
    private static final Gson GSON = new Gson();

    //log
    @Subscribe
    private void receiveDataPackage(ReceiveDataPackageEvent receiveDataPackageEvent) {
        LOGGER.info("Receive: {}", GSON.toJson(receiveDataPackageEvent.getEntity()));
    }

    @Subscribe
    private void danMuMsg(DanMuMsgPackageEvent danMuMsgPackageEvent) {
        Printer.printDanMuMsg(danMuMsgPackageEvent.getEntity());
    }

    @Subscribe
    private void sendGift(SendGiftPackageEvent sendGiftPackageEvent) {
        Printer.printSendGift(sendGiftPackageEvent.getEntity());
    }
}
