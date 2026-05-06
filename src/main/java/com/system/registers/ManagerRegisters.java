package com.system.registers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerRegisters {

    private static ManagerRegisters instance;
    private List<Register> registersList;

    private ManagerRegisters() {
        this.registersList = generateListDefaultName();
    }

    public static ManagerRegisters getInstance() {
        if (instance == null) {
            instance = new ManagerRegisters();
        }

        return instance;
    }

    public List<Register> getRegistersList() {
        return registersList;
    }

    public Register getRegister(String labelName) {
        return getRegistersList().stream()
                                 .filter(n -> n.getLabelName().equals(labelName))
                                 .findFirst()
                                 .orElseThrow(() -> new IllegalArgumentException("Manager Registers: registro con nombre (" + labelName + "), no encontrado."));
    }

    private List<Register> generateListDefaultName() {
        List<Register> result = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            result.add(new Register("R" + i, null));
        }

        return result;
    }

    public String toLogString() {
        return "[Registros -> " + registersList.stream().map(n -> n.getLabelName() + " = " + String.valueOf(n.getValue()));
    }

    public RegistersMemento saveMemento() {
        return new RegistersMemento(
                    getRegistersList().stream()
                                      .map(Register::getValue)
                                      .toList()
                    );
    }

    public void restoreMemento(RegistersMemento restore) {
        List<Integer> values = restore.values();
        for (int i = 0; i < registersList.size(); i++) {
            registersList.get(i).setValue(values.get(i));
        }
    }
}
