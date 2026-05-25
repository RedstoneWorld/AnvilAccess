package xyz.gamecrash.anvilaccess.model.entities;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.model.Section;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class EntityChunk {
    private final int chunkX;
    private final int chunkZ;
    private final CompoundTag nbt;
    private final int dataVersion;

    public EntityChunk(int chunkX, int chunkZ, CompoundTag nbt) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.nbt = nbt;
        dataVersion = nbt.getInt("DataVersion", 0);


    }
}
