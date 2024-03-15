package it.fedet.mutility.bukkit.plugin.command.staff;

import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.bukkit.plugin.module.DisguiseModule;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.command.argument.CommandArgument;
import it.fedet.mutility.common.server.command.argument.impl.PlayerCommandArgument;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class RealNameCommand extends RegistrableCommand<Mutility> {

    private final DisguiseModule disguise;

    public RealNameCommand(Mutility plugin) {
        super(plugin, "realname");
        this.disguise = plugin.gestDisguiseModule();
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        Player target = (Player) args[0];


        if (!disguise.isDisguised(target)) {
            Chatify.sendMessage(player, "&cIl player non è disguisato!");
            return;
        }

        Chatify.sendMessage(player, "&aIl nome del player è: " + disguise.getRealName(target, true));
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        return List.of(
                new PlayerCommandArgument("&cSpecifica il nome del giocatore!")
        );
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
