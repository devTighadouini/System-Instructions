package com.system.instructions.arithmetic;

import com.system.instructions.ArithmeticCommand;
import com.system.instructions.Expiration;
import com.system.registers.Register;
import java.util.Optional;

public class DivCommand extends ArithmeticCommand {

    public DivCommand(Expiration expiration, Register source, Register target, Optional<Register> optionalDestination) {
        super(expiration, "DIV", " / ", source, target, optionalDestination);
    }

    @Override
    public void execute() {
        if ( getTarget().getValue() == 0 ) throw new IllegalArgumentException("División - Arithmetic: introduzca un divisor mayor a 0.");
        getOptionalDestination().orElse(getTarget()).setValue(getSource().getValue() / getTarget().getValue());
    }
}
