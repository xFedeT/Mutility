package it.fedet.mutility.common.server.staffmode.playermode.godmode;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.staffmode.playermode.PlayerMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GodMode extends PlayerMode {

    private static final List<UUID> toggle = new ArrayList<>();

    public enum Action implements PlayerMode.Action {

        ON(player -> {
            toggle.add(player.getUniqueId());
            return true;
        }),
        OFF(player -> {
            toggle.remove(player.getUniqueId());
            return false;
        });

        private final Function<Player, Boolean> action;

        Action(Function<Player, Boolean> action) {
            this.action = action;
        }

        @Override
        public Boolean apply(Player player) {
            return action.apply(player);
        }

    }

    public enum Listener implements PlayerMode.Listener {

        ON_ENTITY_DAMAGE((event, plugin) -> {
            EntityDamageEvent entityDamageEvent = (EntityDamageEvent) event;
            Entity entity = entityDamageEvent.getEntity();

            if (!(entity instanceof Player)) return;
            if (!toggle.contains(entity.getUniqueId())) return;

            entityDamageEvent.setCancelled(true);
        });

        private final BiConsumer<Event, BasePlugin> action;


        Listener(BiConsumer<Event, BasePlugin> action) {
            this.action = action;
        }

        @Override
        public void accept(Event event, Mutility plugin) {
            action.accept(event, plugin);
        }

    }

}