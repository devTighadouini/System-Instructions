package com.system.instructions;

import com.system.registers.Register;
import java.util.Optional;

public abstract class ArithmeticCommand extends ExpirationAction {

    private String operationSymbol;
    private Register source;
    private Register target;
    private Optional<Register> optionalDestination;

    public ArithmeticCommand(Expiration expiration, String name, String operationSymbol, Register source, Register target, Optional<Register> optionalDestination) {
        super(name, expiration);
        setOperationSymbol(operationSymbol);
        setSource(source);
        setTarget(target);
        setOptionalDestination(optionalDestination);
    }

    public String getOperationSymbol() {
        return operationSymbol;
    }

    public void setOperationSymbol(String operationSymbol) {
        this.operationSymbol = operationSymbol;
    }

    public Register getSource() {
        return source;
    }

    public void setSource(Register source) {
        this.source = source;
    }

    public Register getTarget() {
        return target;
    }

    public void setTarget(Register target) {
        this.target = target;
    }

    public Optional<Register> getOptionalDestination() {
        return optionalDestination;
    }

    public void setOptionalDestination(Optional<Register> optionalDestination) {
        this.optionalDestination = optionalDestination;
    }

    /**
     *
     * @return
     */
    @Override
    public String toLogString() {
        //ADD R1 + R2 -> R2
        return this.getNameAction() + " " + this.source.getLabelName() + " " + this.getOperationSymbol() + " "
                     + this.target.getLabelName() + " -> " + ((this.optionalDestination.isPresent()) ? this.getOptionalDestination().get().getLabelName()
                                                                                                     : this.target.getLabelName());
    }
}
