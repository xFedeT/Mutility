package it.fedet.mutility.common.server.player.spectator.inventory;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.item.builder.ItemBuilder;
import it.fedet.mutility.common.server.item.interact.InteractActionItem;
import it.fedet.mutility.common.server.item.interact.impl.InteractItem;
import it.fedet.mutility.common.server.item.interact.selector.InteractItemSelector;
import it.fedet.mutility.common.server.player.spectator.gui.SpectatorGuiSelector;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Function;

public enum SpectatorItemSelector implements InteractItemSelector<Mutility> {

    TELEPORTER(0, plugin -> new InteractItem(
            ItemBuilder.builder(Material.COMPASS)
                    .setDisplayName("&aTeleporter &7(Tasto Destro)")
                    .build(),
            event -> {
                SpectatorGuiSelector.TELEPORT.get().open(event.getPlayer());
                event.setCancelled(true);
            }
    )),
    LEAVE(8, plugin -> new InteractItem(
            ItemBuilder.builder(Material.BED)
                    .setDisplayName("&cLobby &7(Tasto Destro)")
                    .build(),
            event -> {
                event.getPlayer().kickPlayer("");
                event.setCancelled(true);
            }
    ));

    public static final SpectatorItemSelector[] VALUES = values();
    private final int slot;
    private final Function<Mutility, InteractActionItem> loader;
    private InteractActionItem interactItem;

    SpectatorItemSelector(int slot, Function<Mutility, InteractActionItem> loader) {
        this.slot = slot;
        this.loader = loader;
    }

    @Override
    public void load(Mutility plugin) {
        interactItem = loader.apply(plugin);
    }

    public static void loadInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();

        for (SpectatorItemSelector value : VALUES)
            inventory.setItem(value.slot, value.interactItem.getItem());
    }

    @Override
    public InteractActionItem get() {
        return interactItem;
    }

}