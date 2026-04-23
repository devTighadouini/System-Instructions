package com.system.core;

import com.system.instructions.Action;
import com.system.instructions.ExpirationAction;
import com.system.registers.ManagerRegisters;

import java.util.ArrayList;
import java.util.List;

public class SystemCore {

    private List<Action> pendingInstruction;
    private ManagerRegisters managerRegisters;
    private long currentCycle = 0;

    public SystemCore() {
        this.pendingInstruction = new ArrayList<>();
        this.managerRegisters = new ManagerRegisters();
    }

    public void addInstruction(Action action) {
        this.pendingInstruction.add(action);

    }

    public void executeAll() {
        currentCycle++;

        List<Action> executed = new ArrayList<>();

        for (Action action : pendingInstruction) {
            if (action instanceof ExpirationAction exp) {
                if (currentCycle > exp.getExpiration().getTimeCycle()) {
                    action.execute();
                    executed.add(action);
                }
            } else {
                action.execute();
                executed.add(action);
            }
        }

        pendingInstruction.removeAll(executed);
    }
}
