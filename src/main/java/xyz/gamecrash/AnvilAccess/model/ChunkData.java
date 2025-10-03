package xyz.gamecrash.AnvilAccess.model;

public record ChunkData(int length, byte compressionType, byte[] data) { }
