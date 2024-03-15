package it.fedet.mutility.bukkit.api.modules;

public abstract class BaseModule {

    private final String name;
    private final boolean enabled;



    protected BaseModule(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
