package it.fedet.mutility.common.server.config.value;

import it.fedet.mutility.common.server.chat.ConsoleLogger;
import it.fedet.mutility.common.server.config.file.ConfigFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;

public abstract class ConfigValue<T> implements Supplier<T> {

    protected final String path;
    protected T value;

    protected ConfigValue(String path, T value) {
        this.path = path;
        this.value = value;
    }

    public static void loadValues(Class<?> clazz, FileConfiguration config) {
        Field[] fields = clazz.getFields();

        for (Field field : fields) {
            if (!Modifier.isPublic(field.getModifiers())) continue;
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!Modifier.isFinal(field.getModifiers())) continue;

            try {
                Object value = field.get(null);
                if (!(value instanceof ConfigValue<?>)) continue;

                ConfigValue<?> configValue = (ConfigValue<?>) value;
                configValue.load(config);
            } catch (IllegalAccessException | IllegalArgumentException exception) {
                ConsoleLogger.error("Failure to load configuration values of class " + clazz.getName(), exception);
            }
        }
    }

    protected abstract T getFromConfig(FileConfiguration config);

    protected void setToConfig(FileConfiguration config, T value) {
        config.set(path, value);
    }

    public void load(FileConfiguration config) {
        T data = getFromConfig(config);
        if (data != null) value = data;
    }

    public void update(FileConfiguration config, T value, Runnable save) {
        setToConfig(config, value);
        save.run();
        this.value = value;
    }

    public void update(ConfigFile configFile, T value) {
        setToConfig(configFile.getConfig(), value);
        configFile.save();
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

}