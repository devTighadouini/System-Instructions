package com.system.instructions.system;

import com.system.core.SystemCore;
import com.system.instructions.Action;
import com.system.instructions.Inmediate;

public class RollBackCommand extends Action implements Inmediate {

    public RollBackCommand() {
        super("ROLLBACK");
    }

    @Override
    public void execute() {
        SystemCore.getInstance().rollBackLast();
    }

    @Override
    public String toLogString() {
        return "[ " + this.getNameAction() + " ]";
    }
}

