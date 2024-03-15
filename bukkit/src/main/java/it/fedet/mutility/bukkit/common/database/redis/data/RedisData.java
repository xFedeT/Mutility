package it.fedet.mutility.common.database.redis.data;

import it.fedet.mutility.bukkit.config.MutilityConfig;
import it.fedet.mutility.common.database.DBData;
import it.fedet.mutility.common.database.redis.RedisProvider;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class RedisData implements DBData<StatefulRedisConnection<String, String>, Exception> {

    protected static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(MutilityConfig.DATABASE_THREADS.get());

    private final RedisProvider provider;

    protected RedisData(RedisProvider provider) {
        this.provider = provider;
    }

    @Override
    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, EXECUTOR);
    }

    @Override
    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, EXECUTOR);
    }

    @Override
    public StatefulRedisConnection<String, String> getConnection() throws Exception {
        return provider.getConnection();
    }

    public StatefulRedisPubSubConnection<String, String> getPubSubConnection() {
        return provider.getPubSubConnection();
    }

}