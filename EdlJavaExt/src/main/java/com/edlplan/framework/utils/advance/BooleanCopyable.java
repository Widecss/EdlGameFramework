package com.edlplan.framework.utils.advance;

import com.edlplan.framework.utils.interfaces.Copyable;

public class BooleanCopyable implements Copyable {
    private boolean value;

    public BooleanCopyable(boolean value) {
        this.value = value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Copyable copy() {

        return new BooleanCopyable(value);
    }
}
