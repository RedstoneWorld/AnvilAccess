package xyz.gamecrash.anvilaccess.util.block;

import xyz.gamecrash.anvilaccess.model.BlockState;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Utility for interning BlockState objects to reduce memory usage
 */
public class BlockStateInterningUtil {
    private static final Map<BlockState, BlockState> internedStates = new WeakHashMap<>();

    /**
     * Intern a BlockState for reusing existing instances
     */
    public synchronized static BlockState intern(BlockState blockState) {
        if (blockState == null) return null;

        BlockState interned = internedStates.get(blockState);
        if (interned != null) return interned;

        internedStates.put(blockState, blockState);
        return blockState;
    }

    /**
     * Create and intern a BlockState
     */
    public static BlockState create(String id, Map<String, String> properties) {
        return intern(new BlockState(id, properties));
    }

    /**
     * Create and intern a BlockState (with no properties)
     */
    public static BlockState create(String id) {
        return intern(new BlockState(id));
    }
}
