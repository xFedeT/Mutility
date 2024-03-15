package it.fedet.mutility.common.server.gui;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.gui.item.GuiActionItem;
import it.fedet.mutility.common.server.text.Textify;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class InteractGui<T extends BasePlugin> {

    protected final T plugin;
    protected final Map<Integer, GuiActionItem> guiItems = new HashMap<>();
    protected Inventory inventory;

    protected String title;
    protected int lastItemPos = 0;

    /**
     * Constructor for InteractGui.
     *
     * @param plugin The instance of the plugin that extends BasePlugin.
     * @param title  The title of the GUI.
     */
    protected InteractGui(T plugin, String title) {
        this.plugin = plugin;
        this.title = Textify.colorize(title);

        // Create the inventory for the GUI.
        createInventory();
    }

    /**
     * Creates the inventory for the GUI with a specific number of rows.
     */
    protected void createInventory() {
        inventory = Bukkit.createInventory(null, 9 * getRows(), title);
        guiItems.clear();
        lastItemPos = 0;
    }

    /**
     * Abstract method to be implemented by subclasses for setting up the GUI.
     */
    protected abstract void setup();
    public void clearData() {
        inventory.clear();
        guiItems.clear();
        lastItemPos = 0;
    }

    /**
     * Sets a GUI item at the specified slot position.
     *
     * @param slot     The slot position to set the GUI item.
     * @param guiItem  The GuiActionItem to set at the slot position.
     */
    public void setItem(int slot, GuiActionItem guiItem) {
        guiItems.put(slot, guiItem);
        inventory.setItem(slot, guiItem.getItem());
    }

    public boolean addItem(GuiActionItem guiItem) {
        for (int slot = lastItemPos; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) != null) continue;

            setItem(slot, guiItem);
            return true;
        }

        return false;
    }

    /**
     * Sets an ItemStack at the specified slot position in the GUI.
     *
     * @param slot The slot position to set the ItemStack.
     * @param item The ItemStack to set at the slot position.
     */
    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    public void addItem(ItemStack item) {
        for (int slot = lastItemPos; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) != null) continue;

            setItem(slot, item);
            return;
        }
    }

    /**
     * Fills the entire GUI with the provided ItemStack.
     *
     * @param item The ItemStack to fill the GUI with.
     */
    public void fill(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++)
            setItem(i, item);
    }

    /**
     * Fills the entire GUI with the provided GuiActionItem.
     *
     * @param item The GuiActionItem to fill the GUI with.
     */
    public void fill(GuiActionItem item) {
        for (int i = 0; i < inventory.getSize(); i++)
            setItem(i, item);
    }

    /**
     * Sets the border of the GUI with the provided ItemStack.
     *
     * @param item The ItemStack to use as the border of the GUI.
     */
    public void setBorder(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i <= 8 || i % 9 == 0 || (i + 1) % 9 == 0 || i >= inventory.getSize() - 9)
                setItem(i, item);
        }
    }

    /**
     * Sets the border of the GUI with the provided GuiActionItem.
     *
     * @param item The GuiActionItem to use as the border of the GUI.
     */
    public void setBorder(GuiActionItem item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i <= 8 || i % 9 == 0 || (i + 1) % 9 == 0 || i >= inventory.getSize() - 9)
                setItem(i, item);
        }
    }

    /**
     * Opens the GUI for the specified player.
     *
     * @param player The player to open the GUI for.
     */
    public void open(Player player) {
        // Check if the setup should be executed on opening the GUI.
        if (setupOnOpen()) {
            createInventory();
            setup();
        }

        // Open the inventory for the player.
        player.openInventory(inventory);
    }

    /**
     * Gets the map of GUI items associated with their slot positions.
     *
     * @return The map of GUI items with their slot positions.
     */
    public Map<Integer, GuiActionItem> getGuiItems() {
        return guiItems;
    }

    /**
     * Gets the title of the GUI.
     *
     * @return The title of the GUI.
     */
    public String getTitle() {
        return inventory.getTitle();
    }

    /**
     * Specifies whether the setup method should be called on opening the GUI.
     *
     * @return True if setup method should be called, false otherwise.
     */
    public boolean setupOnOpen() {
        return false;
    }

    public boolean removeOnClose() {
        return false;
    }
    public Consumer<InventoryCloseEvent> onClose() {
        return null;
    };

    /**
     * Specifies whether the event should be canceled when interacting with the GUI.
     *
     * @return True if the event should be canceled, false otherwise.
     */
    public boolean cancelEvent() {
        return false;
    }

    /**
     * Abstract method to be implemented by subclasses to define the number of rows in the GUI.
     *
     * @return The number of rows in the GUI.
     */
    public abstract int getRows();

}