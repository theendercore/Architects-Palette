package architectspalette.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.util.KeyMaker.modTag;

public class APTags {
    // Tags
    public static final TagKey<Block> CRYSTAL_REPLACEABLE = blockTag("crystal_formation_replaceable");
    public static final TagKey<Block> GREEN_FIRE_SUPPORTING = blockTag("green_fire_supporting");
    public static final TagKey<Block> WIZARD_BLOCKS = blockTag("wizard_blocks");
    public static final TagKey<Block> NUBS = blockTag("nubs");
    public static final TagKey<Block> COPPER_NUBS = blockTag("copper_nubs");

    public static final TagKey<Item> NUBS_ITEM = itemTag("nubs");
    public static final TagKey<Item> COPPER_NUBS_ITEM = itemTag("copper_nubs");





    public static TagKey<Block> blockTag(String name) {
        return modTag(Registries.BLOCK, name);
    }

    public static TagKey<Item> itemTag(String name) {
        return modTag(Registries.ITEM, name);
    }
}
