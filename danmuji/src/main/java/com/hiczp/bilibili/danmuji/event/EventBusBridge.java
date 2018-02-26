package com.hiczp.bilibili.danmuji.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusBridge {
    private EventBus to;

    public EventBusBridge(EventBus to) {
        this.to = to;
    }

    @Subscribe
    public void transferEventToAnotherEventBus(Object event) {
        to.post(event);
    }
}
