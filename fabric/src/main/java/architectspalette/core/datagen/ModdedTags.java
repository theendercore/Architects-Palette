package architectspalette.core.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.util.KeyMaker.stringTag;

public interface ModdedTags {

    // Quark compatibility
    TagKey<Block> QUARK_VERTICAL_SLAB = blockTag("quark:vertical_slab");
    TagKey<Item> QUARK_VERTICAL_SLAB_ITEM = itemTag("quark:vertical_slab");

    //Forge compatibility
    TagKey<Block> STORAGE_BLOCKS_ENDER_PEARLS = blockTag("c:storage_blocks/ender_pearls");
    TagKey<Item> STORAGE_BLOCKS_ENDER_PEARLS_ITEM = itemTag("c:storage_blocks/ender_pearls");
    TagKey<Item> BONES_WITHER = itemTag("c:bones/wither");



    static TagKey<Block> blockTag(String name) {
        return stringTag(Registries.BLOCK, name);
    }

    static TagKey<Item> itemTag(String name) {
        return stringTag(Registries.ITEM, name);
    }
}
