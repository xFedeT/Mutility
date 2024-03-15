package it.fedet.mutility.common.server.staffmode.playermode;

import it.fedet.mutility.bukkit.plugin.Mutility;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class PlayerMode {

    public interface PluginAction extends BiFunction<Mutility, Player, Boolean> {
    }

    public interface Action extends Function<Player, Boolean> {
    }

    public interface Listener extends BiConsumer<Event, Mutility> {
    }

}