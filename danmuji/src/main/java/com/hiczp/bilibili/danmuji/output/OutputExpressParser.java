package com.hiczp.bilibili.danmuji.output;

import com.hiczp.bilibili.api.live.socket.entity.DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OutputExpressParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutputExpressParser.class);

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$(\\w+)");
    private static final Pattern I18N_PATTERN = Pattern.compile("%(\\w+)(?:\\(((?:\\w|,| |[^\\x00-\\xff])+)\\))?");

    public static <T extends DataEntity> String parse(String string, VariableProvider<T> variableProvider, T entity, ResourceBundle resourceBundle) {
        String result = parseVariable(string, variableProvider, entity);
        result = parseI18N(result, resourceBundle);
        return result;
    }

    //变量: $name
    public static <T extends DataEntity> String parseVariable(String string, VariableProvider<T> variableProvider, T entity) {
        List<Integer> startAndEnd = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Matcher matcher = VARIABLE_PATTERN.matcher(string);
        while (matcher.find()) {
            String name = matcher.group(1);
            Object value = variableProvider.get(name, entity);
            //如果该参数存在
            if (value != null) {
                startAndEnd.add(matcher.start());
                startAndEnd.add(matcher.end());
                values.add(value.toString());
            }
        }
        return replaceVariable(string, startAndEnd, values);
    }

    //无参数的 i18n 变量: key
    //有参数的 i18n 变量: key(arg0, arg1, arg2)
    public static String parseI18N(String string, ResourceBundle resourceBundle) {
        List<Integer> startAndEnd = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Matcher matcher = I18N_PATTERN.matcher(string);
        while (matcher.find()) {
            String key = matcher.group(1);

            String[] arguments =
                    matcher.groupCount() == 3
                            ? matcher.group(2).replaceAll(" ", "").split(",")
                            : null;

            String value;
            try {
                value = resourceBundle.getString(key);
            } catch (MissingResourceException e) {
                LOGGER.error(e.getMessage());
                continue;
            }
            if (arguments != null) {
                value = MessageFormat.format(value, (Object[]) arguments);
            }

            startAndEnd.add(matcher.start());
            startAndEnd.add(matcher.end());
            values.add(value);
        }
        return replaceVariable(string, startAndEnd, values);
    }

    private static String replaceVariable(String string, List<Integer> startAndEnd, List<String> values) {
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = startAndEnd.size() - 2; i >= 0; i -= 2) {
            stringBuilder.replace(startAndEnd.get(i), startAndEnd.get(i + 1), values.get(i / 2));
        }
        return stringBuilder.toString();
    }
}
