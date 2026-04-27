package com.system.instructions.register;


import com.system.instructions.Expiration;
import com.system.instructions.RegisterCommand;
import com.system.registers.Register;

public class MoveCommand extends RegisterCommand {
    private Register target;

    public MoveCommand(Register source, Register target, Expiration expiration) {
        super(source, "MOVE", expiration);
        setTarget(target);
    }

    public Register getTarget() {
        return target;
    }

    public void setTarget(Register target) {
        this.target = target;
    }

    @Override
    public void execute() {
        getTarget().setValue(getSource().getValue());
        getSource().setValue(null);
    }

    @Override
    public String toLogString() {
        return this.getNameAction() + " " + getSource().getLabelName() + " (" + getSource().getValue() + ") " + "->" + getTarget().getLabelName()  + " (" + getTarget().getValue() + ") ";
    }
}
