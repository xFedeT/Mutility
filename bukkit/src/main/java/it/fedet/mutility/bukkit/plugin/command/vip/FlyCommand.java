package it.fedet.mutility.bukkit.plugin.command.vip;

import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import it.fedet.mutility.common.server.staffmode.playermode.fly.Fly;
import it.fedet.mutility.common.util.ServerUtil;
import org.bukkit.entity.Player;

import java.util.Optional;

public class FlyCommand extends RegistrableCommand<Mutility> {

    public FlyCommand(Mutility plugin) {
        super(plugin, "fly");
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        if (ServerUtil.isSwGame() && !player.isOp()) {
            Chatify.sendMessage(player, "&cNon puoi eseguire questo comando all'interno di una partita");
            return;
        }

        Fly.Action.TOGGLE.apply(player);
    }

    @Override
    public String getPermission() {
        return DefaultPermission.VIP.get();
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.of(Messages.ERROR.getMessage());
    }
}