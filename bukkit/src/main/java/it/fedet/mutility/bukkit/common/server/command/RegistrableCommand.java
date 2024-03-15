package it.fedet.mutility.common.server.command;

import com.google.common.collect.Lists;
import it.fedet.mutility.common.plugin.BasePlugin;
import it.fedet.mutility.common.server.chat.ConsoleLogger;
import it.fedet.mutility.common.server.command.argument.CommandArgument;
import it.fedet.mutility.common.server.command.completer.CommandCompleter;
import it.fedet.mutility.common.server.command.handler.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class RegistrableCommand<T extends BasePlugin> extends CommandHandler<T> implements CommandCompleter<T> {

    private static CommandMap commandMap;
    protected final String name;
    protected final T plugin;

    static {
        try {
            Field commandMapFiled = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapFiled.setAccessible(true);
            commandMap = (CommandMap) commandMapFiled.get(Bukkit.getServer());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    protected RegistrableCommand(T plugin, String name) {
        super(name);
        this.plugin = plugin;
        this.name = name;
    }

    protected RegistrableCommand(T plugin, String name, List<String> aliases) {
        super(name, aliases);
        this.plugin = plugin;
        this.name = name;
    }

    protected RegistrableCommand(T plugin, String name, String... aliases) {
        this(plugin, name, Arrays.asList(aliases));
    }

    public void register(JavaPlugin plugin) {
        if (commandMap.register(plugin.getName(), this))
            return;

        ConsoleLogger.warning("Unable to register (" + name + ") command!");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        handleCommand(this, sender, args, 0);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return handleTabComplete(this, sender, args, 0);
    }

    /**
     * @param player That has executed the command
     * @param args   That have been returned in getArguments method
     */
    @SuppressWarnings("EmptyMethod")
    public void playerExecutor(Player player, Object[] args) {
    }

    /**
     * @param sender That has executed the command
     * @param args   That have been returned in getArguments method
     */
    @SuppressWarnings("EmptyMethod")
    public void generalExecutor(CommandSender sender, Object[] args) {
    }

    /**
     * @return The list of sub commands
     */
    public List<RegistrableCommand<T>> getSubcommands() {
        return Lists.newArrayList();
    }

    /**
     * @return The list of arguments that will be passed to playerExecutor and generalExecutor
     */
    public List<CommandArgument<?>> getArguments() {
        return Lists.newArrayList();
    }

    /**
     * @return The permission require to perform the command
     */
    @Override
    public String getPermission() {
        return "";
    }

    /**
     * @return The message to send if player miss the required permission
     */
    public Optional<String> getErrorMessage() {
        return Optional.empty();
    }

}