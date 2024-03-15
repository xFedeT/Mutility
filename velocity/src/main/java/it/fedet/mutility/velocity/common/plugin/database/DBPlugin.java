package it.fedet.mutility.velocity.common.plugin.database;

import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.database.ConnectionProvider;
import it.fedet.mutility.velocity.common.database.DBData;
import it.fedet.mutility.velocity.common.plugin.BasePlugin;
import org.slf4j.Logger;

import java.nio.file.Path;

public abstract class DBPlugin<P extends ConnectionProvider<?, ?>, D extends DBData<?, ?>> extends BasePlugin {

    protected P connectionProvider;
    protected D databaseData;

    protected DBPlugin(ProxyServer server, Logger logger, Path dataDirectory) {
        super(server, logger, dataDirectory);
    }

    @Override
    protected void enable(ProxyInitializeEvent event) {
        connectionProvider = getDBConnectionProvider();
        databaseData = getDBDataProvider();
    }

    protected abstract P getDBConnectionProvider();

    protected abstract D getDBDataProvider();

    public D getDatabaseData() {
        return databaseData;
    }

}