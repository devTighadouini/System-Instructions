package com.system.core;

import com.system.instructions.Action;
import com.system.instructions.ExpirationAction;
import com.system.instructions.system.RollBackCommand;
import com.system.log.Log;
import com.system.registers.ManagerRegisters;
import com.system.registers.RegistersMemento;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SystemCore {

    private static SystemCore instance;
    private List<Action> pendingInstruction;
    private Deque<RegistersMemento> history;
    private Deque<Action> executedHistory;

    private long currentCycle = 0;

    private SystemCore() {
        this.pendingInstruction = new ArrayList<>();
        this.history = new ArrayDeque<>();
        this.executedHistory = new ArrayDeque<>();
    }

    public static SystemCore getInstance() {
        if (instance == null) {
            instance = new SystemCore();
        }

        return instance;
    }

    public void addInstruction(Action action) {
        this.pendingInstruction.add(action);

    }

    public void registerInformation(List<Action> executed, Action aux) {
        history.push(ManagerRegisters.getInstance().saveMemento());
        executedHistory.push(aux);

        aux.execute();
        Log.getInstance().add(aux.toLogString());

        executed.add(aux);
    }

    public void executeAll() {
        currentCycle++;

        // Inicio del ciclo
        Log.getInstance().add("[ Inicio - Ciclo " + currentCycle + " ]");
        List<Action> executed = new ArrayList<>();

        for (Action action : pendingInstruction) {
            if (action instanceof ExpirationAction exp) {
                if (currentCycle > exp.getExpiration().getTimeCycle()) {
                    registerInformation(executed, action);
                }
            } else {
                registerInformation(executed, action);
            }
        }

        pendingInstruction.removeAll(executed);

        // Estado final
        Log.getInstance().add(ManagerRegisters.getInstance().toLogString());
    }

    public void undoLast() {
        pendingInstruction.removeLast();
    }

    public void rollBackLast() {
        Action lastAction = executedHistory.pop();
        ManagerRegisters.getInstance()
                        .restoreMemento(history.pop());

        Log.getInstance().add("[ROLLBACK] " + lastAction.toLogString());
    }
}
