package xyz.gamecrash.AnvilAccess.nbt.tags;

/**
 * A record for named tags, containing the name and tag itself
 */
public record NamedTag(String name, Tag tag) { }