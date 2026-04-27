package com.system.instructions.register;

import com.system.instructions.Expiration;
import com.system.instructions.RegisterCommand;
import com.system.registers.Register;

import java.util.List;
import java.util.stream.Collectors;

public class CopyCommand extends RegisterCommand {
    private List<Register> target;

    public CopyCommand(Register source, List<Register> target, Expiration expiration) {
        super(source, "COPY", expiration);
        setTarget(target);
    }

    public List<Register> getTarget() {
        return target;
    }

    public void setTarget(List<Register> target) {
        this.target = target;
    }

    @Override
    public void execute() {
        getTarget().forEach(n -> n.setValue(getSource().getValue()));
    }

    @Override
    public String toLogString() {
        return this.getNameAction() + " ->" + this.target.stream().map(Register::getLabelName)
                                                                         .collect(
                                                                                 Collectors.joining(", ", "[ ", " ]")
                                                                         );
    }
}
