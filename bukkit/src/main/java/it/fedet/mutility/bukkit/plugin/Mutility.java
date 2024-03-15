package it.fedet.mutility.bukkit.plugin;

import it.fedet.mutility.bukkit.database.MutilityRedisProvider;
import it.fedet.mutility.bukkit.database.data.MutilityRedisData;
import it.fedet.mutility.bukkit.plugin.data.MutilityDataProvider;
import it.fedet.mutility.bukkit.plugin.module.DisguiseModule;
import it.fedet.mutility.bukkit.plugin.module.impl.Disguise;
import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.plugin.data.DataProvider;
import it.fedet.mutility.common.plugin.database.redis.RedisPlugin;
import it.fedet.mutility.common.server.chat.ConsoleLogger;
import me.neznamy.tab.api.TabAPI;

public final class Mutility extends RedisPlugin<MutilityRedisProvider, MutilityRedisData> {

    private DisguiseModule disguiseModule;
    private TabAPI tabAPI;

    @Override
    public void enable() {
        super.enable();

        this.tabAPI = TabAPI.getInstance();
        disguiseModule = new Disguise(this);

        tabAPI.getPlaceholderManager().registerServerPlaceholder(
                "%velocity-counter%", 2500, () -> getDatabaseData().getData("global-count").orElse("0"));

        ConsoleLogger.info("");
        ConsoleLogger.info(" &b█▀▄▀█ █░█  &a" + getDescription().getName() + " &l&bv" + getDescription().getVersion());
        ConsoleLogger.info(" &b█░▀░█ █▄█  &l&8By " + getDescription().getAuthors().get(0));
        ConsoleLogger.info("");
    }

    @Override
    public void disable() {
        ConsoleLogger.info("");
        ConsoleLogger.info(" &b█▀▄▀█ █░█  &c" + getDescription().getName() + " &l&bv" + getDescription().getVersion());
        ConsoleLogger.info(" &b█░▀░█ █▄█  &l&8By " + getDescription().getAuthors().get(0));
        ConsoleLogger.info("");
    }

    @Override
    protected MutilityRedisProvider getDBConnectionProvider() {
        return new MutilityRedisProvider();
    }

    @Override
    protected MutilityRedisData getDBDataProvider() {
        return new MutilityRedisData(getDBConnectionProvider());
    }

    @Override
    protected DataProvider<? extends BasePlugin> getDataProvider() {
        return new MutilityDataProvider(this);
    }

    public DisguiseModule gestDisguiseModule() {
        return disguiseModule;
    }

    public TabAPI getTabAPI() {
        return tabAPI;
    }
}