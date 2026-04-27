package com.system.instructions.register;

import com.system.instructions.Expiration;
import com.system.instructions.RegisterCommand;
import com.system.registers.Register;

public class DelCommand extends RegisterCommand {

    public DelCommand(Register source, Expiration expiration) {
        super(source, "DEL", expiration);
    }

    @Override
    public void execute() {
        getSource().setValue(null);
    }


    @Override
    public String toLogString() {
        return this.getNameAction() + " " + getSource().getLabelName();
    }
}
