package it.fedet.mutility.bukkit.database;

import it.fedet.mutility.bukkit.config.MutilityConfig;
import it.fedet.mutility.common.database.redis.RedisProvider;

public class MutilityRedisProvider extends RedisProvider {

    public MutilityRedisProvider() {
        super(MutilityConfig.REDIS_URI.get());
    }

}
