package it.fedet.mutility.common.server.command.completer;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.command.argument.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface CommandCompleter<T extends BasePlugin> {

    default List<String> handlerSubcommands(RegistrableCommand<T> command, CommandSender sender, String[] args, int pos) {
        List<String> result = new ArrayList<>();

        //   cmd      subcmd[0]     subcmd[1]
        // arcadia    <about>    <server/discord/boh>

        for (RegistrableCommand<T> subcommand : command.getSubcommands())
            if (pos == 0 || args.length >= pos && args[pos - 1].equalsIgnoreCase(command.getName()))
                result.addAll(handleTabComplete(subcommand, sender, args, pos + 1));

        return result;
    }

    default List<String> handleTabComplete(RegistrableCommand<T> command, CommandSender sender, String[] args, int pos) {
        if (!command.getPermission().isEmpty() && !sender.hasPermission(command.getPermission()))
            return Collections.emptyList();

        List<String> tabArguments = handlerSubcommands(command, sender, args, pos);

        // pos 0         0         0
        //   cmd       arg1      arg2
        // gamemode <adventure> <player>

        if (pos > 0 && !args[pos - 1].equalsIgnoreCase(command.getName()))
            return Collections.emptyList();
        if (args.length - pos > command.getArguments().size())
            return Collections.emptyList();

        List<String> arguments = command.getArguments().get(args.length - pos - 1).getValues();
        if (args.length - pos != 0 && arguments.isEmpty())
            return Collections.emptyList();

        tabArguments.addAll(arguments);

        return handleArguments(tabArguments, args);
    }

    default List<String> handleArguments(List<String> arguments, String[] args) {
        List<String> result = new ArrayList<>();

        for (String argument : arguments) {
            if (argument.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                result.add(argument);
                continue;
            }

            result.remove(argument);
        }

        return result;
    }

    default List<String> loadArguments(RegistrableCommand<T> command, String[] args, int pos) {
        List<CommandArgument<?>> commandArguments = command.getArguments();
        List<String> result = new ArrayList<>();

        for (int i = 0; i < args.length - pos; i++) {
            CommandArgument<?> argument = commandArguments.get(i);

            result.addAll(argument.getValues());
        }

        return result;
    }

}