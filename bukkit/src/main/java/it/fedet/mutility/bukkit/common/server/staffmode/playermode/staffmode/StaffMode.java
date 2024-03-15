package it.fedet.mutility.common.server.staffmode.playermode.staffmode;

import it.fedet.mutility.bukkit.database.data.MutilityRedisData;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.item.action.ActionItem;
import it.fedet.mutility.common.server.item.builder.ItemBuilder;
import it.fedet.mutility.common.server.item.interact.impl.InteractEntityItem;
import it.fedet.mutility.common.server.item.interact.impl.InteractItem;
import it.fedet.mutility.common.server.item.interact.selector.InteractItemSelector;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.staffmode.playermode.PlayerMode;
import it.fedet.mutility.common.server.staffmode.playermode.fly.Fly;
import it.fedet.mutility.common.server.staffmode.playermode.godmode.GodMode;
import it.fedet.mutility.common.server.staffmode.playermode.vanish.Vanish;
import it.fedet.mutility.common.util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class StaffMode extends PlayerMode {

    private static final Set<UUID> toggle = new HashSet<>();

    public enum StaffAction implements PlayerMode.PluginAction {

        ON((plugin,player) -> {
            Item.saveHotBarItems(player);
            Vanish.Action.ON.apply(player);
            GodMode.Action.ON.apply(player);
            Fly.Action.ON.apply(player);

            MutilityRedisData redisData = plugin.getDatabaseData();
            redisData.enableStaffMode(player);

            toggle.add(player.getUniqueId());
            Chatify.sendMessage(player, "&3Staff » &bHai &aabilitato &bla modalità Staff!");
            return true;
        }),
        OFF((plugin,player) -> {
            Item.loadHotBarItems(player);
            Vanish.Action.OFF.apply(player);
            GodMode.Action.OFF.apply(player);

            MutilityRedisData redisData = plugin.getDatabaseData();
            redisData.disableStaffMode(player);

            toggle.remove(player.getUniqueId());
            Chatify.sendMessage(player, "&3Staff » &bHai &cdisabilitato &bla modalità Staff!");
            return false;
        }),
        TOGGLE((plugin, player) -> {
            if (toggle.contains(player.getUniqueId()))
                return OFF.apply(plugin, player);

            return ON.apply(plugin, player);
        });

        private final BiFunction<Mutility, Player, Boolean> action;

        StaffAction(BiFunction<Mutility, Player, Boolean> action) {
            this.action = action;
        }

        public Boolean apply(Mutility plugin, Player player) {
            return action.apply(plugin, player);
        }

    }

    public enum Item implements InteractItemSelector<Mutility> {

        VANISH(7, plugin -> new InteractItem(ItemBuilder.builder(Material.INK_SACK)
                .setDyeColor(DyeColor.GRAY)
                .setDisplayName("&cSei invisibile")
                .build(),
                event -> {
                    Player player = event.getPlayer();

                    toggleVanish(
                            player,
                            Vanish.Action.TOGGLE.apply(player)
                    );

                    event.setCancelled(true);
                }
        )),
        RANDOM_TP(0, plugin -> new InteractItem(ItemBuilder.builder(Material.COMPASS)
                .setDisplayName("&cRandom TP")
                .build(),
                event -> {
                    List<? extends Player> players = new ArrayList<>(Bukkit.getOnlinePlayers().stream().toList());
                    players.remove(event.getPlayer());

                    Player target = players.get(ThreadLocalRandom.current().nextInt(players.size()));

                    event.getPlayer().teleport(target);
                    event.setCancelled(true);
                }
        )),
        INVENTORY_VIEWER(1, plugin -> new InteractEntityItem(ItemBuilder.builder(Material.BOOK)
                .setDisplayName("&eInventory Viewer")
                .build(),
                event -> {
                    Entity entity = event.getRightClicked();
                    if (!(entity instanceof Player target)) return;

                    event.getPlayer().openInventory(target.getInventory());
                    event.setCancelled(true);
                }
        )),
        OFF(8, plugin -> new InteractItem(ItemBuilder.builder(Material.BARRIER)
                .setDisplayName("&cEsci")
                .build(),
                event -> {
                    StaffAction.OFF.apply(plugin, event.getPlayer());
                    event.setCancelled(true);
                }
        ));

        public static final Item[] VALUES = values();
        private static final HashMap<UUID, ItemStack[]> toggleItems = new HashMap<>();

        private final int slot;
        private final Function<Mutility, ActionItem> loader;


        private ActionItem interactItem;

        Item(int slot, Function<Mutility, ActionItem> loader) {
            this.slot = slot;
            this.loader = loader;
        }

        private static void toggleVanish(Player player, boolean toggle) {
            ItemBuilder itemBuilder = ItemBuilder.builder(player.getItemInHand())
                    .setDyeColor(!toggle ? DyeColor.YELLOW : DyeColor.GRAY)
                    .setDisplayName(!toggle ? "&dSei visibile" : "&cSei invisibile");

            player.setItemInHand(itemBuilder.build());
        }

        private static void saveHotBarItems(Player player) {
            UUID playerUUID = player.getUniqueId();
            if (toggleItems.containsKey(playerUUID)) return;

            PlayerInventory playerInventory = player.getInventory();
            ItemStack[] hotBarItems = new ItemStack[9];

            for (int i = 0; i < 9; i++) {
                hotBarItems[i] = playerInventory.getItem(i);
                playerInventory.setItem(i, new ItemStack(Material.AIR));
            }

            for (Item smItem : VALUES)
                playerInventory.setItem(smItem.slot, smItem.get().getItem());

            toggleItems.put(playerUUID, hotBarItems);
        }

        private static void loadHotBarItems(Player player) {
            UUID playerUUID = player.getUniqueId();
            if (!toggleItems.containsKey(playerUUID)) return;

            PlayerInventory playerInventory = player.getInventory();
            ItemStack[] hotBarItems = toggleItems.get(playerUUID);

            for (int i = 0; i < 9; i++)
                playerInventory.setItem(i, hotBarItems[i]);

            toggleItems.remove(playerUUID);
        }

        @Override
        public ActionItem get() {
            return interactItem;
        }

        @Override
        public void load(Mutility plugin) {
            interactItem = loader.apply(plugin);
        }
    }

    public enum Listener implements PlayerMode.Listener {

        JOIN((event, plugin) -> {
            Player player = ((PlayerJoinEvent) event).getPlayer();
            if (!DefaultPermission.STAFF.hasPermission(player)) return;

            MutilityRedisData redisData = plugin.getDatabaseData();
            Optional<String> op = redisData.getStaffMode(player);

            if (op.isPresent() && !ServerUtil.isSwGame()) StaffAction.ON.apply(plugin, player);
        }),
        QUIT((event, plugin) -> {
            Player player = ((PlayerQuitEvent) event).getPlayer();
            if (!DefaultPermission.STAFF.hasPermission(player)) return;

            toggle.remove(player.getUniqueId());
            Item.loadHotBarItems(player);
        }),
        INVENTORY_CLICK((event, plugin) -> {
            InventoryClickEvent invClickEvent = (InventoryClickEvent) event;
            HumanEntity humanEntity = ((InventoryClickEvent) event).getWhoClicked();

            if (!(humanEntity instanceof Player)) return;
            if (!toggle.contains(humanEntity.getUniqueId())) return;

            invClickEvent.setCancelled(true);
        }),
        PICKUP_ITEM((event, plugin) -> {
            PlayerPickupItemEvent pickUpEvent = (PlayerPickupItemEvent) event;

            if (!toggle.contains(((PlayerPickupItemEvent) event).getPlayer().getUniqueId())) return;

            pickUpEvent.setCancelled(true);
        });

        private final BiConsumer<Event, Mutility> action;

        Listener(BiConsumer<Event, Mutility> action) {
            this.action = action;
        }

        @Override
        public void accept(Event event, Mutility plugin) {
            action.accept(event, plugin);
        }
    }
}