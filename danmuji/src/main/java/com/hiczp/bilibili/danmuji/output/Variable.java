package com.hiczp.bilibili.danmuji.output;

import com.hiczp.bilibili.api.live.socket.entity.DataEntity;

import java.util.function.Function;

public class Variable<T extends DataEntity> {
    private final String name;
    private final String description;
    private final Function<T, Object> function;

    public Variable(String name, String description, Function<T, Object> function) {
        this.name = name;
        this.description = description;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Function<T, Object> getFunction() {
        return function;
    }
}
