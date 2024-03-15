package it.fedet.mutility.common.server.item.interact.selector;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.item.action.ActionItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public interface InteractItemSelector<P extends BasePlugin> extends Supplier<ActionItem> {

    void load(P plugin);

    static <P extends BasePlugin> void load(P plugin, InteractItemSelector<P>[] values) {
        for (InteractItemSelector<P> value : values)
            value.load(plugin);
    }

    static <P extends BasePlugin> List<ActionItem> adaptToDataProvider(P plugin, InteractItemSelector<P>[] values) {
        List<ActionItem> result = new ArrayList<>();
        load(plugin, values);

        for (InteractItemSelector<P> item : values)
            result.add(item.get());

        return result;
    }

}