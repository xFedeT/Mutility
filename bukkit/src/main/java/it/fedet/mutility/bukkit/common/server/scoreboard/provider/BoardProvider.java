package it.fedet.mutility.common.server.scoreboard.provider;

import org.bukkit.entity.Player;

import java.util.List;

public interface BoardProvider {

    /**
     * Retrieves the title for the objective to be displayed on the scoreboard.
     *
     * @param player The player for whom to supply the title.
     * @return The title for the objective.
     */
    String getTitle(Player player);

    /**
     * Retrieves the list of contents (lines) for the objective to be displayed on the scoreboard.
     *
     * @param player The player for whom to supply the list of contents.
     * @return The list of contents (lines) for the objective.
     */
    List<String> getLines(Player player);
}
