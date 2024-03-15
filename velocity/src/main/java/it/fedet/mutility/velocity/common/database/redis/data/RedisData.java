package it.fedet.mutility.velocity.common.database.redis.data;

import it.fedet.mutility.velocity.common.database.DBData;
import it.fedet.mutility.velocity.common.database.redis.RedisProvider;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class RedisData implements DBData<Jedis, Exception> {

    //TODO: FIXA
    protected static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

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

    public JedisPool getJedisPool() {
        return provider.getJedisPool();
    }

    public JedisPool resetPool() {
        return provider.resetPool();
    }

    @Override
    public Jedis getConnection() throws Exception {
        return provider.getConnection();
    }

}