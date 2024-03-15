package it.fedet.mutility.bukkit.api.dependency.exception;

public class NoDependencyFound extends RuntimeException {

    public NoDependencyFound(String plugin) {
        super(plugin);
    }


}
