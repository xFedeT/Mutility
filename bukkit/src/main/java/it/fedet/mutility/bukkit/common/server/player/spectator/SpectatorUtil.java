package it.fedet.mutility.common.server.player.spectator;

import it.fedet.mutility.common.server.player.spectator.inventory.SpectatorItemSelector;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class SpectatorUtil {

    private static final Set<UUID> spectators = new HashSet<>();
    private static final Map<UUID, UUID> spectating = new HashMap<>();

    private SpectatorUtil() {
    }

    public static void setSpectator(Player player) {
        setSpectator(player, false);
    }

    public static void setSpectator(Player player, boolean clearInv) {
        if (clearInv) {
            PlayerInventory inventory = player.getInventory();
            inventory.setArmorContents(new ItemStack[inventory.getArmorContents().length]);
            inventory.clear();
        }

        hidePlayer(player);
        loadSpectator(player);

        spectators.add(player.getUniqueId());

        // FIXME: 7/19/2023 :)
        /*if (!SpectatorUtil.isBeginSpectating(player)) return;
        SpectatorUtil.getWhoSpectating(player).forEach(spectatorOp ->
                spectatorOp.ifPresent(SpectatorUtil::spectateOff));*/
    }

    public static void spectateOff(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);

        spectating.remove(player.getUniqueId());
    }

    public static void unsetSpectator(Player player) {
        showPlayer(player);
        unloadSpectator(player);

        spectators.remove(player.getUniqueId());
    }

    public static void spectateOn(Player player, Player target) {
        player.setGameMode(GameMode.SPECTATOR);
        player.setSpectatorTarget(target);

        spectating.put(player.getUniqueId(), target.getUniqueId());
    }

    public static boolean isSpectator(Player player) {
        return spectators.contains(player.getUniqueId());
    }

    public static boolean isBeginSpectating(Player player) {
        return spectating.containsValue(player.getUniqueId());
    }

    public static List<Optional<Player>> getWhoSpectating(Player player) {
        List<Optional<Player>> spectators = new ArrayList<>();
        for (UUID uuid : spectating.values())
            if (player.getUniqueId().equals(uuid))
                spectators.add(Optional.ofNullable(Bukkit.getPlayer(uuid)));

        return spectators;
    }

    private static void loadSpectator(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);

        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.getInventory().clear();

        SpectatorItemSelector.loadInventory(player);
    }

    private static void unloadSpectator(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.getInventory().clear();
    }

    private static void hidePlayer(Player player) {
        for (Player target : Bukkit.getOnlinePlayers())
            target.hidePlayer(player);
    }

    private static void showPlayer(Player player) {
        for (Player target : Bukkit.getOnlinePlayers())
            target.showPlayer(player);
    }

}