package com.system.instructions.system;

import com.system.core.SystemCore;
import com.system.instructions.Action;

public class RollBackCommand extends Action {

    public RollBackCommand() {
        super("ROLLBACK");
    }

    @Override
    public void execute() {
        SystemCore.getInstance().rollBackLast();
    }

    @Override
    public String toLogString() {
        return "· [ " + this.getNameAction() + " ]";
    }
}

