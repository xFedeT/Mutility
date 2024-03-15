package it.fedet.mutility.common.server.text;

import org.bukkit.ChatColor;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Textify {

    private Textify() {
    }

    public static String colorize(String text) {
        if (text == null)
            return "";

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String generateSpaces(int spaces) {
        return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
    }

    public static String loadPlaceholders(String text, TextPlaceholder... placeholders) {
        for (TextPlaceholder placeholder : placeholders)
            text = text.replace(placeholder.getPlaceholder(), placeholder.getValue());

        return text;
    }

    public static List<String> loadPlaceholders(List<String> texts, TextPlaceholder... placeholders) {
        List<String> result = new ArrayList<>();

        for (String text : texts)
            result.add(loadPlaceholders(text, placeholders));

        return result;
    }

    /**
     * @return The provided text with first letter in upper case
     */
    public static String firstLetterUpper(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static String allFirstLettersUpper(String text) {
        return String.join(
                " ",
                Arrays.stream(text.split(" "))
                        .map(Textify::firstLetterUpper)
                        .toArray(String[]::new)
        );
    }

}