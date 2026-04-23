package com.system.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Log {

    private static Log instance;
    private List<String> listLog;
    private BufferedWriter writer;

    private Log() {
        this.listLog = new ArrayList<>();
        try {
            this.writer = new BufferedWriter(new FileWriter("system.log", true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }

        return instance;
    }

    public void add(String entry) {
        // Añadimos a la lista
        this.listLog.add(entry);

        // Añadimos al archivo LOG todos los registros
        try {
            writer.write(entry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getListLog() {
        return listLog;
    }

}
