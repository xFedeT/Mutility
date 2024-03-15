package it.fedet.mutility.common.server.gui.selector.impl;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.gui.item.GuiActionItem;
import it.fedet.mutility.common.server.gui.item.impl.GuiItem;
import it.fedet.mutility.common.server.gui.item.selector.GuiItemSelector;
import it.fedet.mutility.common.server.item.builder.ItemBuilder;
import org.bukkit.Material;

import java.util.function.Function;

public enum DefaultGuiItem implements GuiItemSelector<Mutility> {

    FILL(new GuiItem(
            ItemBuilder.builder(Material.STAINED_GLASS_PANE, 1, 7)
                    .setDisplayName(" ")
                    .build(),
            event -> event.setCancelled(true)
    ));

    private Function<Mutility, GuiActionItem> loader;
    private GuiActionItem actionItem;

    DefaultGuiItem(GuiActionItem actionItem) {
        this.actionItem = actionItem;
    }
    DefaultGuiItem(Function<Mutility, GuiActionItem> loader) {
        this.loader = loader;
    }

    @Override
    public void load(Mutility plugin) {
        actionItem = loader.apply(plugin);
    }

    @Override
    public GuiActionItem get() {
        return actionItem;
    }

}