package xyz.gamecrash.AnvilAccess.model;

public record Block(BlockState state, int x, int y, int z) {

    public Block { if (state == null) throw new IllegalArgumentException("BlockState must not be null"); }

    public String getId() { return state.id(); }

    public String getProperty(String key) { return state.getProperty(key); }

    public String getProperty(String key, String defaultValue) { return state.getProperty(key, defaultValue); }

    @Override
    public String toString() { return String.format("%s(%d;%d;%d)", state, x, y, z); }
}
