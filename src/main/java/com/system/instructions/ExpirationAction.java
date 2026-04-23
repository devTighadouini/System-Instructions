package com.system.instructions;

public abstract class ExpirationAction extends Action {
    private Expiration expiration;

    public ExpirationAction(String nameAction, Expiration expiration) {
        super(nameAction);
        this.expiration = expiration;
    }

    public Expiration getExpiration() {
        return expiration;
    }

    public void setExpiration(Expiration expiration) {
        this.expiration = expiration;
    }
}
