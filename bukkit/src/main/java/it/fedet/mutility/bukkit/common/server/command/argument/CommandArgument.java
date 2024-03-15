package it.fedet.mutility.common.server.command.argument;


import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class CommandArgument<T> {

    private List<String> values = new ArrayList<>();

    /**
     * @param sender        That has executed the command
     * @param arguments     That has been passed on command
     * @param currentArgPos The current argument position
     * @return Validation status
     */
    public abstract boolean validate(CommandSender sender, String[] arguments, int currentArgPos);

    /**
     * @param sender        That has executed the command
     * @param arguments     That has been passed on command
     * @param currentArgPos The current argument position
     * @return The value of required type by class
     */
    public abstract T parse(CommandSender sender, String[] arguments, int currentArgPos);

    /**
     * @return The message send if validation fails
     */
    public abstract Optional<String> getErrorMessage();

    /**
     * @param values The values that will be display by command completer
     * @return The modified argument
     */
    public CommandArgument<T> replaceValues(String... values) {
        this.values = Arrays.asList(values);
        return this;
    }

    /**
     * @return The values that will be display by command completer
     */
    public List<String> getValues() {
        return values;
    }

}