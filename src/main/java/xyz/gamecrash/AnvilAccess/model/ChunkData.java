package xyz.gamecrash.AnvilAccess.model;

/**
 * Represents the chunk data of an MCA file
 * @param data the raw chunk data
 * @param length the chunk data length
 * @param compressionType the type of compression used (view CompressionType for types)
 */
public record ChunkData(int length, byte compressionType, byte[] data) { }
