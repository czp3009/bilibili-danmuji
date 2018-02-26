package com.hiczp.bilibili.danmuji.output;

import com.hiczp.bilibili.api.live.socket.entity.DataEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class VariableProvider<T extends DataEntity> extends HashMap<String, Variable<T>> {
    public VariableProvider(@Nonnull List<Variable<T>> variables) {
        super();
        variables.forEach(variable ->
                super.put(variable.getName(), variable)
        );
    }

    @Nullable
    public Object get(String name, T entity) {
        Variable<T> variable = super.get(name);
        return variable != null ? variable.getFunction().apply(entity) : null;
    }
}
