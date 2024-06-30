package architectspalette.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.APConstants.modLoc;

public class APTags {
    // Tags
    public static final TagKey<Block> CRYSTAL_REPLACEABLE = blockTag("crystal_formation_replaceable");
    public static final TagKey<Block> GREEN_FIRE_SUPPORTING = blockTag("green_fire_supporting");
    public static final TagKey<Block> WIZARD_BLOCKS = blockTag("wizard_blocks");
    public static final TagKey<Block> NUBS = blockTag("nubs");
    public static final TagKey<Item> NUBS_ITEM = itemTag("nubs");

    public static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, modLoc(name));
    }

    public static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, modLoc(name));
    }
}
