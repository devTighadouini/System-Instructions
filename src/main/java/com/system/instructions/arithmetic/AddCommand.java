package com.system.instructions.arithmetic;

import com.system.instructions.ArithmeticCommand;
import com.system.instructions.Expiration;
import com.system.registers.Register;
import java.util.Optional;

public class AddCommand extends ArithmeticCommand {

    public AddCommand(Expiration expiration, Register source, Register target, Optional<Register> optionalDestination) {
        super(expiration, "ADD", " + ", source, target, optionalDestination);
    }

    @Override
    public void execute() {
        getOptionalDestination().orElse(getTarget()).setValue(
                Math.addExact(getSource().getValue(), getTarget().getValue())
        );
    }
}
