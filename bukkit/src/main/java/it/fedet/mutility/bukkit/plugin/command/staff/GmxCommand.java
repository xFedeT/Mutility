package it.fedet.mutility.bukkit.plugin.command.staff;

import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.command.argument.CommandArgument;
import it.fedet.mutility.common.server.command.argument.impl.PlayerCommandArgument;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.text.Palette;
import it.fedet.mutility.common.server.text.Textify;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.*;

public class GmxCommand extends RegistrableCommand<Mutility> {

    private static final Map<String, GameMode> gamemodes = new HashMap<>();

    static {
        gamemodes.put("gms", GameMode.SURVIVAL);
        gamemodes.put("gmc", GameMode.CREATIVE);
        gamemodes.put("gma", GameMode.ADVENTURE);
        gamemodes.put("gmsp", GameMode.SPECTATOR);
    }

    private final GameMode gameMode;

    public GmxCommand(Mutility plugin, String name) {
        super(plugin, name);

        gameMode = gamemodes.get(name);
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        // gmx
        if (args.length == 0) {
            player.setGameMode(gameMode);
            Chatify.sendMessage(player, Textify.colorize(
                            "&5Staff » &dModalità di gioco impostata in &e" + Textify.firstLetterUpper(gameMode.name())
                    )
            );
            return;
        }

        // gmx <player>
        if (args.length == 1) {
            Player target = (Player) args[0];

            target.setGameMode(gameMode);
            Chatify.sendMessage(player,
                    Textify.colorize(
                            "&5Staff » &dModalità di gioco impostata in &e"
                                    + gameMode.name().toLowerCase()
                                    + "&d a "
                                    + Palette.PRIMARY + target.getName()
                    )
            );
        }
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        List<CommandArgument<?>> arguments = new ArrayList<>();

        arguments.add(new PlayerCommandArgument(Palette.ERROR + "Il giocatore non è online!"));

        return arguments;
    }

    @Override
    public String getPermission() {
        return DefaultPermission.ADMIN.get();
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.of(Messages.ERROR.getMessage());
    }
}