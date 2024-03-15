package it.fedet.mutility.common.plugin.data;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.config.value.ConfigValue;
import it.fedet.mutility.common.server.gui.InteractGui;
import it.fedet.mutility.common.server.gui.listener.GuiListener;
import it.fedet.mutility.common.server.item.action.ActionItem;
import it.fedet.mutility.common.server.item.interact.listener.ItemsListener;
import it.fedet.mutility.common.server.item.nbt.ItemUUID;
import it.fedet.mutility.common.server.listener.RegistrableListener;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DataProvider<T extends BasePlugin> {

    protected final T plugin;
    private final Map<String, InteractGui<T>> interactGuis = new HashMap<>();
    private final Map<String, ActionItem> interactItems = new HashMap<>();

    protected DataProvider(T plugin) {
        this.plugin = plugin;
    }

    /**
     * Register InteractItems, InteractGuis, Commands and Listeners returned by special methods
     */
    public void register() {
        registerInteractItems();
        registerInteractGuis();

        registerCommands();
        registerListeners();
    }

    /**
     * Register ConfigValues returned by getConfigValues method
     */
    public void registerConfigValues() {
        getConfigValues().forEach(ConfigValue::loadValues);
    }

    /**
     * Register InteractItems returned by getInteractItems method
     */
    private void registerInteractItems() {
        for (ActionItem interactItem : getInteractItems())
            ItemUUID.get(interactItem.getItem()).ifPresent(uuid -> interactItems.put(uuid, interactItem));
    }

    public void addInteractItem(ActionItem interactItem) {
        ItemUUID.get(interactItem.getItem()).ifPresent(uuid -> interactItems.put(uuid, interactItem));
    }

    public void addInteractItems(List<ActionItem> interactItemList) {
        for (ActionItem interactItem : interactItemList)
            ItemUUID.get(interactItem.getItem()).ifPresent(uuid -> interactItems.put(uuid, interactItem));
    }

    /**
     * Register InteractGuis returned by getInteractGuis method
     */
    private void registerInteractGuis() {
        for (InteractGui<T> interactGui : getInteractGuis())
            interactGuis.put(interactGui.getTitle(), interactGui);
    }

    public void addInteractGui(InteractGui<T> interactGui) {
        interactGuis.put(interactGui.getTitle(), interactGui);
    }

    public void addInteractGuis(List<InteractGui<T>> interactGuiList) {
        for (InteractGui<T> interactGui : interactGuiList)
            interactGuis.put(interactGui.getTitle(), interactGui);
    }

    /**
     * Register Commands returned by getCommands method
     */
    private void registerCommands() {
        getCommands().forEach(command -> command.register(plugin));
    }

    /**
     * Register Listeners returned by getListeners method
     */
    private void registerListeners() {
        new ItemsListener(interactItems).register(plugin);
        new GuiListener(interactGuis).register(plugin);

        getListeners().forEach(listener -> listener.register(plugin));
    }

    /**
     * @return The map of Class (that contains ConfigValues) and FileConfiguration (from which plugin will get the values)
     */
    public Map<Class<?>, FileConfiguration> getConfigValues() {
        return Collections.emptyMap();
    }

    /**
     * @return The list of commands to register
     */
    public List<RegistrableCommand<T>> getCommands() {
        return Collections.emptyList();
    }

    /**
     * @return The list of InteractItems to register
     */
    public List<ActionItem> getInteractItems() {
        return Collections.emptyList();
    }

    /**
     * @return The list of InteractGuis to register
     */
    public List<InteractGui<T>> getInteractGuis() {
        return Collections.emptyList();
    }

    /**
     * @return The list of listeners to register
     */
    public List<RegistrableListener> getListeners() {
        return Collections.emptyList();
    }

}