package com.hiczp.bilibili.danmuji.config;

import com.hiczp.bilibili.danmuji.output.OutputFormat;
import javafx.scene.paint.Color;

public class Config {
    public MainWindowSetting mainWindowSetting = new MainWindowSetting();

    public static class MainWindowSetting {
        public OutputFormat danMuJiMessageOutputFormat = new OutputFormat("[%danMuJi] $message", Color.web("#673AB7"));
        public OutputFormat activityEventOutputFormat = new OutputFormat(null, Color.web("#000000"), false);
        public OutputFormat danMuMsgOutputFormat = new OutputFormat("[%DANMU_MSG] $username: $message", Color.web("#000000"));
        public OutputFormat guardMsgOutputFormat = new OutputFormat("[%GUARD_MSG] $message", Color.web("#00BCD4"));
        public OutputFormat liveMsgOutputFormat = new OutputFormat("[%LIVE] %startLive($roomId)", Color.web("#FF9800"));
        public OutputFormat preparingOutputFormat = new OutputFormat("[%PREPARING] %stopLive($roomId)", Color.web("#FF9800"));
        public OutputFormat roomBlockMsgOutputFormat = new OutputFormat("[%ROOM_BLOCK_MSG] %userBlocked($username)", Color.web("#F44336"));
        public OutputFormat roomSilentOffOutputFormat = new OutputFormat("[%ROOM_SILENT_OFF] %roomSilentOff($roomId)", Color.web("#F44336"));
        public OutputFormat sendGiftOutputFormat = new OutputFormat("[%SEND_GIFT] $username %give $giftName * $number", Color.web("#4CAF50"));
        public OutputFormat sysGiftOutputFormat = new OutputFormat("[%SYS_GIFT] $message", Color.web("#CDDC39"));
        public OutputFormat sysMsgOutputFormat = new OutputFormat("[%SYS_MSG] $message", Color.web("#CDDC39"));
        public OutputFormat welcomeOutputFormat = new OutputFormat("[%WELCOME] %userEnterRoom($username)", Color.web("#E91E63"));
        public OutputFormat welcomeGuardOutputFormat = new OutputFormat("[%WELCOME_GUARD] %guardEnterRoom($username)", Color.web("#E91E63"));
        public OutputFormat wishBottleOutputFormat = new OutputFormat(null, Color.web("#ffffff"), false);
    }
}
