package it.fedet.mutility.common.server.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class CounterTask extends BukkitRunnable {

    private final int reach;
    private int status;
    private boolean running;
    private final Runnable onStart;
    private final Consumer<Integer> onRepeat;
    private final Runnable onComplete;

    public CounterTask(int reach, Runnable onStart, Consumer<Integer> onRepeat, Runnable onComplete) {
        this.reach = reach;
        this.status = 0;
        this.onStart = onStart;
        this.onRepeat = onRepeat;
        this.onComplete = onComplete;
    }

    @Override
    public void run() {
        running = true;

        if (status >= reach) {
            complete();
            return;
        }

        onRepeat.accept(status++);
    }

    /**
     * @param period ticks
     */
    public CounterTask start(Plugin plugin, boolean async, int period) {
        if (async) runTaskTimerAsynchronously(plugin, 0, period);
        else runTaskTimer(plugin, 0, period);

        start();
        return this;
    }

    public CounterTask start(Plugin plugin, boolean async) {
        return this.start(plugin, async, 20);
    }

    private synchronized void start() {
        if (!running && onStart != null)
            onStart.run();
    }

    private synchronized void complete() {
        if (running && onComplete != null)
            onComplete.run();

        this.cancel();
    }

    @Override
    public synchronized void cancel() {
        running = false;

        try {
            super.cancel();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public synchronized void updateCount(int count) {
        this.status = count;
    }

    public static CounterTaskBuilder builder() {
        return new CounterTaskBuilder();
    }

}