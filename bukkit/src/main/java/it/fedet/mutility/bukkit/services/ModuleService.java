package it.fedet.mutility.bukkit.services;

import it.fedet.mutility.bukkit.MutilityBukkit;
import it.fedet.mutility.bukkit.api.modules.BaseModule;
import it.fedet.mutility.bukkit.api.services.Service;

import java.util.ArrayList;
import java.util.List;

public class ModuleService implements Service {

    private final MutilityBukkit plugin;

    private final List<BaseModule> modules = new ArrayList<>();

    public ModuleService(MutilityBukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

}
