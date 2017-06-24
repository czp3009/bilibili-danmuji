package com.hiczp.bilibili.live.danmuji.plugin.autoreply;

import com.hiczp.bilibili.live.danmu.api.LiveDanMuCallbackAdapter;
import com.hiczp.bilibili.live.danmu.api.entity.DanMuResponseEntity;
import com.hiczp.bilibili.live.danmu.api.entity.SendGiftEntity;
import com.hiczp.bilibili.live.danmuji.Config;
import com.hiczp.bilibili.live.danmuji.DanMuJi;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by czp on 17-6-23.
 */
public class DanMuCallback extends LiveDanMuCallbackAdapter {
    private Main main;
    private Vector<Thread> danMuSendingThreads = new Vector<>();

    DanMuCallback(Main main) {
        this.main = main;
    }

    void stopDanMuSendingThreads() {
        danMuSendingThreads.forEach(thread -> {
            if (!thread.isInterrupted()) {
                try {
                    thread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onSendGiftPackage(SendGiftEntity sendGiftEntity) {
        if (DanMuJi.isLiveDanMuSenderAvailable()) {
            new Thread(() -> {
                danMuSendingThreads.add(Thread.currentThread());
                try {
                    DanMuResponseEntity danMuResponseEntity = DanMuJi.getLiveDanMuSender().send(
                            String.format("感谢 %s 送的 %s", sendGiftEntity.data.uname, sendGiftEntity.data.giftName)
                    );
                    switch (danMuResponseEntity.code) {
                        case DanMuResponseEntity.NO_LOGIN: {
                            main.printMessage("[%s] cookies incorrect!", main.getName());
                        }
                        break;
                        case DanMuResponseEntity.OUT_OF_LENGTH: {
                            main.printMessage("[%s] Bullet screen only can contains up to %d Unicode characters!", main.getName(), Config.DANMU_MAX_LENGTH);
                        }
                        break;
                        case DanMuResponseEntity.SUCCESS: {
                            if (!danMuResponseEntity.msg.equals("")) {
                                main.printMessage("[%s] %s", main.getName(), danMuResponseEntity.msg);
                            }
                        }
                        break;
                        default: {
                            main.printMessage("[%s] Unknown error: %d %s", main.getName(), danMuResponseEntity.code, danMuResponseEntity.msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    danMuSendingThreads.remove(Thread.currentThread());
                }
            }).start();
        }
    }
}
