package it.fedet.mutility.common.server.command.handler;

import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.command.argument.CommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CommandHandler<T extends BasePlugin> extends BukkitCommand {

    protected CommandHandler(String name) {
        super(name);
    }

    protected CommandHandler(String name, List<String> aliases) {
        super(name, "", "", aliases);
    }

    private boolean hasPermission(RegistrableCommand<T> command, CommandSender sender) {
        if (!command.getPermission().isEmpty() && !sender.hasPermission(command.getPermission())) {
            if (sender instanceof Player)
                command.getErrorMessage().ifPresent(message -> Chatify.sendMessage(sender, message));

            return false;
        }

        return true;
    }

    private void handleSubcommands(RegistrableCommand<T> command, CommandSender sender, String[] args, int pos) {
        //   cmd      subcmd[0]     subcmd[1]
        // arcadia    <about>    <server/discord/boh>

        for (RegistrableCommand<T> subcommand : command.getSubcommands())
            if (pos == 0 || (args.length == pos && args[pos - 1].equalsIgnoreCase(command.getName())))
                handleCommand(subcommand, sender, args, pos + 1);
    }

    public void handleCommand(RegistrableCommand<T> command, CommandSender sender, String[] args, int pos) {
        if (!hasPermission(command, sender)) return;

        handleSubcommands(command, sender, args, pos);

        // pos 0         0         0
        //   cmd       arg1      arg2
        // gamemode <adventure> <player>

        if (pos > 0 && !args[pos - 1].equalsIgnoreCase(command.getName())) return;
        if (args.length - pos > command.getArguments().size()) return;

        Object[] arguments = loadArguments(command, sender, args, pos);
        if (args.length - pos != 0 && arguments.length == 0) return;

        command.generalExecutor(sender, arguments);

        if (!(sender instanceof Player)) return;
        command.playerExecutor((Player) sender, arguments);
    }

    public Object[] loadArguments(RegistrableCommand<T> command, CommandSender sender, String[] args, int pos) {
        List<CommandArgument<?>> commandArguments = command.getArguments();
        Object[] arguments = new Object[args.length - pos];

        for (int i = 0; i < args.length - pos; i++) {
            CommandArgument<?> argument = commandArguments.get(i);
            int argPos = pos + i;

            if (!argument.validate(sender, args, argPos)) {
                argument.getErrorMessage().ifPresent(message -> Chatify.sendMessage(sender, message));
                return new Object[0];
            }

            arguments[i] = argument.parse(sender, args, argPos);
        }

        return arguments;
    }

}