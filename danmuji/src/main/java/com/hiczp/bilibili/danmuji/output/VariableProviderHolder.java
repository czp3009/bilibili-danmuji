package com.hiczp.bilibili.danmuji.output;

import com.hiczp.bilibili.api.live.socket.entity.DanMuMsgEntity;
import com.hiczp.bilibili.api.live.socket.entity.SendGiftEntity;

import java.util.Arrays;

public class VariableProviderHolder {
    public static final VariableProvider<DanMuMsgEntity> DAN_MU_MSG_ENTITY_VARIABLE_PROVIDER = new VariableProvider<>(
            Arrays.asList(
                    new Variable<>("username", "%username", DanMuMsgEntity::getUsername),
                    new Variable<>("message", "%message", DanMuMsgEntity::getMessage)
            )
    );

    public static final VariableProvider<SendGiftEntity> SEND_GIFT_ENTITY_VARIABLE_PROVIDER = new VariableProvider<>(
            Arrays.asList(
                    new Variable<>("username", "%username", sendGiftEntity -> sendGiftEntity.getData().getUserName()),
                    new Variable<>("giftName", "%giftName", sendGiftEntity -> sendGiftEntity.getData().getGiftName()),
                    new Variable<>("number", "%number", sendGiftEntity -> sendGiftEntity.getData().getNum())
            )
    );
}
