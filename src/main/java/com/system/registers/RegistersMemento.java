package com.system.registers;

import java.util.ArrayList;
import java.util.List;

public record RegistersMemento(List<Integer> values) {
    public RegistersMemento(List<Integer> values) {
        this.values = new ArrayList<>(values);
    }
}
