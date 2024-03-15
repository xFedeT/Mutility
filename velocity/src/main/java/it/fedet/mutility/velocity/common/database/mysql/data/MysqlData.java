package it.fedet.mutility.velocity.common.database.mysql.data;

import it.fedet.mutility.velocity.common.database.DBData;
import it.fedet.mutility.velocity.common.database.mysql.MysqlProvider;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class MysqlData implements DBData<Connection, SQLException> {

    //TODO: Fixa
    protected static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

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
            //TODO: Fixa
            //ChatLogger.error("Failure loading queries", exception);
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