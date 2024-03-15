package it.fedet.mutility.common.server.scoreboard;

import it.fedet.mutility.bukkit.config.MutilityConfig;
import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.scoreboard.board.Board;
import it.fedet.mutility.common.server.scoreboard.board.task.BoardUpdateTask;
import it.fedet.mutility.common.server.scoreboard.settings.BoardSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BoardManager implements Listener {

    private final BasePlugin plugin;
    private BoardSettings boardSettings;
    private Map<UUID, Board> scoreboards;
    private BukkitTask updateTask;

    public BoardManager(BasePlugin plugin, BoardSettings boardSettings) {
        this.plugin = plugin;
        this.boardSettings = boardSettings;
        this.scoreboards = new ConcurrentHashMap<>();
        this.updateTask = new BoardUpdateTask(this).runTaskTimer(plugin, 2L, MutilityConfig.SCOREBOARD_UPDATE.get());
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getOnlinePlayers().forEach(this::setup);
    }

    public void setBoardSettings(BoardSettings boardSettings) {
        this.boardSettings = boardSettings;
        scoreboards.values().forEach(board -> board.setBoardSettings(boardSettings));
    }

    public boolean hasBoard(Player player) {
        return scoreboards.containsKey(player.getUniqueId());
    }

    public Optional<Board> getBoard(Player player) {
        return Optional.ofNullable(scoreboards.get(player.getUniqueId()));
    }

    private void setup(Player player) {
        Optional.ofNullable(scoreboards.remove(player.getUniqueId())).ifPresent(Board::resetScoreboard);
        if (player.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        scoreboards.put(player.getUniqueId(), new Board(player, boardSettings));
    }

    private void remove(Player player) {
        Optional.ofNullable(scoreboards.remove(player.getUniqueId())).ifPresent(Board::remove);
    }

    public Map<UUID, Board> getScoreboards() {
        return Collections.unmodifiableMap(scoreboards);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (event.getPlayer().isOnline()) { // Set this up 2 ticks later.
                setup(event.getPlayer());
            }
        }, 2L);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.remove(event.getPlayer());
    }

    public void onDisable() {
        updateTask.cancel();
        plugin.getServer().getOnlinePlayers().forEach(this::remove);
        scoreboards.clear();
    }
}
