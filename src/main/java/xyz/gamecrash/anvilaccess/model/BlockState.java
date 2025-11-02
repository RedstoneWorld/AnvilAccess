package xyz.gamecrash.anvilaccess.model;

import java.util.Map;

/**
 * Representation of a block state, including its ID and properties. (Immutable)
 */
public record BlockState(String id, Map<String, String> properties) {

    public BlockState {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Block ID must not be null or blank");
        properties = properties == null ? Map.of() : Map.copyOf(properties);
    }

    /**
     * Creates a BlockState with no properties
     */
    public BlockState(String id) {
        this(id, Map.of());
    }

    /**
     * Gets a property from the block state, e.g. the facing of a furnace
     */
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Gets a property from the block state, with a default value if the property is not set
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    @Override
    public String toString() {
        if (properties.isEmpty()) return id;

        StringBuilder builder = new StringBuilder(id);
        builder.append("[");
        properties.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> builder.append(entry.getKey()).append("=").append(entry.getValue()).append(",")
            );
        builder.setCharAt(builder.length() - 1, ']');

        return builder.toString();
    }
}
