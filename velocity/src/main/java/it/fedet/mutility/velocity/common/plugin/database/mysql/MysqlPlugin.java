package it.fedet.mutility.velocity.common.plugin.database.mysql;

import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.database.mysql.MysqlProvider;
import it.fedet.mutility.velocity.common.database.mysql.data.MysqlData;
import it.fedet.mutility.velocity.common.plugin.database.DBPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;

public abstract class MysqlPlugin extends DBPlugin<MysqlProvider, MysqlData> {
    public MysqlPlugin(ProxyServer server, Logger logger, Path dataDirectory) {
        super(server, logger, dataDirectory);
    }
}