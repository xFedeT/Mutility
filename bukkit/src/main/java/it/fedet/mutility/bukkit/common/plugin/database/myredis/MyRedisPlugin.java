package it.fedet.mutility.common.plugin.database.myredis;

import it.fedet.mutility.common.database.mysql.MysqlProvider;
import it.fedet.mutility.common.database.mysql.data.MysqlData;
import it.fedet.mutility.common.database.redis.RedisProvider;
import it.fedet.mutility.common.database.redis.data.RedisData;
import it.fedet.mutility.common.plugin.BasePlugin;

public abstract class MyRedisPlugin<R extends RedisProvider, C extends RedisData, M extends MysqlProvider, D extends MysqlData> extends BasePlugin {

    protected MyRedis<R, C, M, D> database;

    @Override
    public void onEnable() {
        database = new MyRedis<>(getRedisProvider(), getRedisData(), getMysqlProvider(), getMysqlData());
        super.onEnable();
    }

    protected abstract R getRedisProvider();

    protected abstract C getRedisData();

    protected abstract M getMysqlProvider();

    protected abstract D getMysqlData();

    public MyRedis<R, C, M, D> getDatabaseData() {
        return database;
    }

}