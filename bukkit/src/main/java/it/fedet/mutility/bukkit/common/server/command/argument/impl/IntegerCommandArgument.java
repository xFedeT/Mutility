package it.fedet.mutility.common.server.command.argument.impl;

import it.fedet.mutility.common.server.command.argument.CommandArgument;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public class IntegerCommandArgument extends CommandArgument<Integer> {

    private final String errorMessage;
    private final Integer min;
    private final Integer max;

    public IntegerCommandArgument(String errorMessage, Integer min, Integer max) {
        this.errorMessage = errorMessage;
        this.min = min;
        this.max = max;
    }

    public IntegerCommandArgument(String errorMessage, Integer min) {
        this(errorMessage, min, null);
    }

    public IntegerCommandArgument(String errorMessage) {
        this(errorMessage, null, null);
    }

    @Override
    public boolean validate(CommandSender sender, String[] arguments, int currentArgPos) {
        if (NumberUtils.isNumber(arguments[currentArgPos])) {
            int value = Integer.parseInt(arguments[currentArgPos]);

            if (min != null && value < min)
                return false;

            return max == null || value <= max;
        }

        return false;
    }

    @Override
    public Integer parse(CommandSender sender, String[] arguments, int currentArgPos) {
        return Integer.parseInt(arguments[currentArgPos]);
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

}