package it.fedet.mutility.common.server.chat;

import it.fedet.mutility.common.server.text.TextPlaceholder;
import it.fedet.mutility.common.server.text.Textify;
import it.fedet.mutility.common.util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

import static it.fedet.mutility.common.server.text.Textify.colorize;

public class Chatify {

    private Chatify() {
    }

    /**
     * @param message That will be sent
     */
    public static void broadcast(@NotNull String message, TextPlaceholder... placeholders) {
        if (message.isEmpty()) return;

        Bukkit.broadcastMessage(colorize(Textify.loadPlaceholders(message, placeholders)));
    }

    /**
     * @param message   That will be sent
     * @param condition That player must have to receive the message
     */
    public static void conditionedBroadcast(@NotNull String message, @NotNull Predicate<Player> condition, TextPlaceholder... placeholders) {
        if (message.isEmpty()) return;

        String finalMessage = colorize(Textify.loadPlaceholders(message, placeholders));

        Bukkit.getOnlinePlayers()
                .stream()
                .filter(condition)
                .forEach(player -> player.sendMessage(finalMessage));
    }

    /**
     * @param message That will be sent
     */
    public static void sendMessage(@NotNull CommandSender sender, @NotNull String message, TextPlaceholder... placeholders) {
        if (message.isEmpty()) return;

        sender.sendMessage(colorize(Textify.loadPlaceholders(message, placeholders)));
    }

    /**
     * @param titleMessage    Message that will be used as title
     * @param subTitleMessage Message that will be used as subtitle
     */
    public static void titleBroadCast(String titleMessage, @Nullable String subTitleMessage) {
        titleBroadCast(titleMessage, subTitleMessage, 20, 200, 20);
    }

    /**
     * @param titleMessage    Message that will be used as title
     * @param subTitleMessage Message that will be used as subtitle
     * @param fadeIn          That will be sent
     * @param stay            That will be sent
     * @param fadeOut         That will be sent
     */
    public static void titleBroadCast(String titleMessage, @Nullable String subTitleMessage, int fadeIn, int stay, int fadeOut) {
        Title title = new Title(
                Textify.colorize(titleMessage), Textify.colorize(subTitleMessage),
                fadeIn, stay, fadeOut
        );


        Bukkit.getOnlinePlayers().stream()
                .filter(online -> ServerUtil.getVersion(online) > 5)
                .forEach(online -> online.sendTitle(title));
    }

}