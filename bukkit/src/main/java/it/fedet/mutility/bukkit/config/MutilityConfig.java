package it.fedet.mutility.bukkit.config;

import com.mojang.authlib.properties.Property;
import it.fedet.mutility.bukkit.config.value.PropertyListConfigValue;
import it.fedet.mutility.common.server.config.value.ConfigValue;
import it.fedet.mutility.common.server.config.value.impl.base.IntegerConfigValue;
import it.fedet.mutility.common.server.config.value.impl.base.StringConfigValue;
import it.fedet.mutility.common.server.config.value.impl.base.StringListConfigValue;

import java.util.ArrayList;
import java.util.List;

public interface MutilityConfig {

    ConfigValue<Integer> DATABASE_THREADS = new IntegerConfigValue("database-threads", 2);
    ConfigValue<String> REDIS_URI = new StringConfigValue("redis-uri", "redis://default:Briccardo05@localhost:6379/0");

    ConfigValue<Integer> HIKARI_POOL = new IntegerConfigValue("hikari-pool", 2);
    ConfigValue<Integer> SCOREBOARD_UPDATE = new IntegerConfigValue("scoreboard-tick", 10);
    ConfigValue<List<String>> DISGUISE_NAME = new StringListConfigValue("disguise.name", new ArrayList<>());
    ConfigValue<List<Property>> DISGUISE_SKIN = new PropertyListConfigValue("disguise.skin", new ArrayList<>());

}