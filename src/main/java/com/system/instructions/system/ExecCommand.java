package com.system.instructions.system;

import com.system.core.SystemCore;
import com.system.instructions.Action;

public class ExecCommand extends Action {

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
