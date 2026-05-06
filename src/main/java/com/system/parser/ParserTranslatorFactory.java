package com.system.parser;

import com.system.instructions.Action;
import com.system.instructions.Expiration;
import com.system.instructions.arithmetic.*;
import com.system.instructions.flag.*;
import com.system.instructions.register.CopyCommand;
import com.system.instructions.register.DelCommand;
import com.system.instructions.register.MoveCommand;
import com.system.instructions.system.ExecCommand;
import com.system.instructions.system.PrintCommand;
import com.system.instructions.system.RollBackCommand;
import com.system.instructions.system.UndoCommand;
import com.system.registers.ManagerRegisters;
import com.system.registers.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParserTranslatorFactory {

    public ParserTranslatorFactory() {

    }

    private Action parseArithmetic(List<String> parts, String type) {
        Register source = ManagerRegisters.getInstance().getRegister(parts.get(1));
        Register target = ManagerRegisters.getInstance().getRegister(parts.get(2));
        Optional<Expiration> expiration = getExpiration(parts);
        Optional<Register> destination = (parts.size() >= 4 && !isNumber(parts.get(3)))
                ? Optional.of(ManagerRegisters.getInstance().getRegister(parts.get(3)))
                : Optional.empty();

        return switch (type) {
            case "ADD" -> new AddCommand(expiration.orElse(null), source, target, destination);
            case "SUB" -> new SubCommand(expiration.orElse(null), source, target, destination);
            case "MULT" -> new MultCommand(expiration.orElse(null), source, target, destination);
            case "DIV" -> new DivCommand(expiration.orElse(null), source, target, destination);
            case "MOD" -> new ModCommand(expiration.orElse(null), source, target, destination);
            default -> throw new IllegalArgumentException("Operación desconocida: " + type);
        };
    }

    private Action parseDel(List<String> parts) {
        Register source = ManagerRegisters.getInstance().getRegister(parts.get(1));

        return new DelCommand(source, getExpiration(parts).orElse(null));
    }

    private Action parseMove(List<String> parts) {
        Register source = ManagerRegisters.getInstance().getRegister(parts.get(1));
        Register target = ManagerRegisters.getInstance().getRegister(parts.get(2));

        return new MoveCommand(source, target, getExpiration(parts).orElse(null));
    }

    private Action parseCopy(List<String> parts) {
        Register source = ManagerRegisters.getInstance().getRegister(parts.get(1));

        List<Register> targets = parts.subList(2, parts.size()).stream()
                .filter(p -> !isNumber(p))
                .map(p -> ManagerRegisters.getInstance().getRegister(p))
                .toList();

        return new CopyCommand(source, targets, getExpiration(parts).orElse(null));
    }

    public Action parse(String line) {
        List<String> result = new ArrayList<>(List.of(line.split(" ")));
        boolean hasIf = result.remove("IF");

        Action action = switch (result.get(0)) {
            case "ADD", "SUB", "MULT", "DIV", "MOD" -> parseArithmetic(result, result.get(0));
            case "MOVE" -> parseMove(result);
            case "DEL" -> parseDel(result);
            case "COPY" -> parseCopy(result);
            case "PRINT" -> new PrintCommand();
            case "EXEC" -> new ExecCommand();
            case "UNDO" -> new UndoCommand();
            case "ROLLBACK" -> new RollBackCommand();
            case "FLAG" -> new FlagCommand(Boolean.parseBoolean(result.get(1)), getExpiration(result).orElse(null));
            case "NOT" -> new NotCommand("NOT", getExpiration(result).orElse(null));
            case "AND" -> new AndCommand(Boolean.parseBoolean(result.get(1)), getExpiration(result).orElse(null));
            case "OR" -> new OrCommand(Boolean.parseBoolean(result.get(1)), getExpiration(result).orElse(null));
            default -> throw new IllegalArgumentException("Valor desconocida: " + result.get(0));
        };

        return hasIf ? new IfDecorator(action) : action;
    }

    private boolean isNumber(String number) {
        try {
            Long.parseLong(number);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Optional<Expiration> getExpiration( List<String> parts ) {
        return isNumber(parts.getLast()) ? Optional.of(new Expiration( Long.parseLong( parts.getLast())) )
                                         : Optional.empty();
    }
}
