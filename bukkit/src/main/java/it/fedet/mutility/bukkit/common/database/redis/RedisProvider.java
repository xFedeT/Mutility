package it.fedet.mutility.common.database.redis;


import it.fedet.mutility.common.database.ConnectionProvider;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

public class RedisProvider implements ConnectionProvider<StatefulRedisConnection<String, String>, Exception> {

    private final RedisClient  redisClient;

    public RedisProvider(String uri) {
        this.redisClient = RedisClient.create(uri);
        redisClient.setOptions(
                ClientOptions.builder()
                        .autoReconnect(true)
                        .pingBeforeActivateConnection(true)
                        .build()
        );
    }

    /*
     * @return connection to redis client
     */
    public StatefulRedisConnection<String, String> getConnection() throws Exception {
        return redisClient.connect();
    }

    /*
     * @return pub/sub connection to redis client
     */
    public StatefulRedisPubSubConnection<String, String> getPubSubConnection() {
        return redisClient.connectPubSub();
    }

}