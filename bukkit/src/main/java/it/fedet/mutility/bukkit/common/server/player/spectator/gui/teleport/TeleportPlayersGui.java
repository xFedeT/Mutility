package it.fedet.mutility.common.server.player.spectator.gui.teleport;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.gui.InteractGui;
import it.fedet.mutility.common.server.gui.item.GuiActionItem;
import it.fedet.mutility.common.server.gui.item.impl.GuiItem;
import it.fedet.mutility.common.server.item.builder.ItemBuilder;
import it.fedet.mutility.common.server.player.spectator.SpectatorUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.stream.Collectors;

public class TeleportPlayersGui extends InteractGui<Mutility> {

    private List<Player> players;

    public TeleportPlayersGui(Mutility plugin) {
        super(plugin, "&aTeleporter");

        setup();
    }

    @Override
    protected void setup() {
        int i = 0;

        for (Player target : players) {
            GuiActionItem item = new GuiItem(
                    ItemBuilder.builder(Material.SKULL_ITEM, 1, 3)
                            .setDisplayName("&r&7" + target.getName())
                            .setLore(
                                    "",
                                    "&7Vita: &a" + (int) (target.getHealth() * 100 / 20) + "%",
                                    "&7Cibo: &6" + target.getFoodLevel() * 100 / 20 + "%",
                                    "",
                                    "&7Teletrasportati (Tasto Sinistro)",
                                    "&7Osserva (Tasto Destro)"
                            )
                            .setPlayerSkull(target.getName())
                            .build(),
                    event -> {
                        if (!(event.getWhoClicked() instanceof Player)) return;

                        Player player = (Player) event.getWhoClicked();

                        if (event.getClick() == ClickType.LEFT) player.teleport(target);
                        if (event.getClick() == ClickType.RIGHT) SpectatorUtil.spectateOn(player, target);

                        player.closeInventory();

                        event.setCancelled(true);
                    }
            );

            setItem(i, item);
            i++;
        }
    }

    @Override
    public int getRows() {
        players = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getGameMode() == GameMode.SURVIVAL)
                .collect(Collectors.toList());

        return (int) Math.ceil(players.size() / 9.0);
    }

    @Override
    public boolean setupOnOpen() {
        return true;
    }

}