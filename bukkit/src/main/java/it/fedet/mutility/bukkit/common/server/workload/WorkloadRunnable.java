package it.fedet.mutility.common.server.workload;

import it.fedet.mutility.common.plugin.BasePlugin;

import java.util.ArrayDeque;
import java.util.Deque;

public class WorkloadRunnable {

    private static final double MAX_MILLIS_PER_TICK = 2.5;
    private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIS_PER_TICK * 1E6);

    private final BasePlugin plugin;
    private final Deque<Runnable> workloadDeque = new ArrayDeque<>();

    public WorkloadRunnable(BasePlugin plugin) {
        this.plugin = plugin;
    }

    public void addWorkload(Runnable workload) {
        this.workloadDeque.add(workload);
    }

    public void run() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;
            Runnable nextLoad;

            while (System.nanoTime() <= stopTime && (nextLoad = this.workloadDeque.poll()) != null)
                nextLoad.run();
        }, 1, 1);
    }

}