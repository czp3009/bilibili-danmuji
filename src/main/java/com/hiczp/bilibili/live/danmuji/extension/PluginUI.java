package com.hiczp.bilibili.live.danmuji.extension;

import ro.fortsoft.pf4j.ExtensionPoint;

import javax.swing.*;

/**
 * Created by czp on 17-6-29.
 */
public interface PluginUI extends ExtensionPoint {
    JMenuItem getPluginConfigMenu();
}
