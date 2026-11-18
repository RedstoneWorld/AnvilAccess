package dev.gamecrash.anvilaccess.model;

import java.util.Map;
import java.util.TreeMap;

public record BlockState(String id, Map<String, String> properties) {
    public BlockState(String id) {
        this(id, Map.of());
    }

    public String property(String key) {
        return properties.get(key);
    }

    public String property(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    @Override
    public String toString() {
        if (properties.isEmpty()) return id;
        StringBuilder sb = new StringBuilder(id).append("[");
        new TreeMap<>(properties).forEach((k, v) -> sb.append(k).append("=").append(v).append(","));
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }
}
