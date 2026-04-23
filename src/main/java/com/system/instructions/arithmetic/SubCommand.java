package com.system.instructions.arithmetic;

import com.system.instructions.ArithmeticCommand;
import com.system.instructions.Expiration;
import com.system.registers.Register;
import java.util.Optional;

public class SubCommand extends ArithmeticCommand {

    public SubCommand(Expiration expiration, Register source, Register target, Optional<Register> optionalDestination) {
        super(expiration, "SUB", " - ", source, target, optionalDestination);
    }

    @Override
    public void execute() {
        getOptionalDestination().orElse(getTarget()).setValue(
                Math.subtractExact(getSource().getValue(), getTarget().getValue())
        );
    }
}