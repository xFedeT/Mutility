package it.fedet.mutility.common.server.scoreboard.board;

import it.fedet.mutility.common.server.scoreboard.settings.BoardSettings;
import it.fedet.mutility.common.server.scoreboard.settings.ScoreDirection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private static final String[] CACHED_ENTRIES = new String[ChatColor.values().length];

    private static final Function<String, String> APPLY_COLOR_TRANSLATION = s -> ChatColor.translateAlternateColorCodes('&', s);

    static {
        IntStream.range(0, 15).forEach(i -> CACHED_ENTRIES[i] = ChatColor.values()[i].toString() + ChatColor.RESET);
    }

    private final Player player;
    private final Objective objective;
    private final Team team;

    private BoardSettings boardSettings;
    private boolean ready;

    public Board(@NonNull final Player player, final BoardSettings boardSettings) {
        this.player = player;
        this.boardSettings = boardSettings;
        this.objective = this.getScoreboard().getObjective("board") == null ? this.getScoreboard().registerNewObjective("board", "dummy") : this.getScoreboard().getObjective("board");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.team = this.getScoreboard().getTeam("board") == null ? this.getScoreboard().registerNewTeam("board") : this.getScoreboard().getTeam("board");
        this.team.setAllowFriendlyFire(true);
        this.team.setCanSeeFriendlyInvisibles(false);
        this.team.setPrefix("");
        this.team.setSuffix("");
        this.ready = true;
    }

    public Scoreboard getScoreboard() {
        return (player != null) ? player.getScoreboard() : null;
    }

    public void remove() {
        this.resetScoreboard();
    }

    public void update() {
        // Checking if we are ready to start updating the Scoreboard.
        if (!ready) {
            return;
        }

        // Making sure the player is connected.
        if (!player.isOnline()) {
            remove();
            return;
        }

        // Making sure the Scoreboard Provider is set.
        if (boardSettings == null) {
            return;
        }

        // Getting their Scoreboard display from the Scoreboard Provider.
        final List<String> entries = boardSettings.getBoardProvider().getLines(player).stream().map(APPLY_COLOR_TRANSLATION).collect(Collectors.toList());

        if (boardSettings.getScoreDirection() == ScoreDirection.UP) {
            Collections.reverse(entries);
        }

        // Setting the Scoreboard title
        String title = boardSettings.getBoardProvider().getTitle(player);
        if (title.length() > 32) {
            Bukkit.getLogger().warning("The title " + title + " is over 32 characters in length, substringing to prevent errors.");
            title = title.substring(0, 32);
        }
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));

        // Clearing previous Scoreboard values if entry sizes don't match.
        if (this.getScoreboard().getEntries().size() != entries.size())
            this.getScoreboard().getEntries().forEach(this::removeEntry);

        // Setting Scoreboard lines.
        for (int i = 0; i < entries.size(); i++) {
            String str = entries.get(i);
            BoardEntry entry = BoardEntry.translateToEntry(str);
            Team team = getScoreboard().getTeam(CACHED_ENTRIES[i]);

            if (team == null) {
                team = this.getScoreboard().registerNewTeam(CACHED_ENTRIES[i]);
                team.addEntry(team.getName());
            }

            team.setPrefix(entry.getPrefix());
            team.setSuffix(entry.getSuffix());

            switch (boardSettings.getScoreDirection()) {
                case UP:
                    objective.getScore(team.getName()).setScore(1 + i);
                    break;
                case DOWN:
                    objective.getScore(team.getName()).setScore(15 - i);
                    break;
            }
        }
    }

    public void removeEntry(String id) {
        this.getScoreboard().resetScores(id);
    }

    public void resetScoreboard() {
        ready = false;
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public void setBoardSettings(BoardSettings boardSettings) {
        this.boardSettings = boardSettings;
    }
}
