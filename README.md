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
将插件 jar 放入弹幕姬工作目录下的 ./plugin/ 文件夹.

# 插件编写
插件内有且仅有一个类(此类即为插件主类)继承于 AbstractDanMuJiPlugin , 并拥有 @DanMuJiPlugin 注解即可被识别为插件.

主类需实现 AbstractDanMuJiPlugin 中的方法, 这些方法在各个阶段被调用.

插件需要打包为 jar 格式, 主类可以放在任何位置, 插件 jar 包中的全部类会被加载.

# B站弹幕协议
见此项目 https://github.com/czp3009/bilibili_live_danmu_api

# 开源协议
GPL V3
