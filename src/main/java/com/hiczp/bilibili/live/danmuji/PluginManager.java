package com.hiczp.bilibili.live.danmuji;

import com.hiczp.bilibili.live.danmuji.annotation.DanMuJiPlugin;
import com.hiczp.bilibili.live.danmuji.api.AbstractDanMuJiPlugin;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by czp on 17-6-21.
 */
public class PluginManager {
    private static final File PLUGIN_DIRECTORY = new File("plugin");
    private static List<AbstractDanMuJiPlugin> danMuJiPluginList = new Vector<>();

    static void loadPlugins() {
        System.out.println("Loading plugins...");
        //创建插件目录
        if (PLUGIN_DIRECTORY.exists()) {
            //有一个和插件文件夹同名的文件存在
            if (!PLUGIN_DIRECTORY.isDirectory()) {
                System.out.println("Please remove this file before creating plugin directory: " + PLUGIN_DIRECTORY.getAbsolutePath());
                return;
            }
        } else {
            if (PLUGIN_DIRECTORY.mkdirs()) {
                System.out.println("Created plugin directory: " + PLUGIN_DIRECTORY.getAbsolutePath());
            } else {
                System.out.println("Cannot create plugin directory: " + PLUGIN_DIRECTORY.getAbsolutePath());
                return;
            }
        }

        //加载jar
        File[] files = PLUGIN_DIRECTORY.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        if (files == null) {
            return;
        }
        List<JMenuItem> pluginMenus = new ArrayList<>();
        Method addURLMethod;
        try {
            addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        addURLMethod.setAccessible(true);
        for (File file : files) {
            String pluginPath = file.getAbsolutePath();
            try {
                //获得插件中的所有类名
                JarFile jarFile = new JarFile(file);
                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                List<String> classNameList = new ArrayList<>();
                while (jarEntryEnumeration.hasMoreElements()) {
                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                    String entryName = jarEntry.getName();
                    if (entryName.endsWith(".class")) {
                        classNameList.add(entryName.substring(0, entryName.lastIndexOf(".class")).replace("/", "."));
                    }
                }
                //jar包中无类时直接开始加载下一个jar包
                if (classNameList.size() == 0) {
                    System.out.println("No classes find in: " + pluginPath);
                    continue;
                }
                //用自定义ClassLoader加载此jar包
                URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
                //寻找插件主类
                Class pluginMainClass = null;
                for (String className : classNameList) {
                    try {
                        Class clazz = urlClassLoader.loadClass(className);
                        if (AbstractDanMuJiPlugin.class.isAssignableFrom(clazz) && clazz.getAnnotation(DanMuJiPlugin.class) != null) {
                            if (pluginMainClass == null) {
                                pluginMainClass = clazz;
                            } else {    //找到了第二个主类
                                System.out.println("Multi main class in plugin: " + pluginPath);
                                pluginMainClass = null;
                                break;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                //找不到插件主类或有多个
                if (pluginMainClass == null) {
                    System.out.println("No or ambiguous plugin entrance found in: " + pluginPath);
                    continue;
                }
                //让AppClassLoader加载此jar包
                System.out.println("Find plugin: " + pluginPath);
                addURLMethod.invoke(ClassLoader.getSystemClassLoader(), file.toURI().toURL());
                //创建插件主类的实例
                AbstractDanMuJiPlugin abstractDanMuJiPlugin = (AbstractDanMuJiPlugin) pluginMainClass.newInstance();
                //执行加载时方法
                abstractDanMuJiPlugin.onLoad();
                //执行创建插件菜单方法
                JMenuItem jMenuItem = abstractDanMuJiPlugin.createPluginMenu();
                if (jMenuItem != null) {
                    pluginMenus.add(jMenuItem);
                }
                //将主类加入到插件列表
                danMuJiPluginList.add(abstractDanMuJiPlugin);
                //输出加载提示
                System.out.printf("Loaded plugin: %s %s\n", abstractDanMuJiPlugin.getName(), abstractDanMuJiPlugin.getVersion());
            } catch (Exception e) {
                System.out.println("Cannot load plugin: " + pluginPath);
                e.printStackTrace();
            }
        }

        //如果有插件创建了菜单
        if (pluginMenus.size() > 0) {
            JMenu pluginConfigMenu = WindowManager.getMainForm().getPluginConfigMenu();
            pluginMenus.forEach(pluginConfigMenu::add);
            pluginConfigMenu.setEnabled(true);
        }

        System.out.println("All plugins loaded, total: " + danMuJiPluginList.size());
    }

    public static void reloadPlugins() {
        unloadPlugins();
        loadPlugins();
    }

    public static void unloadPlugins() {
        System.out.println("Unloading plugins...");
        danMuJiPluginList.forEach(abstractDanMuJiPlugin -> {
            try {
                abstractDanMuJiPlugin.onStop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                abstractDanMuJiPlugin.onUnload();
                System.out.printf("Plugin unloaded: %s %s\n", abstractDanMuJiPlugin.getName(), abstractDanMuJiPlugin.getVersion());
            } catch (Exception e) {
                System.out.printf("Error occur while unloading plugin: %s %s\n", abstractDanMuJiPlugin.getName(), abstractDanMuJiPlugin.getVersion());
                e.printStackTrace();
            }
        });
        JMenu pluginConfigMenu = WindowManager.getMainForm().getPluginConfigMenu();
        pluginConfigMenu.removeAll();
        pluginConfigMenu.setEnabled(false);
        danMuJiPluginList.clear();
        System.out.println("All plugins unloaded!");
    }

    public static List<AbstractDanMuJiPlugin> getPluginList() {
        return danMuJiPluginList;
    }

    public static int getPluginCount() {
        return danMuJiPluginList.size();
    }

    public static Optional<AbstractDanMuJiPlugin> getPluginByName(String name) {
        for (AbstractDanMuJiPlugin abstractDanMuJiPlugin : danMuJiPluginList) {
            if (abstractDanMuJiPlugin.getName().equals(name)) {
                return Optional.of(abstractDanMuJiPlugin);
            }
        }
        return Optional.empty();
    }
}
