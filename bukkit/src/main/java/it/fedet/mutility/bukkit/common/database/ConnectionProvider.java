package it.fedet.mutility.common.database;

public interface ConnectionProvider<T, E extends Exception> {

    /**
     * @return connection to the database
     * @throws E the error generated if connection fails
     */
    T getConnection() throws E;

}