package com.system.instructions.flag;

import com.system.instructions.Expiration;
import com.system.instructions.ExpirationAction;
import com.system.registers.FlagRegister;

public class FlagCommand extends ExpirationAction {

    private boolean value;

    public FlagCommand(boolean value, Expiration expiration) {
        super("FLAG", expiration);
        this.value = value;
    }

    @Override
    public void execute() {
        FlagRegister.getInstance().setValue(this.value);
    }

    @Override
    public String toLogString() {
        return "";
    }
}
