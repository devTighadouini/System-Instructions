package com.system.instructions;

public abstract class Action {

    private String nameAction;

    public Action(String nameAction) {
        setNameAction(nameAction);

    }

    public void setNameAction(String nameAction) {
        if (nameAction == null || nameAction.isBlank()) throw new IllegalArgumentException("Action: introduce un nombre correctamente de la acción.");
        this.nameAction = nameAction.toUpperCase();
    }

    public String getNameAction() {
        return nameAction;
    }

    // Patrón command
    public abstract void execute();
}
