package com.system.registers;

public class FlagRegister {

    private static FlagRegister instance;
    private boolean value;

    private FlagRegister() {
        this.value = true;

    }

    public static FlagRegister getInstance() {
        if (instance == null) {
            instance = new FlagRegister();
        }

        return instance;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
