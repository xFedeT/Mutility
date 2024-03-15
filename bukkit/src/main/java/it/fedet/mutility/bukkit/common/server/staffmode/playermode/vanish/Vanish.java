package it.fedet.mutility.common.server.staffmode.playermode.vanish;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.staffmode.playermode.PlayerMode;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Vanish extends PlayerMode {
    private static final List<UUID> toggle = new ArrayList<>();

    private Vanish() {

    }

    public enum Action implements PlayerMode.Action {

        ON(player -> {
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (DefaultPermission.STAFF.hasPermission(target)) continue;
                target.hidePlayer(player);
            }

            toggle.add(player.getUniqueId());
            return true;
        }),
        OFF(player -> {
            for (Player target : Bukkit.getOnlinePlayers()) target.showPlayer(player);

            toggle.remove(player.getUniqueId());
            return false;
        }),
        TOGGLE(player -> {
            if (toggle.contains(player.getUniqueId())) {
                return OFF.apply(player);
            }

            return ON.apply(player);
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

        INVENTORY_OPEN((event, plugin) -> {
            PlayerInteractEvent invOpenEvent = (PlayerInteractEvent) event;

            Player player = invOpenEvent.getPlayer();
            if (!toggle.contains(player.getUniqueId())) return;
            if (invOpenEvent.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) return;
            if (!(invOpenEvent.getClickedBlock() instanceof Chest)) return;

            invOpenEvent.setCancelled(true);

            Chest chest = (Chest) invOpenEvent.getClickedBlock();
            player.openInventory(chest.getInventory());
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

    public static boolean isVanished(UUID uuid) {
        return toggle.contains(uuid);
    }
}