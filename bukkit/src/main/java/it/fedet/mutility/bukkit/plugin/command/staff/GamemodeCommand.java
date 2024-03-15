package it.fedet.mutility.bukkit.plugin.command.staff;

import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.bukkit.plugin.command.arguments.GameModeArgument;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.command.argument.CommandArgument;
import it.fedet.mutility.common.server.command.argument.impl.PlayerCommandArgument;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.text.Palette;
import it.fedet.mutility.common.server.text.Textify;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GamemodeCommand extends RegistrableCommand<Mutility> {

    // gamemode / gm <gamemode> <player>

    public GamemodeCommand(Mutility plugin) {
        super(plugin, "gamemode", "gm");
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        if (!DefaultPermission.ADMIN.hasPermission(player) && !isDivinityHost(player)) {
            Chatify.sendMessage(player, Messages.ERROR.getMessage());
            return;
        }

        // gmx <gaemode>
        if (args.length == 1) {
            GameMode gameMode = (GameMode) args[0];
            Chatify.sendMessage(player, Textify.colorize(
                            "&5Staff » &dModalità di gioco impostata in &e" + Textify.firstLetterUpper(gameMode.name())
                    )
            );
            player.setGameMode(gameMode);
            return;
        }

        // gmx <gaemode> <player>
        if (args.length == 2) {
            GameMode gameMode = (GameMode) args[0];
            Player target = (Player) args[1];
            //System.out.println("Gamemode: " + gameMode.name() +  "\nN Arg: " + args.length + "\nTarget: " + args[1]);
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

    //TODO: CHE SCHIFO
    private boolean isDivinityHost(Player player) {

        return false;
    }

    @Override
    public String getPermission() {
        return DefaultPermission.ADMIN.get();
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.of(Messages.ERROR.getMessage());
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        List<CommandArgument<?>> arguments = new ArrayList<>();

        arguments.add(new GameModeArgument());
        arguments.add(new PlayerCommandArgument(Palette.ERROR + "Il giocatore non è online!"));

        return arguments;
    }

}
