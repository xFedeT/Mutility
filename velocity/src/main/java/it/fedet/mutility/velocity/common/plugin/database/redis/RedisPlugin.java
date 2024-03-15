package it.fedet.mutility.velocity.common.plugin.database.redis;

import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.database.redis.RedisProvider;
import it.fedet.mutility.velocity.common.database.redis.data.RedisData;
import it.fedet.mutility.velocity.common.plugin.database.DBPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;

public abstract class RedisPlugin<P extends RedisProvider, D extends RedisData> extends DBPlugin<P, D> {

    protected RedisPlugin(ProxyServer server, Logger logger, Path dataDirectory) {
        super(server, logger, dataDirectory);
    }

}