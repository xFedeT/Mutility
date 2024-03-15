package it.fedet.mutility.common.server.staffmode.playermode.fly;

import it.fedet.mutility.common.server.chat.Chatify;
import it.fedet.mutility.common.server.staffmode.playermode.PlayerMode;
import it.fedet.mutility.common.server.text.Textify;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class Fly extends PlayerMode {

    public enum Action implements PlayerMode.Action {

        ON(player -> {
            player.setAllowFlight(true);
            return true;
        }),
        OFF(player -> {
            player.setAllowFlight(false);
            return false;
        }),
        TOGGLE(player -> {
            if (player.getAllowFlight()) {
                Chatify.sendMessage(player, Textify.colorize(
                        "&5Fly » &dHai &cdisabilitato &dla modalità volo!"
                ));
                return OFF.apply(player);
            }

            Chatify.sendMessage(player, Textify.colorize(
                    "&5Fly » &dHai &aabilitato &dla modalità volo!"
            ));
            return ON.apply(player);
        });

        private final Function<Player, Boolean> action;

        Action(Function<Player, Boolean> action) {
            this.action = action;
        }

        @Override
        public Boolean apply(Player player) {
            return action.apply(player);
        }

    }
}