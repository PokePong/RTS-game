package engine.core;

public class Timer {

    private double lastLoopTime;

    public void mark() {
        this.lastLoopTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1_000_000_000.0;
    }

    public double getDelta() {
        double time = getTime();
        double delta = time - lastLoopTime;
        lastLoopTime = time;
        return delta;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
