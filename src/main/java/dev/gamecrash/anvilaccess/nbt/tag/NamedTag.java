package dev.gamecrash.anvilaccess.nbt.tag;


/**
 * A record for named tags, containing the name and tag itself
 */
public record NamedTag(String name, Tag<?> tag) { }