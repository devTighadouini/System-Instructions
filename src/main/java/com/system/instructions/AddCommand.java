package com.system.instructions;

import com.system.registers.Register;
import java.util.Optional;

public class AddCommand extends ArithmeticCommand {

    public AddCommand(Expiration expiration, String name, Register source, Register target, Optional<Register> optionalDestination) {
        super(expiration, name, " + ", source, target, optionalDestination);
    }

    @Override
    public void execute() {
        getOptionalDestination().orElse(getTarget()).setValue(
                Math.addExact(getSource().getValue(), getTarget().getValue())
        );
    }
}
