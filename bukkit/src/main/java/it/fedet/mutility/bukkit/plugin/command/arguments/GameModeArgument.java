package it.fedet.mutility.bukkit.plugin.command.arguments;


import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.common.server.command.argument.CommandArgument;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

import java.util.*;

public class GameModeArgument extends CommandArgument<GameMode> {

    private static final Map<String, GameMode> gamemodes = new HashMap<>();

    static {
        gamemodes.put("0", GameMode.SURVIVAL);
        gamemodes.put("1", GameMode.CREATIVE);
        gamemodes.put("2", GameMode.ADVENTURE);
        gamemodes.put("3", GameMode.SPECTATOR);

        for (GameMode gameMode : GameMode.values())
            gamemodes.put(gameMode.name(), gameMode);
    }

    private final List<String> values;

    public GameModeArgument() {
        values = new ArrayList<>(gamemodes.keySet());
    }

    @Override
    public boolean validate(CommandSender sender, String[] strings, int i) {
        return gamemodes.get(strings[i].toUpperCase()) != null;
    }

    @Override
    public GameMode parse(CommandSender sender, String[] strings, int i) {
        return gamemodes.get(strings[i].toUpperCase());
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.of(Messages.ERROR.getMessage());
    }

    @Override
    public List<String> getValues() {
        return values;
    }

}