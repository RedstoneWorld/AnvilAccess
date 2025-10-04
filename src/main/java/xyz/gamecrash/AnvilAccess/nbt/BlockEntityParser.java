package xyz.gamecrash.AnvilAccess.nbt;

import xyz.gamecrash.AnvilAccess.nbt.blockentities.*;
import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.BlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.blockentities.base.InventoryBlockEntity;
import xyz.gamecrash.AnvilAccess.nbt.tags.CompoundTag;

public class BlockEntityParser {
    public static BlockEntity fromNbt(CompoundTag nbt) {
        String id = nbt.getString("id", "unknown");
        BlockEntityType type = BlockEntityType.fromId(id).orElseThrow(() -> new IllegalStateException("Unknown block entity type: " + id));

        // Due to wanted completeness of this list, some block entities are listed in a separated switch-case. The compiler will probably optimize this anyway
        return switch(type) {
            case BANNER -> new BannerBlockEntity(nbt);
            case BARREL, CHEST, TRAPPED_CHEST, SHULKER_BOX -> new InventoryBlockEntity(nbt);
            case BEACON -> new BeaconBlockEntity(nbt);
            case BED -> new BlockEntity(nbt);
            case BEEHIVE, BEE_NEST -> new BeehiveBlockEntity(nbt);
            case BELL -> new BlockEntity(nbt);
            case BLAST_FURNACE, FURNACE, SMOKER -> new FurnaceBlockEntity(nbt);
            case BREWING_STAND ->  new BrewingStandBlockEntity(nbt);
            case SUSPICIOUS_SAND, SUSPICIOUS_GRAVEL -> new BrushableBlockEntity(nbt);
            case CALIBRATED_SCULK_SENSOR, SCULK_SENSOR -> new SculkBlockEntity(nbt);
            case CAMPFIRE, SOUL_CAMPFIRE -> new CampfireBlockEntity(nbt);
            case CHISELED_BOOKSHELF -> new ChiseledBookshelfBlockEntity(nbt);
            case COMPARATOR -> new ComparatorBlockEntity(nbt);
            case COMMAND_BLOCK -> new CommandBlockEntity(nbt);
            case CONDUIT -> new ConduitBlockEntity(nbt);
            case COPPER_GOLEM_STATUE -> new BlockEntity(nbt); // Not yet implemented
            case CRAFTER -> new CrafterBlockEntity(nbt);
            case CREAKING_HEART -> new BlockEntity(nbt); // Not yet implemented
            case DAYLIGHT_DETECTOR -> new BlockEntity(nbt);
            case DECORATED_POT -> new DecoratedPotBlockEntity(nbt);
            case DISPENSER, DROPPER -> new InventoryBlockEntity(nbt);
            case ENCHANTING_TABLE -> new BlockEntity(nbt);
            case ENDER_CHEST -> new BlockEntity(nbt);
            case END_GATEWAY -> new EndGatewayBlockEntity(nbt);
            case END_PORTAL -> new BlockEntity(nbt);
            case HANGING_SIGN, SIGN -> new SignBlockEntity(nbt);
            case HOPPER -> new HopperBlockEntity(nbt);
            case JIGSAW -> new BlockEntity(nbt); // Not yet implemented
            case JUKEBOX ->  new JukeboxBlockEntity(nbt);
            case LECTERN -> new LecternBlockEntity(nbt);
            case MOB_SPAWNER -> new SpawnerBlockEntity(nbt);
            case MOVING_PISTON -> new MovingPistonBlockEntity(nbt);
            case SKULL -> new SkullBlockEntity(nbt);
            case SCULK_CATALYST -> new SculkCatalystBlockEntity(nbt);
            case SCULK_SHRIEKER -> new SculkBlockEntity(nbt);
            case STRUCTURE_BLOCK -> new BlockEntity(nbt); // Not yet implemented
            case TRIAL_SPAWNER -> new TrialSpawnerBlockEntity(nbt);
            case VAULT -> new VaultBlockEntity(nbt);
        };
    }
}
