package it.fedet.mutility.common.util;

import com.viaversion.viaversion.api.Via;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerUtil {

    private ServerUtil() {}

    public static boolean isSwGame() {
        return Bukkit.getServerName().startsWith("SW") && !Bukkit.getServerName().contains("Lobby");
    }

    public static int getVersion(Player player) {
        return Via.getAPI().getPlayerVersion(player.getUniqueId());
    }

}
