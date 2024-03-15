package it.fedet.mutility.common.server.player.spectator.gui;

import it.fedet.mutility.bukkit.plugin.Mutility;
import it.fedet.mutility.common.server.gui.InteractGui;
import it.fedet.mutility.common.server.gui.selector.GuiSelector;
import it.fedet.mutility.common.server.player.spectator.gui.teleport.TeleportPlayersGui;

import java.util.function.Function;

public enum SpectatorGuiSelector implements GuiSelector<Mutility> {

    TELEPORT(TeleportPlayersGui::new);

    public static final SpectatorGuiSelector[] VALUES = values();

    private final Function<Mutility, InteractGui<Mutility>> loader;
    private InteractGui<Mutility> interactGui;

    SpectatorGuiSelector(Function<Mutility, InteractGui<Mutility>> loader) {
        this.loader = loader;
    }

    @Override
    public void load(Mutility plugin) {
        interactGui = loader.apply(plugin);
    }

    @Override
    public InteractGui<Mutility> get() {
        return interactGui;
    }

}