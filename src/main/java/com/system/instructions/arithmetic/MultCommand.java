package com.system.instructions.arithmetic;

import com.system.instructions.ArithmeticCommand;
import com.system.instructions.Expiration;
import com.system.registers.Register;
import java.util.Optional;

public class MultCommand extends ArithmeticCommand {

    public MultCommand(Expiration expiration, Register source, Register target, Optional<Register> optionalDestination) {
        super(expiration, "MULT", " * ", source, target, optionalDestination);
    }

    @Override
    public void execute() {
        validateOperands();
        getOptionalDestination().orElse(getTarget()).setValue(
                Math.multiplyExact(getSource().getValue(), getTarget().getValue())
        );
    }
}

