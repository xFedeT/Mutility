package it.fedet.mutility.bukkit.plugin.module;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface DisguiseModule {

    void disguise(Player player, Property skin);

    void disguise(Player player, String name);

    void disguise(Player player);

    void disguise(Player player, String name, Property skin);

    void undisguise(Player player);

    void updateName(Player player);

    boolean isDisguised(Player player);

    void addDisguise(Player player);

    void removeDisguise(Player player);

    void addRedis(Player player);

    void removeRedis(Player player);

    /**
     * Retrieves the real (original) name of a disguised player.
     *
     * @param player The disguised player.
     * @return The real name of the player.
     */
    String getRealName(Player player, boolean db);
}
