package xyz.gamecrash.anvilaccess.model.entities;

import lombok.Getter;
import xyz.gamecrash.anvilaccess.model.Section;
import xyz.gamecrash.anvilaccess.nbt.TagType;
import xyz.gamecrash.anvilaccess.nbt.tags.CompoundTag;
import xyz.gamecrash.anvilaccess.nbt.tags.ListTag;

import java.lang.annotation.ElementType;
import java.util.*;

@Getter
public class EntityChunk {
    private final int chunkX;
    private final int chunkZ;
    private final CompoundTag nbt;
    private final int dataVersion;
    private final List<Entity> entities = new ArrayList<>();

    public EntityChunk(int chunkX, int chunkZ, CompoundTag nbt) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.nbt = nbt;
        dataVersion = nbt.getInt("DataVersion", 0);

        ListTag entityList = nbt.getList("Entities", null);
        if (entityList == null) return;
        if (entityList.getElementType() != TagType.COMPOUND) throw new IllegalArgumentException("Invalid entity chunk");
        entityList.iterator().forEachRemaining(t -> {
            entities.add(new Entity((CompoundTag) t.getValue()));
        });
    }
}
