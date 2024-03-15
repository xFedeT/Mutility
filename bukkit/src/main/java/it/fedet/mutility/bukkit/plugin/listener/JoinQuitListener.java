package it.fedet.mutility.bukkit.plugin.listener;

import it.fedet.mutility.bukkit.database.data.MutilityRedisData;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.bukkit.plugin.module.DisguiseModule;
import it.fedet.mutility.common.server.listener.RegistrableListener;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.staffmode.playermode.vanish.Vanish;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class JoinQuitListener implements RegistrableListener {

    private final DisguiseModule disguise;
    private final MutilityRedisData redisData;

    public JoinQuitListener(Mutility plugin) {
        this.disguise = plugin.gestDisguiseModule();
        this.redisData = plugin.getDatabaseData();

        plugin.getTabAPI().getEventBus().register(PlayerLoadEvent.class, this::onTabJoin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!DefaultPermission.STAFF.hasPermission(player))
            Bukkit.getOnlinePlayers().stream()
                    .filter(online -> Vanish.isVanished(online.getUniqueId()))
                    .forEach(player::hidePlayer);


        if (redisData.getData("disguise:" + player.getUniqueId()).isPresent())
            disguise.addDisguise(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        disguise.removeDisguise(player);
    }

    private void onTabJoin(PlayerLoadEvent event) {
        TabPlayer tabPlayer = event.getPlayer();
        Player player = (Player) tabPlayer.getPlayer();

        if (disguise.isDisguised(player)) disguise.updateName(player);
    }

}
