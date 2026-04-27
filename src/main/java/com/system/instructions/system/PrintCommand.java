package com.system.instructions.system;

import com.system.instructions.Action;
import com.system.log.Log;
import com.system.registers.ManagerRegisters;

public class PrintCommand extends Action {

    public PrintCommand() {
        super("PRINT");
    }

    @Override
    public void execute() {
        Log.getInstance().add(ManagerRegisters.getInstance().toLogString());
    }

    @Override
    public String toLogString() {
        return this.getNameAction();
    }
}
