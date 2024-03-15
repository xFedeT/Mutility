package it.fedet.mutility.velocity.common.server.config.value.impl;


/*
public class LocationConfigValue extends ConfigValue<Location> {

    public LocationConfigValue(String path, Location value) {
        super(path, value);
    }

    @Override
    protected Location getFromConfig(FileConfiguration config) {
        try {
            World world = Bukkit.getWorld(config.getString(path + ".world"));
            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");
            float yaw = (float) config.getDouble(path + ".yaw");
            float pitch = (float) config.getDouble(path + ".pitch");

            return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception ex) {
            ChatLogger.error("Errore while loading location from config", ex);
            return null;
        }
    }

    @Override
    protected void setToConfig(FileConfiguration config, Location location) {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
    }

}*/
