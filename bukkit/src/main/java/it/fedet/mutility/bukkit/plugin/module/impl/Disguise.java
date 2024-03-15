package it.fedet.mutility.bukkit.plugin.module.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import it.fedet.mutility.bukkit.database.data.MutilityRedisData;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.bukkit.plugin.module.DisguiseModule;
import it.fedet.mutility.bukkit.config.MutilityConfig;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.chat.ConsoleLogger;
import it.fedet.mutility.common.util.RandomUtil;
import it.fedet.mutility.common.util.ServerUtil;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.placeholder.PlaceholderManager;
import me.neznamy.tab.api.placeholder.PlayerPlaceholder;
import me.neznamy.tab.api.tablist.TabListFormatManager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

public class Disguise implements DisguiseModule {

    private final Mutility plugin;
    private final MutilityRedisData redisData;
    private final PlaceholderManager placeholderManager;
    private final TabListFormatManager tabListManager;

    private final List<UUID> disguised = new ArrayList<>();

    private final Map<UUID, GameProfile> oldProfile = new HashMap<>();

    private final Multimap<UUID, UUID> whoCanSee = ArrayListMultimap.create();


    public Disguise(Mutility plugin) {
        this.plugin = plugin;
        this.redisData = plugin.getDatabaseData();
        this.placeholderManager = plugin.getTabAPI().getPlaceholderManager();
        this.tabListManager = plugin.getTabAPI().getTabListFormatManager();
    }

    @Override
    public void disguise(Player player, Property skin) {
        disguise(player, generateName(), skin);
    }

    @Override
    public void disguise(Player player, String name) {
        disguise(player, name, generateSkin());
    }

    @Override
    public void disguise(Player player) {
        disguise(player, generateName(), generateSkin());
    }

    @Override
    public void disguise(Player player, String name, Property skin) {
        CraftPlayer craftPlayer = ((CraftPlayer) player);
        oldProfile.put(player.getUniqueId(), craftPlayer.getProfile());
        //disguised.add(player.getUniqueId());

        GameProfile profile = new GameProfile(craftPlayer.getProfile().getId(), name);
        profile.getProperties().put("textures", skin);

        updatePlayer(player, profile);
        updateName(player);
    }

    @Override
    public void undisguise(Player player) {
        GameProfile profile = oldProfile.get(player.getUniqueId());
        oldProfile.remove(player.getUniqueId());

        MutilityConfig.DISGUISE_NAME.get().add(((CraftPlayer)player).getProfile().getName());
        disguise(player, profile.getName(), profile.getProperties().get("textures").stream().findFirst().orElse(null));
    }

    public String generateName() {
        List<String> randomName = MutilityConfig.DISGUISE_NAME.get();

        String generatedName = randomName.get(RandomUtil.generateInt(0, Math.max(0, randomName.size() - 1)));
        MutilityConfig.DISGUISE_NAME.get().remove(generatedName);

        return generatedName;
    }

    public Property generateSkin() {
        return MutilityConfig.DISGUISE_SKIN.get().get(RandomUtil.generateInt(0, Math.max(0, MutilityConfig.DISGUISE_SKIN.get().size() - 1)));
    }


    @Override
    public void updateName(Player player) {
        TabPlayer tabPlayer = plugin.getTabAPI().getPlayer(player.getUniqueId());
        if (tabPlayer == null || !tabPlayer.isLoaded()) return;


        PlayerPlaceholder colorPlaceholder = (PlayerPlaceholder) placeholderManager.getPlaceholder("%rank_color%");
        PlayerPlaceholder guildPlaceholder = (PlayerPlaceholder) placeholderManager.getPlaceholder("%gilda-name%");

        tabListManager.setName(tabPlayer, player.getName());

        colorPlaceholder.update(tabPlayer);
        guildPlaceholder.update(tabPlayer);
    }


    public void updatePlayer(Player player, GameProfile profile) {
        CraftPlayer craftPlayer = ((CraftPlayer) player);

        craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle()));

        setGP(player, craftPlayer.getHandle(), profile);

        craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle()));

        Bukkit.getOnlinePlayers().forEach(online -> {
            if (online.getUniqueId().equals(player.getUniqueId())) return;

            boolean canSee = online.canSee(player);

            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(craftPlayer.getHandle()));

            Bukkit.getServer().getScheduler().runTask(this.plugin, () -> online.hidePlayer(player));

            if (!canSee) return;
            Bukkit.getServer().getScheduler().runTaskLater(this.plugin, () -> online.showPlayer(player), 5);
        });

        ((CraftServer) Bukkit.getServer()).getServer().getPlayerList().players.removeIf(online -> online.getUniqueID().equals(player.getUniqueId()));
        ((CraftServer) Bukkit.getServer()).getServer().getPlayerList().players.add(((CraftPlayer)player).getHandle());
    }

    public void setGP(Player player, EntityLiving entityLiving, GameProfile gameProfile) {
        try {
            Field gp2 = entityLiving.getClass().getSuperclass().getDeclaredField("bH");
            gp2.setAccessible(true);

            gp2.set(entityLiving, gameProfile);

            gp2.setAccessible(false);

        } catch (Exception ex) {
            ConsoleLogger.error("C'e' stato un errore durante il disguise di " + player.getName(), ex);
        }
    }

    @Override
    public boolean isDisguised(Player player) {
        return disguised.contains(player.getUniqueId());
    }

    @Override
    public void addDisguise(Player player) {
        disguised.add(player.getUniqueId());

        if (ServerUtil.isSwGame()) {
            disguise(player);
            Chatify.sendMessage(player, "&aTi trovi in disguise con il nome: &f" + player.getName());
        }
    }
    @Override
    public void removeDisguise(Player player) {
        disguised.remove(player.getUniqueId());

        GameProfile profile = oldProfile.get(player.getUniqueId());
        if (profile == null) return;

        if (ServerUtil.isSwGame()) undisguise(player);

        MutilityConfig.DISGUISE_NAME.get().add(profile.getName());
    }

    @Override
    public void addRedis(Player player) {
        redisData.setData("disguise:" + player.getUniqueId(), player.getName());
    }

    @Override
    public void removeRedis(Player player) {
        redisData.removeData("disguise:" + player.getUniqueId());
    }

    @Override
    public String getRealName(Player player, boolean db) {
        if (db) return redisData.getData("disguise:" + player.getUniqueId()).orElse(player.getName());

        return Optional.ofNullable(oldProfile.get(player.getUniqueId()))
                .map(GameProfile::getName)
                .orElse(player.getName());
    }

    private void hideAll(Player player) {
        Bukkit.getOnlinePlayers().forEach(online -> {
            if (online.canSee(player)) {
                ConsoleLogger.info("Nome: " + online.getName());
                whoCanSee.put(player.getUniqueId(), online.getUniqueId());
            }

            online.hidePlayer(player);
        });
    }

    private void showAll(Player player) {
        Collection<UUID> whoSee = whoCanSee.get(player.getUniqueId());

        Bukkit.getOnlinePlayers().stream()
                .filter(online -> whoSee.contains(online.getUniqueId()))
                .forEach(online -> online.showPlayer(player));

        whoSee.remove(player.getUniqueId());
    }
}