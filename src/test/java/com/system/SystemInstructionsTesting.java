package com.system;

import com.system.core.SystemCore;
import com.system.instructions.Expiration;
import com.system.instructions.flag.*;
import com.system.instructions.register.*;
import com.system.instructions.arithmetic.*;
import com.system.instructions.system.*;
import com.system.log.Log;
import com.system.parser.ParserTranslatorFactory;
import com.system.registers.FlagRegister;
import com.system.registers.ManagerRegisters;
import com.system.registers.Register;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Sistema de instrucciones ")
public class SystemInstructionsTesting {

    @BeforeEach
    void reiniciarSistema() throws Exception {
        // Resetear Singletons entre tests
        var fieldCore = SystemCore.class.getDeclaredField("instance");
        fieldCore.setAccessible(true);
        fieldCore.set(null, null);

        var fieldManager = ManagerRegisters.class.getDeclaredField("instance");
        fieldManager.setAccessible(true);
        fieldManager.set(null, null);

        var fieldLog = Log.class.getDeclaredField("instance");
        fieldLog.setAccessible(true);
        fieldLog.set(null, null);

        var fieldFlag = FlagRegister.class.getDeclaredField("instance");
        fieldFlag.setAccessible(true);
        fieldFlag.set(null, null);
    }

    // ==================== REGISTROS ====================

    @Test
    @DisplayName("Los 8 registros se inicializan correctamente con etiquetas R0-R7")
    void registrosInicializadosCorrectamente() {
        ManagerRegisters manager = ManagerRegisters.getInstance();
        for (int i = 0; i < 8; i++) {
            Register r = manager.getRegister("R" + i);
            assertNotNull(r);
            assertEquals("R" + i, r.getLabelName());
            assertNull(r.getValue());
        }
    }

    @Test
    @DisplayName("ManagerRegisters es Singleton")
    void managerRegistersEsSingleton() {
        ManagerRegisters a = ManagerRegisters.getInstance();
        ManagerRegisters b = ManagerRegisters.getInstance();
        assertSame(a, b);
    }

    @Test
    @DisplayName("Obtener registro inexistente lanza excepción")
    void registroInexistenteLanzaExcepcion() {
        assertThrows(Exception.class, () ->
                ManagerRegisters.getInstance().getRegister("R99")
        );
    }

    // ==================== LOG ====================

    @Test
    @DisplayName("Log es Singleton")
    void logEsSingleton() {
        Log a = Log.getInstance();
        Log b = Log.getInstance();
        assertSame(a, b);
    }

    @Test
    @DisplayName("Log registra entradas correctamente")
    void logRegistraEntradas() {
        Log log = Log.getInstance();
        log.add("entrada de prueba");
        assertTrue(log.getListLog().contains("entrada de prueba"));
    }

    // ==================== OPERACIONES ARITMÉTICAS ====================

    @Test
    @DisplayName("ADD suma dos registros y guarda en target")
    void addSumaEnTarget() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(10);
        ManagerRegisters.getInstance().getRegister("R1").setValue(5);

        new AddCommand(null,
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.empty()
        ).execute();

        assertEquals(15, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    @Test
    @DisplayName("ADD con destino opcional guarda en D en vez de T")
    void addConDestinoOpcional() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(10);
        ManagerRegisters.getInstance().getRegister("R1").setValue(5);
        ManagerRegisters.getInstance().getRegister("R2").setValue(0);

        new AddCommand(null,
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.of(ManagerRegisters.getInstance().getRegister("R2"))
        ).execute();

        assertEquals(15, ManagerRegisters.getInstance().getRegister("R2").getValue());
        assertEquals(5, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    @Test
    @DisplayName("DIV lanza excepción al dividir por cero")
    void divisionPorCeroLanzaExcepcion() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(10);
        ManagerRegisters.getInstance().getRegister("R1").setValue(0);

        assertThrows(IllegalArgumentException.class, () ->
                new DivCommand(null,
                        ManagerRegisters.getInstance().getRegister("R0"),
                        ManagerRegisters.getInstance().getRegister("R1"),
                        Optional.empty()
                ).execute()
        );
    }

    @Test
    @DisplayName("MOD lanza excepción con divisor cero")
    void modPorCeroLanzaExcepcion() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(10);
        ManagerRegisters.getInstance().getRegister("R1").setValue(0);

        assertThrows(IllegalArgumentException.class, () ->
                new ModCommand(null,
                        ManagerRegisters.getInstance().getRegister("R0"),
                        ManagerRegisters.getInstance().getRegister("R1"),
                        Optional.empty()
                ).execute()
        );
    }

