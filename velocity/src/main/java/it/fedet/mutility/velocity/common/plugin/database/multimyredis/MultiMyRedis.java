package it.fedet.mutility.velocity.common.plugin.database.multimyredis;

import it.fedet.mutility.velocity.common.database.mysql.MysqlProvider;
import it.fedet.mutility.velocity.common.database.mysql.data.MysqlData;
import it.fedet.mutility.velocity.common.database.redis.RedisProvider;
import it.fedet.mutility.velocity.common.database.redis.data.RedisData;

import java.util.Map;

public class MultiMyRedis<R extends RedisProvider, C extends RedisData, M extends MysqlProvider, D extends MysqlData> {

    protected final R redisProvider;
    protected final C redisData;
    protected final Map<Class<? extends MysqlProvider>, MysqlProvider> mysqlProvider;
    protected final Map<Class<? extends MysqlData>, MysqlData> mysqlData;

    public MultiMyRedis(R redisProvider, C redisData, Map<Class<? extends MysqlProvider>, MysqlProvider> mysqlProvider, Map<Class<? extends MysqlData>, MysqlData> mysqlData) {
        this.redisProvider = redisProvider;
        this.redisData = redisData;
        this.mysqlProvider = mysqlProvider;
        this.mysqlData = mysqlData;
    }

    public R getRedisProvider() {
        return redisProvider;
    }

    public C getRedisData() {
        return redisData;
    }

    public Map<Class<? extends MysqlProvider>, MysqlProvider> getMysqlProvider() {
        return mysqlProvider;
    }

    public Map<Class<? extends MysqlData>, MysqlData> getMysqlData() {
        return mysqlData;
    }

}