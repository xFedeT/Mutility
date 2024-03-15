package it.fedet.mutility.velocity.common.server.permission;

import com.velocitypowered.api.proxy.Player;

import java.util.function.Supplier;

public interface PermissionProvider extends Supplier<String> {

    String PREFIX = "arcadiamc.";

    default boolean hasPermission(Player player) {
        return player.hasPermission(get());
    }

}