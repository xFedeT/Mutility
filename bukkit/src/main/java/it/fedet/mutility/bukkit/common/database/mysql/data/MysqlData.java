package it.fedet.mutility.common.database.mysql.data;

import it.fedet.mutility.bukkit.config.MutilityConfig;
import it.fedet.mutility.common.database.DBData;
import it.fedet.mutility.common.database.mysql.MysqlProvider;
import it.fedet.mutility.common.server.chat.ConsoleLogger;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class MysqlData implements DBData<Connection, SQLException> {

    protected static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(MutilityConfig.DATABASE_THREADS.get());


    private final MysqlProvider provider;

    protected MysqlData(MysqlProvider provider, @Language("SQL") String... queries) {
        this.provider = provider;

        loadQueries(queries);
    }

    private void loadQueries(String[] queries) {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            for (String query : queries)
                statement.addBatch(query);

            statement.executeBatch();
        } catch (SQLException exception) {
            ConsoleLogger.error("Failure loading queries", exception);
        }
    }

    @Override
    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, EXECUTOR);
    }

    @Override
    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, EXECUTOR);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return provider.getConnection();
    }

}