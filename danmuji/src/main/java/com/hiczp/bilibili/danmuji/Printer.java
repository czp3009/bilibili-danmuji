package com.hiczp.bilibili.danmuji;

import com.hiczp.bilibili.api.live.socket.entity.DanMuMsgEntity;
import com.hiczp.bilibili.api.live.socket.entity.SendGiftEntity;
import com.hiczp.bilibili.danmuji.bundle.UTF8Control;
import com.hiczp.bilibili.danmuji.output.OutputExpressParser;
import com.hiczp.bilibili.danmuji.output.OutputFormat;
import com.hiczp.bilibili.danmuji.output.VariableProviderHolder;
import javafx.scene.text.Text;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Printer {
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bundle.Message", Locale.getDefault(), new UTF8Control());
    private static DanMuJi danMuJi;

    static void init(DanMuJi danMuJi) {
        Printer.danMuJi = danMuJi;
    }

    public static void print(Text text) {
        danMuJi.getMainWindowController().printText(text);
    }

    public static void printDanMuJiMessage(String key, Object... arguments) {
        OutputFormat outputFormat = danMuJi.getConfig().mainWindowSetting.danMuJiMessageOutputFormat;
        if (!checkNeeded(outputFormat)) {
            return;
        }
        String message = MessageFormat.format(RESOURCE_BUNDLE.getString(key), arguments);
        String string = outputFormat.text
                .replaceAll("\\$message", message);
        string = OutputExpressParser.parseI18N(string, RESOURCE_BUNDLE);
        Text text = new Text(string);
        text.setFill(outputFormat.color);
        print(text);
    }

    public static void printDanMuMsg(DanMuMsgEntity danMuMsgEntity) {
        OutputFormat outputFormat = danMuJi.getConfig().mainWindowSetting.danMuMsgOutputFormat;
        if (!checkNeeded(outputFormat)) {
            return;
        }
        String string = OutputExpressParser.parse(
                outputFormat.text,
                VariableProviderHolder.DAN_MU_MSG_ENTITY_VARIABLE_PROVIDER,
                danMuMsgEntity,
                RESOURCE_BUNDLE
        );
        Text text = new Text(string);
        text.setFill(outputFormat.color);
        print(text);
    }

    public static void printSendGift(SendGiftEntity sendGiftEntity) {
        OutputFormat outputFormat = danMuJi.getConfig().mainWindowSetting.sendGiftOutputFormat;
        if (!checkNeeded(outputFormat)) {
            return;
        }
        String string = OutputExpressParser.parse(
                outputFormat.text,
                VariableProviderHolder.SEND_GIFT_ENTITY_VARIABLE_PROVIDER,
                sendGiftEntity,
                RESOURCE_BUNDLE
        );
        Text text = new Text(string);
        text.setFill(outputFormat.color);
        print(text);
    }

    private static boolean checkNeeded(OutputFormat outputFormat) {
        return outputFormat.display && outputFormat.text != null;
    }
}
