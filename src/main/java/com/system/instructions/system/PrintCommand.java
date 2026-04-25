package com.system.instructions.system;

import com.system.instructions.Action;
import com.system.registers.ManagerRegisters;

public class PrintCommand extends Action {
    private ManagerRegisters managerRegisters;

    public PrintCommand() {
        super("PRINT");
        this.managerRegisters = new ManagerRegisters();
    }

    @Override
    public void execute() {
        this.managerRegisters.toLogString();
    }

    @Override
    public String toLogString() {
        return "";
    }
}
