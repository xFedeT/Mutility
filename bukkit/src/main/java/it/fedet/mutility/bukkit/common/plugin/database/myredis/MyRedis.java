package it.fedet.mutility.common.plugin.database.myredis;

import it.fedet.mutility.common.database.mysql.MysqlProvider;
import it.fedet.mutility.common.database.mysql.data.MysqlData;
import it.fedet.mutility.common.database.redis.RedisProvider;
import it.fedet.mutility.common.database.redis.data.RedisData;

public class MyRedis<R extends RedisProvider, C extends RedisData, M extends MysqlProvider, D extends MysqlData> {

    protected final R redisProvider;
    protected final C redisData;
    protected final M mysqlProvider;
    protected final D mysqlData;

    public MyRedis(R redisProvider, C redisData, M mysqlProvider, D mysqlData) {
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

    public M getMysqlProvider() {
        return mysqlProvider;
    }

    public D getMysqlData() {
        return mysqlData;
    }

}