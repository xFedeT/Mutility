package it.fedet.mutility.bukkit.plugin.command.staff;

import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.staffmode.playermode.vanish.Vanish;
import it.fedet.mutility.common.util.ServerUtil;
import org.bukkit.entity.Player;

import java.util.Optional;

public class VanishCommand extends RegistrableCommand<Mutility> {

    public VanishCommand(Mutility plugin) {
        super(plugin, "vanish");
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        if (ServerUtil.isSwGame() && !player.isOp()) {
            Chatify.sendMessage(player, "&cNon puoi eseguire questo comando all'interno di una partita");
            return;
        }

        if(Vanish.Action.TOGGLE.apply(player)) {
            Chatify.sendMessage(player, "&3Staff » &bHai &aabilitato &bla modalità Vanish!");
        } else Chatify.sendMessage(player, "&3Staff » &bHai &cdisabilitato &bla modalità Vanish!");
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