    @Test
    @DisplayName("SUB resta correctamente")
    void subRestaCorrectamente() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(10);
        ManagerRegisters.getInstance().getRegister("R1").setValue(3);

        new SubCommand(null,
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.empty()
        ).execute();

        assertEquals(7, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    // ==================== OPERACIONES SOBRE REGISTROS ====================

    @Test
    @DisplayName("MOVE transfiere valor y deja source en null")
    void moveDejaSoureEnNull() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(42);

        new MoveCommand(
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                null
        ).execute();

        assertEquals(42, ManagerRegisters.getInstance().getRegister("R1").getValue());
        assertNull(ManagerRegisters.getInstance().getRegister("R0").getValue());
    }

    @Test
    @DisplayName("DEL elimina el contenido del registro")
    void delEliminaRegistro() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(99);
        new DelCommand(ManagerRegisters.getInstance().getRegister("R0"), null).execute();
        assertNull(ManagerRegisters.getInstance().getRegister("R0").getValue());
    }

    @Test
    @DisplayName("COPY copia valor a múltiples registros")
    void copyCopiaAMultiplesRegistros() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(7);

        new CopyCommand(
                ManagerRegisters.getInstance().getRegister("R0"),
                List.of(
                        ManagerRegisters.getInstance().getRegister("R1"),
                        ManagerRegisters.getInstance().getRegister("R2")
                ),
                null
        ).execute();

        assertEquals(7, ManagerRegisters.getInstance().getRegister("R1").getValue());
        assertEquals(7, ManagerRegisters.getInstance().getRegister("R2").getValue());
    }

    // ==================== SYSTEM CORE ====================

    @Test
    @DisplayName("SystemCore es Singleton")
    void systemCoreEsSingleton() {
        SystemCore a = SystemCore.getInstance();
        SystemCore b = SystemCore.getInstance();
        assertSame(a, b);
    }

    @Test
    @DisplayName("EXEC ejecuta instrucciones pendientes sin expiración")
    void execEjecutaPendientes() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(5);
        ManagerRegisters.getInstance().getRegister("R1").setValue(3);

        SystemCore core = SystemCore.getInstance();
        core.addInstruction(new AddCommand(null,
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.empty()
        ));

        core.executeAll();

        assertEquals(8, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    @Test
    @DisplayName("EXEC no ejecuta instrucciones cuya expiración no ha llegado")
    void execNoEjecutaExpiracionFutura() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(5);
        ManagerRegisters.getInstance().getRegister("R1").setValue(3);

        SystemCore core = SystemCore.getInstance();
        core.addInstruction(new AddCommand(
                new Expiration(999),
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.empty()
        ));

        core.executeAll();

        assertEquals(3, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    @Test
    @DisplayName("UNDO elimina la última instrucción pendiente")
    void undoEliminaUltimaPendiente() {
        SystemCore core = SystemCore.getInstance();
        core.addInstruction(new DelCommand(
                ManagerRegisters.getInstance().getRegister("R0"), null
        ));
        core.undoLast();
        core.executeAll();
        assertNull(ManagerRegisters.getInstance().getRegister("R0").getValue());
    }

    @Test
    @DisplayName("ROLLBACK restaura el estado anterior de los registros")
    void rollbackRestauraEstadoAnterior() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(10);

        SystemCore core = SystemCore.getInstance();
        core.addInstruction(new DelCommand(
                ManagerRegisters.getInstance().getRegister("R0"), null
        ));
        core.executeAll();

        assertNull(ManagerRegisters.getInstance().getRegister("R0").getValue());

        core.rollBackLast();

        assertEquals(10, ManagerRegisters.getInstance().getRegister("R0").getValue());
    }

    // ==================== FLAG ====================

    @Test
    @DisplayName("FlagRegister empieza en true")
    void flagEmpiezaEnTrue() {
        assertTrue(FlagRegister.getInstance().isValue());
    }

    @Test
    @DisplayName("NOT niega el valor del FLAG")
    void notNiegaFlag() {
        new NotCommand("NOT", null).execute();
        assertFalse(FlagRegister.getInstance().isValue());
    }

    @Test
    @DisplayName("AND con false deja FLAG en false")
    void andConFalseDejFlagEnFalse() {
        new AndCommand(false, null).execute();
        assertFalse(FlagRegister.getInstance().isValue());
    }

    @Test
    @DisplayName("OR con true deja FLAG en true")
    void orConTrueDejFlagEnTrue() {
        FlagRegister.getInstance().setValue(false);
        new OrCommand(true, null).execute();
        assertTrue(FlagRegister.getInstance().isValue());
    }

    // ==================== IF DECORATOR ====================

    @Test
    @DisplayName("IfDecorator ejecuta instrucción si FLAG es true")
    void ifDecoratorEjecutaSiFlagTrue() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(99);
        FlagRegister.getInstance().setValue(true);

        new IfDecorator(
                new DelCommand(ManagerRegisters.getInstance().getRegister("R0"), null)
        ).execute();

        assertNull(ManagerRegisters.getInstance().getRegister("R0").getValue());
    }

    @Test
    @DisplayName("IfDecorator no ejecuta instrucción si FLAG es false")
    void ifDecoratorNoEjecutaSiFlagFalse() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(99);
        FlagRegister.getInstance().setValue(false);

        new IfDecorator(
                new DelCommand(ManagerRegisters.getInstance().getRegister("R0"), null)
        ).execute();

        assertEquals(99, ManagerRegisters.getInstance().getRegister("R0").getValue());
    }

    // ==================== PARSER ====================

    @Test
    @DisplayName("Parser convierte ADD correctamente")
    void parserConvierteAdd() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(3);
        ManagerRegisters.getInstance().getRegister("R1").setValue(7);

        ParserTranslatorFactory parser = new ParserTranslatorFactory();
        parser.parse("ADD R0 R1").execute();

        assertEquals(10, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    @Test
    @DisplayName("Parser detecta modificador IF correctamente")
    void parserDetectaIF() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(99);
        FlagRegister.getInstance().setValue(false);

        ParserTranslatorFactory parser = new ParserTranslatorFactory();
        parser.parse("DEL R0 IF").execute();

        assertEquals(99, ManagerRegisters.getInstance().getRegister("R0").getValue());
    }

    @Test
    @DisplayName("Parser lanza excepción con instrucción desconocida")
    void parserLanzaExcepcionInstruccionDesconocida() {
        ParserTranslatorFactory parser = new ParserTranslatorFactory();
        assertThrows(IllegalArgumentException.class, () ->
                parser.parse("INVALIDA R0")
        );
    }

    // ==================== INSTRUCCIONES DEL SISTEMA ====================

    @Test
    @DisplayName("EXEC inmediato se ejecuta al añadirse al sistema")
    void execEsInmediato() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(5);
        ManagerRegisters.getInstance().getRegister("R1").setValue(3);

        SystemCore core = SystemCore.getInstance();
        core.addInstruction(new AddCommand(null,
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.empty()
        ));

        core.addInstruction(new ExecCommand());

        assertEquals(8, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    @Test
    @DisplayName("UNDO inmediato elimina la última instrucción pendiente antes de EXEC")
    void undoEsInmediato() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(5);
        ManagerRegisters.getInstance().getRegister("R1").setValue(3);

        SystemCore core = SystemCore.getInstance();
        core.addInstruction(new AddCommand(null,
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.empty()
        ));

        // UNDO elimina el ADD antes de ejecutar
        core.addInstruction(new UndoCommand());
        core.addInstruction(new ExecCommand());

        // R1 sigue siendo 3 porque el ADD fue eliminado
        assertEquals(3, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

    @Test
    @DisplayName("ROLLBACK inmediato devuelve el estado tras ejecutar")
    void rollbackEsInmediato() {
        ManagerRegisters.getInstance().getRegister("R0").setValue(10);
        ManagerRegisters.getInstance().getRegister("R1").setValue(5);

        SystemCore core = SystemCore.getInstance();
        core.addInstruction(new AddCommand(null,
                ManagerRegisters.getInstance().getRegister("R0"),
                ManagerRegisters.getInstance().getRegister("R1"),
                Optional.empty()
        ));
        core.addInstruction(new ExecCommand());

        assertEquals(15, ManagerRegisters.getInstance().getRegister("R1").getValue());

        // ROLLBACK devuelve el estado anterior
        core.addInstruction(new RollBackCommand());

        assertEquals(5, ManagerRegisters.getInstance().getRegister("R1").getValue());
    }

}