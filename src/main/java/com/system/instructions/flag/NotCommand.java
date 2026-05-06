package com.system.instructions.flag;

import com.system.instructions.Expiration;
import com.system.instructions.ExpirationAction;
import com.system.registers.FlagRegister;

public class NotCommand extends ExpirationAction {
    public NotCommand(String nameAction, Expiration expiration) {
        super("NOT", expiration);
    }

    @Override
    public void execute() {
        FlagRegister.getInstance().setValue(!FlagRegister.getInstance().isValue());
    }

    @Override
    public String toLogString() {
        return "";
    }
}
