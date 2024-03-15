package it.fedet.mutility.common.server.task;

import java.util.function.Consumer;

public class CounterTaskBuilder {

    private int reach;
    private Runnable onStart;
    private Consumer<Integer> onRepeat;
    private Runnable onComplete;

    public CounterTaskBuilder reach(int reach) {
        this.reach = reach;
        return this;
    }

    public CounterTaskBuilder onStart(Runnable onStart) {
        this.onStart = onStart;
        return this;
    }

    public CounterTaskBuilder onRepeat(Consumer<Integer> onRepeat) {
        this.onRepeat = onRepeat;
        return this;
    }

    public CounterTaskBuilder onComplete(Runnable onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    public CounterTask build() {
        return new CounterTask(reach, onStart, onRepeat, onComplete);
    }

}