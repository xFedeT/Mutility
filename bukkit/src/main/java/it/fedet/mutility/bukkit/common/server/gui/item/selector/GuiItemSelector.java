package it.fedet.mutility.common.server.gui.item.selector;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.gui.item.GuiActionItem;

import java.util.function.Supplier;

public interface GuiItemSelector<P extends BasePlugin> extends Supplier<GuiActionItem> {

    void load(P plugin);

    static <P extends BasePlugin> void load(P plugin, GuiItemSelector<P>[] values) {
        for (GuiItemSelector<P> value : values)
            value.load(plugin);
    }

}