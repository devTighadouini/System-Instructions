package com.system.instructions.flag;

import com.system.instructions.Action;
import com.system.registers.FlagRegister;

public class IfDecorator extends Action {
    private Action wrapped;

    public IfDecorator(Action wrapped) {
        super(wrapped.getNameAction());
        this.wrapped = wrapped;

    }

    @Override
    public void execute() {
        if (FlagRegister.getInstance().isValue()) wrapped.execute();
    }

    @Override
    public String toLogString() {
        return "";
    }
}
