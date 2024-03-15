package it.fedet.mutility.common.plugin.database.redis;

import it.fedet.mutility.common.database.redis.RedisProvider;
import it.fedet.mutility.common.database.redis.data.RedisData;
import it.fedet.mutility.common.plugin.database.DBPlugin;

public abstract class RedisPlugin<P extends RedisProvider, D extends RedisData> extends DBPlugin<P, D> {
}