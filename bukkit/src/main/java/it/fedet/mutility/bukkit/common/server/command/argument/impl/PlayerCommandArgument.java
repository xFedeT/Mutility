package it.fedet.mutility.common.server.command.argument.impl;

import it.fedet.mutility.common.server.command.argument.CommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class PlayerCommandArgument extends CommandArgument<Player> {

    private final String errorMessage;

    public PlayerCommandArgument(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean validate(CommandSender sender, String[] arguments, int currentArgPos) {
        return Bukkit.getPlayerExact(arguments[currentArgPos]) != null;
    }

    @Override
    public Player parse(CommandSender sender, String[] arguments, int currentArgPos) {
        return Bukkit.getPlayerExact(arguments[currentArgPos]);
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    @Override
    public List<String> getValues() {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();
    }

}