package it.fedet.mutility.bukkit.plugin.data;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.bukkit.plugin.command.staff.GamemodeCommand;
import it.fedet.mutility.bukkit.plugin.command.staff.GmxCommand;
import it.fedet.mutility.bukkit.plugin.command.staff.RealNameCommand;
import it.fedet.mutility.bukkit.plugin.command.staff.StaffModeCommand;
import it.fedet.mutility.bukkit.plugin.command.vip.DisguiseCommand;
import it.fedet.mutility.bukkit.plugin.listener.JoinQuitListener;
import it.fedet.mutility.bukkit.plugin.listener.KickListener;
import it.fedet.mutility.bukkit.config.MutilityConfig;
import it.fedet.mutility.common.plugin.data.DataProvider;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.gui.InteractGui;
import it.fedet.mutility.common.server.gui.selector.GuiSelector;
import it.fedet.mutility.common.server.item.action.ActionItem;
import it.fedet.mutility.common.server.item.interact.selector.InteractItemSelector;
import it.fedet.mutility.common.server.listener.RegistrableListener;
import it.fedet.mutility.common.server.player.spectator.gui.SpectatorGuiSelector;
import it.fedet.mutility.common.server.player.spectator.inventory.SpectatorItemSelector;
import it.fedet.mutility.common.server.player.spectator.listener.SpectatorListener;
import it.fedet.mutility.common.server.staffmode.listener.GeneralListener;
import it.fedet.mutility.bukkit.plugin.command.vip.FlyCommand;
import it.fedet.mutility.common.server.staffmode.playermode.staffmode.StaffMode;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MutilityDataProvider extends DataProvider<Mutility> {

    public MutilityDataProvider(Mutility plugin) {
        super(plugin);
    }

    @Override
    public Map<Class<?>, FileConfiguration> getConfigValues() {
        Map<Class<?>, FileConfiguration> values = new HashMap<>();

        values.put(MutilityConfig.class, plugin.getConfig());

        return values;
    }

    @Override
    public List<ActionItem> getInteractItems() {
        List<ActionItem> items = new ArrayList<>();

        items.addAll(InteractItemSelector.adaptToDataProvider(plugin, StaffMode.Item.VALUES));
        items.addAll(InteractItemSelector.adaptToDataProvider(plugin, SpectatorItemSelector.VALUES));

        return items;
    }

    @Override
    public List<InteractGui<Mutility>> getInteractGuis() {
        List<InteractGui<Mutility>> interactGuis = new ArrayList<>();

        interactGuis.addAll(GuiSelector.adaptToDataProvider(plugin, SpectatorGuiSelector.VALUES));

        return interactGuis;
    }

    @Override
    public List<RegistrableCommand<Mutility>> getCommands() {
        List<RegistrableCommand<Mutility>> commands = new ArrayList<>();

        commands.add(new FlyCommand(plugin));
        commands.add(new DisguiseCommand(plugin));
        commands.add(new StaffModeCommand(plugin));

        commands.add(new GamemodeCommand(plugin));
        commands.add(new GmxCommand(plugin, "gms"));
        commands.add(new GmxCommand(plugin, "gmc"));
        commands.add(new GmxCommand(plugin, "gma"));
        commands.add(new GmxCommand(plugin, "gmsp"));
        commands.add(new RealNameCommand(plugin));


        return commands;
    }

    @Override
    public List<RegistrableListener> getListeners() {
        List<RegistrableListener> listeners = new ArrayList<>();

        listeners.add(new GeneralListener(plugin));
        listeners.add(new SpectatorListener());
        listeners.add(new JoinQuitListener(plugin));
        listeners.add(new KickListener());

        return listeners;

    }

}