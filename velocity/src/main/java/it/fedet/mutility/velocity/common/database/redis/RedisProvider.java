package it.fedet.mutility.velocity.common.database.redis;

import it.fedet.mutility.velocity.common.database.ConnectionProvider;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class RedisProvider implements ConnectionProvider<Jedis, Exception> {

    //private final RedisClient redisClient;
    private JedisPool jedisPool;
    private final JedisPoolConfig config;
    private final String uri;


    public RedisProvider(String uri) {
        this.uri = uri;
        this.config = buildPoolConfig();

        try {
            this.jedisPool = new JedisPool(config, new URI(uri), 0);
        } catch (URISyntaxException e) {
            System.out.println("errore nell'uri");
        }
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public JedisPool resetPool() {
        jedisPool = new JedisPool(config, uri);

        return jedisPool;
    }

    /**
     * @return connection to redis client
     */
    @Override
    public Jedis getConnection() throws Exception {
        return jedisPool.getResource();
    }

}