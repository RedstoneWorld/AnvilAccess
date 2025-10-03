package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter @RequiredArgsConstructor
public class Chunk {
    private final int chunkX;
    private final int chunkZ;
    private final List<Section> sections;

    public Block getBlock(int x, int y, int z) {
        int lX = x & 15;
        int lZ = z & 15;

        int sectionY = y >> 4;

        Optional<Section> section = sections.stream()
            .filter(s -> s.getYIndex() == sectionY)
            .findFirst();

        if (section.isEmpty()) return new Block(new BlockState("minecraft:air"), x, y, z);

        int lY = y & 15;
        BlockState state = section.get().getBlockState(lX, lY, lZ);

        return new Block(state, x, y, z);
    }

    public Block getLocalBlock(int localX, int y, int localZ) {
        int wX = chunkX << 4 + localX;
        int wZ = chunkZ << 4 + localZ;

        return getBlock(wX, y, wZ);
    }

    public Optional<Section> getSection(int y) {
        int sectionY = y >> 4;

        return sections.stream()
            .filter(s -> s.getYIndex() == sectionY)
            .findFirst();
    }

    public int getMinY() {
        return sections.stream()
            .mapToInt(Section::getYIndex)
            .min()
            .orElse(0);
    }

    public int getMaxY() {
        return sections.stream()
            .mapToInt(s -> s.getAbsoluteY() + 15)
            .max()
            .orElse(255);
    }

    public int getWorldX() { return chunkX << 4; }

    public int getWorldZ() { return chunkZ << 4; }

    @Override
    public String toString() { return String.format("Chunk(%d;%d)[Sections:%d]", chunkX, chunkZ, sections.size()); }
}
