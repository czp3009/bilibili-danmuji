# Bilibili直播弹幕库
示例

    LiveDanMuSDK liveDanMuSDK = new LiveDanMuSDK(1000);   //1000 为 RoomID
    liveDanMuSDK.setPrintDebugInfo(true);   //设置在控制台输出调试信息
    //MyLiveDanMuCallBack 是 implement 于 ILiveDanMuCallback 的实体类, 用于处理回调, 需实现其中的各项方法
    liveDanMuSDK.setLiveDanMuCallback(new MyLiveDanMuCallBack());
    try {
        liveDanMuSDK.connect(); //连接, 可能出现 Socket 相关异常
    } catch (IOException e) {
        //Connect failed, do something here
    }
    //Other code
    liveDanMuSDK.close();   //关闭连接

    
# 开源协议
GPL V3
