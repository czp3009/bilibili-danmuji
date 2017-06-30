# Bilibili弹幕姬
这是一个 Java 编写的简易弹幕姬, 用于实时获取直播房间的弹幕并显示.

# 构建需求
openjdk-8-jdk

Maven 3.3.9

    mvn compile
    mvn exec:exec

# 使用
补全直播房间 URL 后, 点击 Start 按钮开始接收弹幕.

点击 Stop 按钮断开连接.

上方菜单栏包含清屏, 隐藏一些区域, 设置等多种功能, 详情不赘述.

# 插件使用
将插件 jar 放入弹幕姬工作目录下的 ./plugins/ 文件夹.

# 插件编写
插件系统使用 [pf4j](https://github.com/decebals/pf4j) 实现, 需根据其指定要求编写插件.

本项目下的 ./test-plugin 为插件编写示例.

本项目有两个已设定的 ExtensionPoint, 分别为 DanMuJiAction 和 PluginUI.

DanMuJiAction 在弹幕姬执行各种操作和状态改变时被调用.

PluginUI 在插件被加载时调用, 用于生成插件特有的 UI 部分.

插件需要打包为 jar 格式.

# B站弹幕协议
见此项目 https://github.com/czp3009/bilibili_live_danmu_api

# 开源协议
GPL V3
