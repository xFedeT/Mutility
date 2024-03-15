package it.fedet.mutility.velocity.common.server.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import it.fedet.mutility.velocity.common.server.chat.Chatify;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class RegistrableCommand implements SimpleCommand {

    protected final String name;
    protected final String[] aliases;

    private final Map<UUID, Long> delayMap = new HashMap<>();

    protected RegistrableCommand(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public void register(ProxyServer server) {
        server.getCommandManager().register(name, this, aliases);
    }

    @Override
    public void execute(Invocation invocation) {
        if (invocation.source() instanceof Player player) {
            if (!delayMap.containsKey(player.getUniqueId())) {
                onExecute(invocation);
                return;
            }
            if (System.currentTimeMillis() - delayMap.get(player.getUniqueId()) < getDelay() * 1000L) {
                Chatify.sendMessage(player, onDelay(invocation));
                return;
            }

            delayMap.remove(player.getUniqueId());
        }

        onExecute(invocation);
    }

    public abstract void onExecute(Invocation invocation);
    public Component onDelay(Invocation invocation) {
        return Component.text("");
    }

    public void addDelay(Player player) {
        delayMap.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public int getDelay() {
        return 0;
    }

}