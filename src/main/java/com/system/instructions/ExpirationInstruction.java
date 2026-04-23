package com.system.instructions;

public abstract class ExpirationInstruction extends Action {
    private Expiration expiration;

    public ExpirationInstruction(String nameAction, Expiration expiration) {
        super(nameAction);
    }
}
