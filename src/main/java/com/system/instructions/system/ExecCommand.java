package com.system.instructions.system;

import com.system.core.SystemCore;
import com.system.instructions.Action;
import com.system.instructions.Inmediate;

public class ExecCommand extends Action implements Inmediate {

    public ExecCommand() {
        super("EXEC");
    }

    @Override
    public void execute() {
        SystemCore.getInstance().executeAll();
    }

    @Override
    public String toLogString() {
        return "";
    }
}
