package com.system.instructions.system;

import com.system.instructions.Action;

public class ExecCommand extends Action {

    public ExecCommand() {
        super("EXEC");
    }

    @Override
    public void execute() {

    }

    @Override
    public String toLogString() {
        return "";
    }
}
