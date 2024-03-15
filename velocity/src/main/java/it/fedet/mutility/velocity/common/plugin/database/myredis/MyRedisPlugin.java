package it.fedet.mutility.velocity.common.plugin.database.myredis;

import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.database.mysql.MysqlProvider;
import it.fedet.mutility.velocity.common.database.mysql.data.MysqlData;
import it.fedet.mutility.velocity.common.database.redis.RedisProvider;
import it.fedet.mutility.velocity.common.database.redis.data.RedisData;
import it.fedet.mutility.velocity.common.plugin.BasePlugin;
import org.slf4j.Logger;

import java.nio.file.Path;

public abstract class MyRedisPlugin<R extends RedisProvider, C extends RedisData, M extends MysqlProvider, D extends MysqlData> extends BasePlugin {

    protected MyRedis<R, C, M, D> database;

    protected MyRedisPlugin(ProxyServer server, Logger logger, Path dataDirectory) {
        super(server, logger, dataDirectory);
    }

    public void load() {
        database = new MyRedis<>(getRedisProvider(), getRedisData(), getMysqlProvider(), getMysqlData());
    }

    protected abstract R getRedisProvider();

    protected abstract C getRedisData();

    protected abstract M getMysqlProvider();

    protected abstract D getMysqlData();

    public MyRedis<R, C, M, D> getDatabase() {
        return database;
    }

}