package xyz.gamecrash.AnvilAccess.util.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class HashUtils {
    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static String sha256(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input);
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static String md5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    public static String crc32(byte[] input) {
        CRC32 crc = new CRC32();
        crc.update(input);
        return Long.toHexString(crc.getValue());
    }

    public static String crc32(String input) {
        return crc32(input.getBytes());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) result.append(String.format("%02x", b));
        return result.toString();
    }

    public static int hashLongArray(long[] array) {
        if (array == null) return 0;

        int result = 1;
        for (long element : array) {
            int elementHash = Long.hashCode(element);
            result = 31 * result + elementHash;
        }
        return result;
    }

    public static int fastHash(byte[] data) {
        if (data == null) return 0;

        int hash = 0;
        for (byte b : data) hash = hash * 31 + (b & 0xFF);
        return hash;
    }
}
