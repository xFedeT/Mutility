package it.fedet.mutility.common.server.command.argument.impl;

import it.fedet.mutility.common.server.command.argument.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class StringCommandArgument extends CommandArgument<String> {

    private final String errorMessage;
    private final Integer maxLength;

    public StringCommandArgument(String errorMessage, Integer maxLength) {
        this.errorMessage = errorMessage;
        this.maxLength = maxLength;
    }

    public StringCommandArgument(String errorMessage) {
        this(errorMessage, null);
    }

    public StringCommandArgument() {
        this(null, null);
    }

    @Override
    public boolean validate(CommandSender sender, String[] arguments, int currentArgPos) {
        if (maxLength != null && arguments[currentArgPos].length() > maxLength)
            return false;

        return getValues().isEmpty() || getValues().contains(arguments[currentArgPos].toLowerCase());
    }

    @Override
    public String parse(CommandSender sender, String[] arguments, int currentArgPos) {
        return arguments[currentArgPos];
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

}