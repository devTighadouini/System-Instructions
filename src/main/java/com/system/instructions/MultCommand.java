package com.system.instructions;

import com.system.registers.Register;

import java.util.Optional;

public class MultCommand extends ArithmeticCommand {

    public SubCommand(Expiration expiration, String name, Register source, Register target, Optional<Register> optionalDestination) {
        super(expiration, name, " * ", source, target, optionalDestination);
    }

    @Override
    public void execute() {
        getOptionalDestination().orElse(getTarget()).setValue(
                Math.subtractExact(getSource().getValue(), getTarget().getValue())
        );
    }
}
}
