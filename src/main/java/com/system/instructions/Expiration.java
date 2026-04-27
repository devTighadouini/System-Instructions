package com.system.instructions;

public class Expiration {
    private long timeCycle;

    public Expiration(long timeCycle) {
        setTimeCycle(timeCycle);
    }

    public void setTimeCycle(long timeCycle) {
        if (timeCycle < 0) throw new IllegalArgumentException("Expiration: introduce un ciclo de ejecución correcto.");
        this.timeCycle = timeCycle;
    }

    public long getTimeCycle() {
        return timeCycle;
    }
}
