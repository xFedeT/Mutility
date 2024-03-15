package it.fedet.mutility.common.server.chat;

import it.fedet.mutility.common.server.text.Textify;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleLogger {

    private static final ConsoleCommandSender CONSOLE_SENDER = Bukkit.getConsoleSender();
    private static final String MESSAGE_PREFIX = "[MUtility] ";

    private ConsoleLogger() {
    }

    public static void error(String message, Throwable throwable) {
        CONSOLE_SENDER.sendMessage(Textify.colorize(MESSAGE_PREFIX + "&c" + message));
        CONSOLE_SENDER.sendMessage(Textify.colorize(MESSAGE_PREFIX + "&4" + throwable.getMessage()));
    }

    public static void info(String message) {
        CONSOLE_SENDER.sendMessage(Textify.colorize(MESSAGE_PREFIX + "&9" + message));
    }

    public static void warning(String message) {
        CONSOLE_SENDER.sendMessage(Textify.colorize(MESSAGE_PREFIX + "&e" + message));
    }

}