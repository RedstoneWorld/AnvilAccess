package xyz.gamecrash.anvilaccess.util.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * Util class for hashing operations
 */
public class HashUtils {

    /**
     * Generates a SHA-256 hash of the given input
     */
    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * Generates a SHA-256 hash of the given input
     */
    public static String sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input);
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * Generates a MD5 hash of the given input
     */
    public static String md5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * Generates a crc32 hash of the given input
     */
    public static String crc32(byte[] input) {
        CRC32 crc = new CRC32();
        crc.update(input);
        return Long.toHexString(crc.getValue());
    }

    /**
     * Generates a crc32 hash of the given input
     */
    public static String crc32(String input) {
        return crc32(input.getBytes());
    }

    /**
     * Converts byte array to a hex string.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) result.append(String.format("%02x", b));
        return result.toString();
    }

    /**
     * Generates a simple hash code for a long array.
     */
    public static int hashLongArray(long[] array) {
        if (array == null) return 0;

        int result = 1;
        for (long element : array) {
            int elementHash = Long.hashCode(element);
            result = 31 * result + elementHash;
        }
        return result;
    }

    /**
     * Generates a fast hash for byte arrays.
     */
    public static int fastHash(byte[] data) {
        if (data == null) return 0;

        int hash = 0;
        for (byte b : data) hash = hash * 31 + (b & 0xFF);
        return hash;
    }
}
