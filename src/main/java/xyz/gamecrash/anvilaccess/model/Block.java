package xyz.gamecrash.anvilaccess.model;

/**
 * Immutable representation of a Minecraft block with a {@link BlockState} and its coordinates
 */
public record Block(BlockState state, int x, int y, int z) {

    public Block {
        if (state == null) throw new IllegalArgumentException("BlockState must not be null");
    }

    /**
     * Get the ID of the block (e.g. "minecraft:air")
     *
     * @return the Block-ID
     */
    public String getId() {
        return state.id();
    }

    /**
     * Gets a property from the block state, e.g. the facing of a furnace
     */
    public String getProperty(String key) {
        return state.getProperty(key);
    }

    /**
     * Gets a property from the block state, with a default value if the property is not set
     */
    public String getProperty(String key, String defaultValue) {
        return state.getProperty(key, defaultValue);
    }

    @Override
    public String toString() {
        return String.format("%s(%d;%d;%d)", state, x, y, z);
    }
}
