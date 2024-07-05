package architectspalette.core.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.util.KeyMaker.stringTag;

public interface MiscTags {

    // Quark compatibility
    TagKey<Block> QUARK_VERTICAL_SLAB = blockTag("quark:vertical_slab");
    TagKey<Item> QUARK_VERTICAL_SLAB_ITEM = itemTag("quark:vertical_slab");

    //Forge compatibility
    TagKey<Block> FORGE_STONE = blockTag("forge:stone");
    TagKey<Block> FORGE_ICES_PACKEDICE = blockTag("forge:ices/packedice");
    TagKey<Block> FORGE_STORAGE_BLOCKS_ENDER_PEARLS = blockTag("forge:storage_blocks/ender_pearls");

    TagKey<Item> FORGE_STONE_ITEM = itemTag("forge:stone");
    TagKey<Item> FORGE_ICES_PACKEDICE_ITEM = itemTag("forge:ices/packedice");
    TagKey<Item> FORGE_STORAGE_BLOCKS_ENDER_PEARLS_ITEM = itemTag("forge:storage_blocks/ender_pearls");
    TagKey<Item> FORGE_BONES_WITHER = itemTag("forge:bones/wither");



    static TagKey<Block> blockTag(String name) {
        return stringTag(Registries.BLOCK, name);
    }

    static TagKey<Item> itemTag(String name) {
        return stringTag(Registries.ITEM, name);
    }
}
