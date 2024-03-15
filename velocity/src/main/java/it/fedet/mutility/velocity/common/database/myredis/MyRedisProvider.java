package it.fedet.mutility.velocity.common.database.myredis;

import it.fedet.mutility.velocity.common.database.mysql.auth.MysqlCredentials;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;

public class MyRedisProvider {

    private JedisPool redisClient;
    private final JedisPoolConfig config;
    private final String uri;
    private final MysqlCredentials credentials;

    public MyRedisProvider(String uri, MysqlCredentials credentials) {
        this.uri = uri;
        this.config = buildPoolConfig();

        try {
            this.redisClient = new JedisPool(config, new URI(uri), 0);
        } catch (URISyntaxException e) {
            System.out.println("errore nell'uri");
        }
        this.credentials = credentials;
    }

    private String getConnectionUrl() {
        return "jdbc:mysql://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabase();
    }

    /**
     * @return Connection to mysql database
     */
    public Connection getMysqlConnection() throws SQLException {
        return DriverManager.getConnection(
                getConnectionUrl(),
                credentials.getUsername(),
                credentials.getPassword()
        );
    }

    /**
     * @return connection to redis client
     */
    public Jedis getRedisConnection() {
        return redisClient.getResource();
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
        return redisClient;
    }

    public JedisPool resetPool() {
        redisClient = new JedisPool(config, uri);

        return redisClient;
    }

    /**
     * @return connection to redis client
     */
    public Jedis getConnection() throws Exception {
        return redisClient.getResource();
    }

}