package it.fedet.mutility.common.plugin.database.mysql;

import it.fedet.mutility.common.database.mysql.MysqlProvider;
import it.fedet.mutility.common.database.mysql.data.MysqlData;
import it.fedet.mutility.common.plugin.database.DBPlugin;

public abstract class MysqlPlugin<P extends MysqlProvider, D extends MysqlData> extends DBPlugin<P, D> {
}