package it.fedet.mutility.common.server.gui;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.gui.item.impl.GuiItem;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class PaginatedInteractGui<T extends BasePlugin> extends InteractGui<T> {

    protected final Map<UUID, Integer> currentPage = new HashMap<>();

    /**
     * Constructor for InteractGui.
     *
     * @param plugin The instance of the plugin that extends BasePlugin.
     * @param title  The title of the GUI.
     */
    protected PaginatedInteractGui(T plugin, String title) {
        super(plugin, title);
    }


    public void onSetup(Player player) {
        List<GuiItem> items = getItems(player);

        clearData();
        setup(player, items);
        setupPagination(player, items);
    }

    public abstract void setup(Player player, List<GuiItem> items);

    public void setupPagination(Player player, List<GuiItem> items) {
        int startPage = getPage(player)*getFreeSlot();

        for (int i = startPage; i < startPage+getFreeSlot() && i < items.size(); i++)
            addItem(items.get(i));
    }



    public void nextPage(Player player) {
        currentPage.put(player.getUniqueId(), getPage(player)+1);

        clearData();
        onSetup(player);
    }

    public void previousPage(Player player) {
        currentPage.put(player.getUniqueId(), getPage(player)-1);

        clearData();
        onSetup(player);
    }

    public boolean hasNextPage(Player player, List<GuiItem> items) {
        return (items.size() > ((getPage(player)*getFreeSlot())+getFreeSlot()));
    }

    public boolean hasPreviousPage(Player player) {
        return (getPage(player) > 0);
    }


    public int getPage(Player player) {
        return Optional.ofNullable(currentPage.get(player.getUniqueId())).orElse(0);
    }

    public abstract int getFreeSlot();
    public abstract List<GuiItem> getItems(Player player);

    @Override
    public void open(Player player) {
        this.createInventory();
        onSetup(player);
        player.openInventory(inventory);
    }
}
