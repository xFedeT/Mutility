package it.fedet.mutility.common.server.command.argument.impl;

import it.fedet.mutility.common.server.command.argument.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Optional;

public class TextCommandArgument extends CommandArgument<String> {

    private final String errorMessage;

    public TextCommandArgument(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TextCommandArgument() {
        this(null);
    }

    @Override
    public boolean validate(CommandSender sender, String[] arguments, int currentArgPos) {
        return true;
    }

    @Override
    public String parse(CommandSender sender, String[] arguments, int currentArgPos) {
        return String.join(" ", Arrays.copyOfRange(arguments, currentArgPos, arguments.length - 1));
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

}