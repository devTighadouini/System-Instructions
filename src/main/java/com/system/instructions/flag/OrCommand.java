package com.system.instructions.flag;

import com.system.instructions.Expiration;
import com.system.instructions.ExpirationAction;
import com.system.registers.FlagRegister;

public class OrCommand extends ExpirationAction {

    private boolean value;

    public OrCommand(boolean value, Expiration expiration) {
        super("OR", expiration);
        this.value = value;
    }

    @Override
    public void execute() {
        FlagRegister.getInstance().setValue(FlagRegister.getInstance().isValue() || this.value);
    }

    @Override
    public String toLogString() {
        return "· OR " + this.value;
    }
}