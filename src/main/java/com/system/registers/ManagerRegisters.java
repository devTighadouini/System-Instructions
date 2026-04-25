package com.system.registers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerRegisters {

    private List<Register> registersList;

    public ManagerRegisters() {
        this.registersList = generateListDefaultName();
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
        return "[Registros -> " + registersList.stream().map(n -> n.getLabelName() + " = " + n.getValue())
                                                        .collect(
                                                                Collectors.joining(", ", "", "]")
                                                        );
    }

}
