package it.fedet.mutility.common.server.config.value;

import it.fedet.mutility.common.database.redis.data.RedisData;
import it.fedet.mutility.common.server.chat.ConsoleLogger;

import java.util.function.Supplier;

public abstract class RedisConfigValue<T> implements Supplier<T> {

    protected final String key;
    protected T value;

    protected RedisConfigValue(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public void load(RedisData redisData, String mapName, String path) {
        T data = null;
        try {
            data = getRedisValue(redisData, mapName, path + ":" + key);
        } catch (Exception ex) {
            ConsoleLogger.error("Errore durante il caricamento delle config redis", ex);
        }
        if (data != null) value = data;
    }

    public abstract T getRedisValue(RedisData data, String mapName, String path);

    public T get() {
        return value;
    }

}
