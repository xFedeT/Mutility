package it.fedet.mutility.velocity.common.server.chat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.server.text.TextPlaceholder;
import it.fedet.mutility.velocity.common.server.text.Textify;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class Chatify {

    private Chatify() {
    }

    /**
     * @param message That will be sent
     */
    public static void broadcast(ProxyServer server, @NotNull Component message, TextPlaceholder... placeholders) {
        message = Textify.loadPlaceholders(message, placeholders);

        @NotNull Component finalMessage = message;
        server.getAllPlayers().forEach(online -> online.sendMessage(finalMessage));
    }

    /**
     * @param message   That will be sent
     * @param condition That player must have to receive the message
     */
    public static void conditionedBroadcast(ProxyServer server, @NotNull Component message, @NotNull Predicate<Player> condition, TextPlaceholder... placeholders) {
        message = Textify.loadPlaceholders(message, placeholders);

        @NotNull Component finalMessage = message;
        server.getAllPlayers()
                .stream()
                .filter(condition)
                .forEach(online -> online.sendMessage(finalMessage));
    }

    /**
     * @param message That will be sent
     */
    public static void sendMessage(@NotNull CommandSource sender, @NotNull Component message, TextPlaceholder... placeholders) {
        sender.sendMessage(Textify.loadPlaceholders(message, placeholders));
    }

    /**
     * @return The provided text with first letter in upper case
     */
    public static String firstUpper(String text) {
        text = text.toLowerCase();
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}