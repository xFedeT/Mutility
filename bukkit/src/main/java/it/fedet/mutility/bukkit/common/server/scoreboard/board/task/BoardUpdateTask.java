package it.fedet.mutility.common.server.scoreboard.board.task;

import it.fedet.mutility.common.server.scoreboard.BoardManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.function.Predicate;

public class BoardUpdateTask extends BukkitRunnable {

    private static final Predicate<UUID> PLAYER_IS_ONLINE = uuid -> Bukkit.getPlayer(uuid) != null;

    public BoardUpdateTask(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    private final BoardManager boardManager;

    @Override
    public void run() {
        boardManager.getScoreboards().entrySet().stream().filter(entrySet -> PLAYER_IS_ONLINE.test(entrySet.getKey())).forEach(entrySet -> entrySet.getValue().update());
    }
}
