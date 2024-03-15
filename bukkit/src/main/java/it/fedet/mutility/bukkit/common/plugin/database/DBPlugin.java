package it.fedet.mutility.common.plugin.database;

import it.fedet.mutility.common.database.ConnectionProvider;
import it.fedet.mutility.common.database.DBData;
import it.fedet.mutility.common.plugin.BasePlugin;

public abstract class DBPlugin<P extends ConnectionProvider<?, ?>, D extends DBData<?, ?>> extends BasePlugin {

    protected P connectionProvider;
    protected D databaseData;

    @Override
    protected void enable() {
        connectionProvider = getDBConnectionProvider();
        databaseData = getDBDataProvider();
    }

    protected abstract P getDBConnectionProvider();

    protected abstract D getDBDataProvider();

    public D getDatabaseData() {
        return databaseData;
    }

}