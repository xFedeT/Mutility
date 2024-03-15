package it.fedet.mutility.bukkit.plugin.command.vip;

import it.fedet.mutility.bukkit.message.Messages;
import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.bukkit.plugin.module.DisguiseModule;
import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.command.RegistrableCommand;
import it.fedet.mutility.common.server.player.permission.impl.DefaultPermission;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DisguiseCommand extends RegistrableCommand<Mutility> {
    private final DisguiseModule disguise;

    public DisguiseCommand(Mutility plugin) {
        super(plugin, "disguise");
        this.disguise = plugin.gestDisguiseModule();
    }

    @Override
    public void playerExecutor(Player player, Object[] args) {
        if(!disguise.isDisguised(player)) {
            disguise.addRedis(player);
            disguise.addDisguise(player);

            Chatify.sendMessage(player, "&5Vip » &dHai &aabilitato &dil disguise!");
        } else {
            disguise.removeRedis(player);
            disguise.removeDisguise(player);
            Chatify.sendMessage(player, "&5Vip » &dHai &cdisabilitato &dil disguise!");
        }

    }

    @Override
    public String getPermission() {
        return DefaultPermission.DISGUISE.get();
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.of(Messages.ERROR.getMessage());
    }
}
