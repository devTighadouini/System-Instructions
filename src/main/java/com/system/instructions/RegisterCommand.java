package com.system.instructions;

import com.system.registers.Register;

public abstract class RegisterCommand extends ExpirationAction {
    private Register source;

    public RegisterCommand(Register source, String nameAction, Expiration expiration) {
        super(nameAction, expiration);
        setSource(source);

    }

    public Register getSource() {
        return source;
    }

    public void setSource(Register source) {
        this.source = source;
    }


}
