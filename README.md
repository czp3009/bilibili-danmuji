# Bilibili弹幕姬
这是一个 Java 编写的简易弹幕姬, 用于实时获取直播房间的弹幕并显示.

# 构建
注意: 由于 UI 部分使用了 JetBrains 相关库和自动处理, 仅支持使用 Intellij IDEA 预编译.

Intellij IDEA 2017.1

Maven 3.3.9

openjdk-8-jdk build 1.8.0_121

# 使用
假设一个直播间 URL 为 http://live.bilibili.com/1000

由于一些直播间的 pathVariable 和 RoomID 不符, 故不能直接使用 1000 作为房间号.

在 Chrome 中访问目标直播间, 查看源代码

在 <head> 标签中找到类似如下段

    <script>
        document.domain = 'bilibili.com';

        var ROOMID = 5067;
        var DANMU_RND = 1491217361;
        var NEED_VIDEO = 1;
        var ROOMURL = 1000;
        var INITTIME = Date.now();
    </script>
    
其中的 5067 即为我们需要的 RoomID.

将其填入程序上方的输入框中, 单击 Start 按钮, enjoy!

双击中心的 JTextArea 可以隐藏上方输入栏和按钮.

在中心的 JTextArea 上右键可展开右键菜单, 可清屏, 隐藏输入框, 设定皮肤.

# 库
./danmu-api 文件夹是直播弹幕库, 若有需要可用其二次开发.

示例

    LiveDanMuSDK liveDanMuSDK=new LiveDanMuSDK(1000);   //1000 为 RoomID
    liveDanMuSDK.setPrintDebugInfo(true);   //设置在控制台输出调试信息
    //MyLiveDanMuCallBack 是 implement 于 ILiveDanMuCallback 的实体类, 用于处理回调, 需实现其中的各项方法
    liveDanMuSDK.setLiveDanMuCallback(new MyLiveDanMuCallBack());
    try {
    } catch (IOException e) {
        //Connect failed, do something here
    }
    //Other code
    liveDanMuSDK.close();   //关闭连接

# 直播弹幕协议
感谢 lyy 提供的协议分析 http://www.lyyyuna.com/2016/03/14/bilibili-danmu01/

抓取到的示例 json (包含一些注释) 在 ./danmu-api/protocol/

# 开源协议
GPL V3
