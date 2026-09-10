package xyz.gamecrash.anvilaccess.nbt;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * Enum containing all possible block entity types
 * <p>
 * This is on the state of the Minecraft version 26.2
 * </p>
 * @see BlockEntityParser
 */
// TODO: get rid of the minecraft: (namespace key) at the beginning - maybe add when required?
public enum BlockEntityType {
    BANNER("minecraft:banner"),
    BARREL("minecraft:barrel"),
    BEACON("minecraft:beacon"),
    BEEHIVE("minecraft:beehive"),
    BELL("minecraft:bell"),
    BLAST_FURNACE("minecraft:blast_furnace"),
    BREWING_STAND("minecraft:brewing_stand"),
    BRUSHABLE_BLOCK("minecraft:brushable_block"),
    CALIBRATED_SCULK_SENSOR("minecraft:calibrated_sculk_sensor"),
    CAMPFIRE("minecraft:campfire"),
    CHISELED_BOOKSHELF("minecraft:chiseled_bookshelf"),
    CHEST("minecraft:chest"),
    COMPARATOR("minecraft:comparator"),
    COMMAND_BLOCK("minecraft:command_block"),
    CONDUIT("minecraft:conduit"),
    COPPER_GOLEM_STATUE("minecraft:copper_golem_statue"),
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
    HANGING_SIGN("minecraft:hanging_sign"),
    HOPPER("minecraft:hopper"),
    JIGSAW("minecraft:jigsaw_block"),
    JUKEBOX("minecraft:jukebox"),
    LECTERN("minecraft:lectern"),
    MOB_SPAWNER("minecraft:mob_spawner"),
    MOVING_PISTON("minecraft:piston"),
    POTENT_SULFUR("minecraft:potent_sulfur"),
    SCULK_CATALYST("minecraft:sculk_catalyst"),
    SCULK_SENSOR("minecraft:sculk_sensor"),
    SCULK_SHRIEKER("minecraft:sculk_shrieker"),
    SHULKER_BOX("minecraft:shulker_box"),
    SHELF("minecraft:shelf"),
    SIGN("minecraft:sign"),
    SKULL("minecraft:skull"),
    SMOKER("minecraft:smoker"),
    SOUL_CAMPFIRE("minecraft:soul_campfire"),
    STRUCTURE_BLOCK("minecraft:structure_block"),
    TEST_BLOCK("minecraft:test_block"),
    TEST_INSTANCE_BLOCK("minecraft:test_instance_block"),
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
