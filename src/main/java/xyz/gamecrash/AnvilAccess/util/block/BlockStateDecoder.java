package xyz.gamecrash.AnvilAccess.util.block;

import xyz.gamecrash.AnvilAccess.model.BlockState;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;
import xyz.gamecrash.AnvilAccess.nbt.tags.StringTag;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to decode block states from NBT
 */
public class BlockStateDecoder {

    /**
     * Decodes a BlockState from a given NBT compound tag
     */
    public static BlockState decodeBlockState(CompoundTag blockStateTag) {
        String name = blockStateTag.getString("Name", "minecraft:air");

        Map<String, String> properties = new HashMap<>();
        CompoundTag propertiesTag = blockStateTag.getCompound("Properties", null);

        if (propertiesTag != null) {
            for (String key : propertiesTag.getKeys()) {
                if (propertiesTag.get(key) instanceof StringTag stringTag) properties.put(key, stringTag.getValue());
            }
        }

        return new BlockState(name, properties);
    }
}
