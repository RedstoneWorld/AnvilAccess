package xyz.gamecrash.anvilaccess.nbt;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * Enum containing all possible block entity types
 * <p>
 * Note: this is on the version of 1.21.11
 * </p>
 */
// TODO: get rid of the minecraft: (namespace key) at the beginning - maybe add when required?
public enum BlockEntityType {
    BANNER("minecraft:banner"),
    BARREL("minecraft:barrel"),
    BEACON("minecraft:beacon"),
    BED("minecraft:bed"),
    BEEHIVE("minecraft:beehive"),
    BEE_NEST("minecraft:bee_nest"),
    BELL("minecraft:bell"),
    BLAST_FURNACE("minecraft:blast_furnace"),
    BREWING_STAND("minecraft:brewing_stand"),
    SUSPICIOUS_SAND("minecraft:suspicious_sand", "suspicious_gravel"),
    SUSPICIOUS_GRAVEL("minecraft:suspicious_gravel"),
    CALIBRATED_SCULK_SENSOR("minecraft:calibrated_sculk_sensor"),
    CAMPFIRE("minecraft:campfire"),
    CHEST("minecraft:chest"),
    CHISELED_BOOKSHELF("minecraft:chiseled_bookshelf"),
    COMPARATOR("minecraft:comparator"),
    COMMAND_BLOCK("minecraft:command_block", "minecraft:chain_command_block", "minecraft:repeating_command_block"),
    CONDUIT("minecraft:conduit"),
    COPPER_GOLEM_STATUE("minecraft:copper_golem_statue", "minecraft:exposed_copper_golem_statue", "minecraft:weathered_copper_golem_statue", "minecraft:oxidized_copper_golem_statue",
        "minecraft:waxed_copper_golem_statue", "minecraft:waxed_exposed_copper_golem_statue", "minecraft:waxed_weathered_copper_golem_statue", "minecraft:waxed_oxidized_copper_golem_statue"),
    CRAFTER("minecraft:crafter"),
    CREAKING_HEART("minecraft:creaking_heart"),
    DAYLIGHT_DETECTOR("minecraft:daylight_detector"),
    DECORATED_POT("minecraft:decorated_pot"),
    DISPENSER("minecraft:dispenser"),
    DROPPER("minecraft:dropper"),
    ENCHANTING_TABLE("minecraft:enchanting_table"),
    ENDER_CHEST("minecraft:ender_chest"),
    END_GATEWAY("minecraft:end_gateway"),
    END_PORTAL("minecraft:end_portal"),
    FURNACE("minecraft:furnace"),
    HANGING_SIGN("minecraft:oak_hanging_sign", "minecraft:spruce_hanging_sign", "minecraft:birch_hanging_sign", "minecraft:jungle_hanging_sign", "minecraft:acacia_hanging_sign",
        "minecraft:dark_oak_hanging_sign", "minecraft:mangrove_hanging_sign", "minecraft:cherry_hanging_sign", "minecraft:pale_oak_hanging_sign", "minecraft:bamboo_hanging_sign",
        "minecraft:crimson_hanging_sign", "minecraft:warped_hanging_sign", "minecraft:oak_wall_hanging_sign", "minecraft:spruce_wall_hanging_sign", "minecraft:birch_wall_hanging_sign",
        "minecraft:jungle_wall_hanging_sign", "minecraft:acacia_wall_hanging_sign", "minecraft:dark_oak_wall_hanging_sign", "minecraft:mangrove_wall_hanging_sign", "minecraft:cherry_wall_hanging_sign",
        "minecraft:pale_oak_wall_hanging_sign", "minecraft:bamboo_wall_hanging_sign", "minecraft:crimson_wall_hanging_sign", "minecraft:warped_wall_hanging_sign"),
    HOPPER("minecraft:hopper"),
    JIGSAW("minecraft:jigsaw"),
    JUKEBOX("minecraft:jukebox"),
    LECTERN("minecraft:lectern"),
    MOB_SPAWNER("minecraft:mob_spawner"),
    MOVING_PISTON("minecraft:moving_piston"),
    SHULKER_BOX("minecraft:shulker_box", "minecraft:white_shulker_box", "minecraft:orange_shulker_box", "minecraft:magenta_shulker_box", "minecraft:light_blue_shulker_box",
        "minecraft:yellow_shulker_box", "minecraft:lime_shulker_box", "minecraft:pink_shulker_box", "minecraft:gray_shulker_box", "minecraft:light_gray_shulker_box",
        "minecraft:cyan_shulker_box", "minecraft:purple_shulker_box", "minecraft:blue_shulker_box", "minecraft:brown_shulker_box", "minecraft:green_shulker_box",
        "minecraft:red_shulker_box", "minecraft:black_shulker_box"),
    SIGN("minecraft:oak_sign", "minecraft:spruce_sign", "minecraft:birch_sign", "minecraft:jungle_sign", "minecraft:acacia_sign", "minecraft:dark_oak_sign", "minecraft:mangrove_sign",
        "minecraft:cherry_sign", "minecraft:pale_oak_sign", "minecraft:bamboo_sign", "minecraft:crimson_sign", "minecraft:warped_sign", "minecraft:oak_wall_sign", "minecraft:spruce_wall_sign",
        "minecraft:birch_wall_sign", "minecraft:jungle_wall_sign", "minecraft:acacia_wall_sign", "minecraft:dark_oak_wall_sign", "minecraft:mangrove_wall_sign", "minecraft:cherry_wall_sign",
        "minecraft:pale_oak_wall_sign", "minecraft:bamboo_wall_sign", "minecraft:crimson_wall_sign", "minecraft:warped_wall_sign"),
    SKULL("minecraft:skeleton_skull", "minecraft:wither_skeleton_skull", "minecraft:zombie_head", "minecraft:player_head", "minecraft:creeper_head", "minecraft:dragon_head", "minecraft:piglin_head",
        "minecraft:skeleton_wall_skull", "minecraft:wither_skeleton_wall_skull", "minecraft:zombie_wall_head", "minecraft:player_wall_head", "minecraft:creeper_wall_head", "minecraft:dragon_wall_head", "minecraft:piglin_wall_head"),
    SCULK_CATALYST("minecraft:sculk_catalyst"),
    SCULK_SENSOR("minecraft:sculk_sensor"),
    SCULK_SHRIEKER("minecraft:sculk_shrieker"),
    SHELF("minecraft:oak_shelf", "minecraft:spruce_shelf", "minecraft:birch_shelf", "minecraft:jungle_shelf", "minecraft:acacia_shelf", "minecraft:dark_oak_shelf",
        "minecraft:mangrove_shelf", "minecraft:cherry_shelf", "minecraft:pale_oak_shelf", "minecraft:bamboo_shelf", "minecraft:crimson_shelf", "minecraft:warped_shelf"),
    SMOKER("minecraft:smoker"),
    SOUL_CAMPFIRE("minecraft:soul_campfire"),
    STRUCTURE_BLOCK("minecraft:structure_block"),
    TRAPPED_CHEST("minecraft:trapped_chest"),
    TRIAL_SPAWNER("minecraft:trial_spawner"),
    VAULT("minecraft:vault");

    @Getter
    private final Set<String> id;

    BlockEntityType(String... id) {
        this.id = Set.of(id);
    }

    public static Optional<BlockEntityType> fromId(String id) {
        return Arrays.stream(values())
            .filter(type -> type.getId().contains(id))
            .findFirst();
    }
}
