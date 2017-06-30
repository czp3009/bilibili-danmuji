package com.hiczp.bilibili.live.danmuji.extension;

import ro.fortsoft.pf4j.ExtensionPoint;

/**
 * Created by czp on 17-6-29.
 */
public interface DanMuJiAction extends ExtensionPoint {
    void connect();

    void disconnect();
}
