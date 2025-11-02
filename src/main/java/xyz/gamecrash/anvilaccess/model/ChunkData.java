package xyz.gamecrash.anvilaccess.model;

/**
 * Represents the raw chunk data of an MCA file
 */
public record ChunkData(int length, byte compressionType, byte[] data) {
}
