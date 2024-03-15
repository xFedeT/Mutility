package it.fedet.mutility.bukkit.message;

public enum Messages {

    ERROR("&d&lARCADIA§5§lMC &fComando sconosciuto. &e/help");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
