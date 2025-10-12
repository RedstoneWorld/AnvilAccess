package xyz.gamecrash.AnvilAccess.model;

/**
 * Represents the raw chunk data of a MCA file
 */
public record ChunkData(int length, byte compressionType, byte[] data) { }
