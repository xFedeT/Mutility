package it.fedet.mutility.velocity.common.server.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class Textify {

    private Textify() {
    }

    public static String generateSpaces(int spaces) {
        return CharBuffer.allocate(spaces).toString().replace('\0', ' ');
    }

    //TODO: Si puo' fixare
    public static Component loadPlaceholders(Component text, TextPlaceholder... placeholders) {
        for (TextPlaceholder placeholder : placeholders)
            text = text.replaceText(builder ->
                    builder.match(placeholder.getPlaceholder())
                            .replacement(placeholder.getValue())
            );

        return text;
    }

    public static List<Component> loadPlaceholders(List<Component> texts, TextPlaceholder... placeholders) {
        List<Component> result = new ArrayList<>();

        for (Component text : texts)
            result.add(loadPlaceholders(text, placeholders));

        return result;
    }

    public static Component colorize(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

}