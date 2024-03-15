package it.fedet.mutility.common.server.player.permission;

import org.bukkit.entity.Player;

import java.util.function.Supplier;

public interface PermissionProvider extends Supplier<String> {

    String PERMISSION_PREFIX = "arcadiamc.";

    default boolean hasPermission(Player player) {
        return player.hasPermission(get());
    }

}