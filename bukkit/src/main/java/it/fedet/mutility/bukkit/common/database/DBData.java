package it.fedet.mutility.common.database;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface DBData<C, E extends Exception> extends ConnectionProvider<C, E> {

    /**
     * @param runnable will be executed when future is complete
     */
    CompletableFuture<Void> runAsync(Runnable runnable);

    /**
     * @param supplier will be executed when future is complete
     * @param <T>      type of the value to supply
     * @return the value from the supplier
     */
    <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier);

    /**
     * @return connection to the database
     * @throws E the error generated if connection fails
     */
    C getConnection() throws E;

}