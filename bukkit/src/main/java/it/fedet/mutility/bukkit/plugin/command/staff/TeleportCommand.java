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
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeleportCommand extends RegistrableCommand<Mutility> {

    public TeleportCommand(Mutility plugin) {
        super(plugin, "teleport", "tp");
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        if (args.length == 1) {
            Player target = (Player) args[0];

            player.teleport(target.getLocation());
            Chatify.sendMessage(player,
                    Textify.colorize(
                            "&5Staff » &dTi sei teletrasportato presso "
                                    + "&e" + target.getName())
            );
            return;
        }

        if (args.length > 1) {
            Player targetOne = (Player) args[0];
            Player targetTwo = (Player) args[1];

            targetOne.teleport(targetTwo.getLocation());
            Chatify.sendMessage(player,
                    "&5Staff » &dHai teletrasportato &5 " + targetOne.getName() + " &dda &5" + targetTwo.getName()
            );
        }
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        String errorMessage = Palette.ERROR + "Il giocatore non è online!";

        List<CommandArgument<?>> arguments = new ArrayList<>();

        arguments.add(new PlayerCommandArgument(errorMessage));
        arguments.add(new PlayerCommandArgument(errorMessage));

        return arguments;
    }

    @Override
    public String getPermission() {
        return DefaultPermission.STAFF.get();
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.of(Messages.ERROR.getMessage());
    }
}