package com.system.instructions.system;

import com.system.core.SystemCore;
import com.system.instructions.Action;

public class UndoCommand extends Action {

    public UndoCommand() {
        super("UNDO");
    }

    @Override
    public void execute() {
        SystemCore.getInstance().undoLast();
    }

    @Override
    public String toLogString() {
        return "";
    }
}
