package it.fedet.mutility.common.server.gui.selector;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.gui.InteractGui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public interface GuiSelector<P extends BasePlugin> extends Supplier<InteractGui<P>> {

    void load(P plugin);

    static <P extends BasePlugin> void load(P plugin, GuiSelector<P>[] values) {
        for (GuiSelector<P> value : values)
            value.load(plugin);
    }

    static <P extends BasePlugin> List<InteractGui<P>> adaptToDataProvider(P plugin, GuiSelector<P>[] values) {
        List<InteractGui<P>> result = new ArrayList<>();
        load(plugin, values);

        for (GuiSelector<P> interactGui : values)
            result.add(interactGui.get());

        return result;
    }

}