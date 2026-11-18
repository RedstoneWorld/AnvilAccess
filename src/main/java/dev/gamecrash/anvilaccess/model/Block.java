package dev.gamecrash.anvilaccess.model;

public record Block(BlockState state, int x, int y, int z) {
    public String id() {
        return state.id();
    }

    public String property(String key) {
        return state.property(key);
    }

    public String property(String key, String defaultValue) {
        return state.property(key, defaultValue);
    }

    @Override
    public String toString() {
        return String.format("%s(%d;%d;%d)", state, x, y, z);
    }
}
