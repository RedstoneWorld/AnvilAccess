package xyz.gamecrash.anvilaccess.util;

import xyz.gamecrash.anvilaccess.io.MCAReader;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Cache for MCAReader instances to reduce file opening overhead
 */
public class MCAReaderCache {
    private static final int MAX_CACHE_SIZE = 10; // Limit to avoid memory issues
    private static final ConcurrentHashMap<Path, CachedReader> cache = new ConcurrentHashMap<>();
    private static final ReadWriteLock cacheLock = new ReentrantReadWriteLock();

    private static class CachedReader {
        final MCAReader reader;
        long lastAccessed;

        CachedReader(MCAReader reader) {
            this.reader = reader;
            this.lastAccessed = System.currentTimeMillis();
        }

        void updateAccess() {
            this.lastAccessed = System.currentTimeMillis();
        }
    }

    /**
     * Get a cached MCAReader or create a new one
     */
    public static MCAReader get(Path path) throws Exception {
        cacheLock.readLock().lock();
        try {
            CachedReader cached = cache.get(path);
            if (cached != null) {
                cached.updateAccess();
                return cached.reader;
            }
        } finally {
            cacheLock.readLock().unlock();
        }

        cacheLock.writeLock().lock();
        try {
            // concurrency is fun
            // IJ benchmarks say this is faster for some reason than without
            CachedReader cached = cache.get(path);
            if (cached != null) {
                cached.updateAccess();
                return cached.reader;
            }

            if (cache.size() >= MAX_CACHE_SIZE) evictOldest();

            MCAReader reader = new MCAReader(path);
            cache.put(path, new CachedReader(reader));
            return reader;
        } finally {
            cacheLock.writeLock().unlock();
        }
    }

    /**
     * Remove the oldest entry from cache
     */
    private static void evictOldest() {
        Path oldestPath = null;
        long oldestTime = Long.MAX_VALUE;

        for (var entry : cache.entrySet()) {
            if (entry.getValue().lastAccessed < oldestTime) {
                oldestTime = entry.getValue().lastAccessed;
                oldestPath = entry.getKey();
            }
        }

        if (oldestPath != null) {
            cache.remove(oldestPath);
        }
    }

    /**
     * Clear all cached readers
     */
    public static void clearCache() {
        cacheLock.writeLock().lock();
        try {
            cache.clear();
        } finally {
            cacheLock.writeLock().unlock();
        }
    }

    /**
     * Get current cache size
     */
    public static int getCacheSize() {
        return cache.size();
    }
}
