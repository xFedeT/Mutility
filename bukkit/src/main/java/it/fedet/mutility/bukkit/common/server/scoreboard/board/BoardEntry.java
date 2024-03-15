package it.fedet.mutility.common.server.scoreboard.board;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

public class BoardEntry {
    private final String prefix;
    private final String suffix;

    private BoardEntry(final String prefix, final String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public static BoardEntry translateToEntry(String input) {
        if (input.isEmpty()) {
            return new BoardEntry("", "");
        }
        if (input.length() <= 16) {
            return new BoardEntry(input, "");
        } else {
            String prefix = input.substring(0, 16);
            String suffix = "";

            if (prefix.endsWith("§")) {
                prefix = prefix.substring(0, prefix.length() - 1);
                suffix = "§" + suffix;
            }

            suffix = StringUtils.left(ChatColor.getLastColors(prefix) + suffix + input.substring(16), 16);
            return new BoardEntry(prefix, suffix);
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
