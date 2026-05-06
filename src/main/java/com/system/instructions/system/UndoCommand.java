package com.system.instructions.system;

import com.system.core.SystemCore;
import com.system.instructions.Action;
import com.system.instructions.Inmediate;

public class UndoCommand extends Action implements Inmediate {

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
