package xyz.gamecrash.anvilaccess.world;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class WorldEntityManager {
    private final Path worldDirectory;
    private final Path entityDirectory;

    public WorldEntityManager(Path worldDirectory) {
        this.worldDirectory = worldDirectory;
        entityDirectory = worldDirectory.resolve("entities");

        if (!Files.exists(entityDirectory))
            throw new IllegalArgumentException("Entities directory does not exist: " + entityDirectory);
    }


}
