package it.fedet.mutility.common.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.fedet.mutility.bukkit.config.MutilityConfig;
import it.fedet.mutility.common.database.ConnectionProvider;
import it.fedet.mutility.common.database.mysql.auth.MysqlCredentials;
import it.fedet.mutility.common.server.chat.ConsoleLogger;

import java.sql.Connection;
import java.sql.SQLException;

public class MysqlProvider implements ConnectionProvider<Connection, SQLException> {

    private final HikariConfig config;
    private HikariDataSource hikari;
    private final MysqlCredentials credentials;

    public MysqlProvider(MysqlCredentials credentials) {
        this.credentials = credentials;

        config = new HikariConfig();
        loadDatabase();
    }

    private void loadDatabase() {
        config.setPoolName("Mutility MySQL Pool");
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl(getConnectionUrl());
        config.setUsername(credentials.getUsername());
        config.setPassword(credentials.getPassword());
        config.setMaximumPoolSize(MutilityConfig.HIKARI_POOL.get());
        config.setMinimumIdle(2);
        config.setIdleTimeout(120000);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikari = new HikariDataSource(config);

        ConsoleLogger.info("Ho caricato un hikari" + " Pool: " + hikari.getMaximumPoolSize());
    }

    private String getConnectionUrl() {
        return "jdbc:mariadb://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabase();
    }

    /**
     * @return Connection to mysql database
     */
    public Connection getConnection() throws SQLException {
        return hikari.getConnection();
    }


}