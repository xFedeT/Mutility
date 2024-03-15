package it.fedet.mutility.bukkit.plugin.command.staff;

import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.staffmode.playermode.staffmode.StaffMode;
import it.fedet.mutility.common.util.ServerUtil;
import org.bukkit.entity.Player;

import java.util.Optional;

public class StaffModeCommand extends RegistrableCommand<Mutility> {

    public StaffModeCommand(Mutility plugin) {
        super(plugin, "staffmode", "sm", "sf", "staff");
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        if (ServerUtil.isSwGame()) {
            Chatify.sendMessage(player, "&cNon puoi eseguire questo comando all'interno di una partita");
            return;
        }

        StaffMode.StaffAction.TOGGLE.apply(plugin, player);
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