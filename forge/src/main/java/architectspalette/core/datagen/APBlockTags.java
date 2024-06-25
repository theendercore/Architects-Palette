package architectspalette.core.datagen;

import architectspalette.core.registry.MiscRegistry;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.platform.ForgeRegistryHelper.BLOCKS;
import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.APBlocksFG.*;

public class APBlockTags extends BlockTagsProvider {
    public APBlockTags(PackOutput pack, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(pack, completableFuture, MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        registerMiningTags();
        tagWalls();
    }

    @SafeVarargs
    private void tagBlocks(TagKey<Block> tagKey, RegistryObject<? extends Block>... registryObjects) {
        var tag = tag(tagKey);
        Arrays.stream(registryObjects).map(RegistryObject::get).forEach(tag::add);
    }

    private void tagWalls() {
        var walls = tag(BlockTags.WALLS);
        var slabs = tag(BlockTags.SLABS);
        var stairs = tag(BlockTags.STAIRS);

        BLOCKS.getEntries().forEach((reg) -> {
            if (reg.get() instanceof WallBlock wall) {
                walls.add(wall);
            }
            else if (reg.get() instanceof SlabBlock slab) {
                slabs.add(slab);
            }
            else if (reg.get() instanceof StairBlock stair) {
                stairs.add(stair);
            }
        });
    }

    private void registerMiningTags() {
        //Handle all sets
        StoneBlockSet.forAllSets(set -> {
            var tag = tag(set.miningTag);
            set.forEach(tag::add);
            //TODO: Commented out for now because I don't need to mess with it atm
//            if (set.miningLevel != null) {
//                TagAppender<Block> levelTag = tag(set.miningLevel);
//                set.forEach(levelTag::add);
//            }
        });

        BlockNode.forAllBaseNodes((node) -> {
            node.forEach((n) -> {
                var tag = tag(n.tool.getToolTag());
                tag.add(n.get());
                //TODO: Mining level, other tags
            });
        });

        tagBlocks(BlockTags.MINEABLE_WITH_PICKAXE,
                ABYSSALINE,
                CHISELED_ABYSSALINE_BRICKS,
                ABYSSALINE_PILLAR,
                ABYSSALINE_LAMP_BLOCK,
                ABYSSALINE_PLATING,
                HADALINE,
                CHISELED_HADALINE_BRICKS,
                HADALINE_PILLAR,
                HADALINE_LAMP_BLOCK,
                HADALINE_PLATING,
                PIPE,
                OLIVESTONE_PILLAR,
                CRACKED_OLIVESTONE_BRICKS,
                CRACKED_OLIVESTONE_TILES,
                CHISELED_OLIVESTONE,
                ILLUMINATED_OLIVESTONE,
                CRACKED_ALGAL_BRICKS,
                CHISELED_ALGAL_BRICKS,
                (RegistryObject<? extends Block>) ALGAL_LAMP,
                FLINT_BLOCK,
                FLINT_PILLAR,
                CHISELED_PACKED_ICE,
                PACKED_ICE_PILLAR,
                CHISELED_SUNMETAL_BLOCK,
                SUNMETAL_PILLAR,
                SUNMETAL_BARS,
                (RegistryObject<? extends Block>) OSSEOUS_PILLAR,
                (RegistryObject<? extends Block>)  OSSEOUS_SKULL,
                (RegistryObject<? extends Block>) LIT_OSSEOUS_SKULL,
                (RegistryObject<? extends Block>) WITHERED_BONE_BLOCK,
                (RegistryObject<? extends Block>) WITHERED_OSSEOUS_PILLAR,
                (RegistryObject<? extends Block>) WITHERED_OSSEOUS_SKULL,
                (RegistryObject<? extends Block>) LIT_WITHERED_OSSEOUS_SKULL,
                (RegistryObject<? extends Block>) WITHER_LAMP,
                ENTWINE_PILLAR,
                CHISELED_ENTWINE,
                ENTWINE_BARS,
                (RegistryObject<? extends Block>) HEAVY_STONE_BRICKS,
                (RegistryObject<? extends Block>) HEAVY_MOSSY_STONE_BRICKS,
                (RegistryObject<? extends Block>) HEAVY_CRACKED_STONE_BRICKS,
                (RegistryObject<? extends Block>) RUNIC_GLOWSTONE,
                (RegistryObject<? extends Block>) SCUTE_BLOCK,
                ROTTEN_FLESH_BLOCK,
                (RegistryObject<? extends Block>) GILDED_SANDSTONE_PILLAR,
                (RegistryObject<? extends Block>) CHISELED_GILDED_SANDSTONE,
                (RegistryObject<? extends Block>) WEEPING_BLACKSTONE,
                (RegistryObject<? extends Block>) TWISTING_BLACKSTONE,
                (RegistryObject<? extends Block>) WEEPING_BLACKSTONE_BRICKS,
                (RegistryObject<? extends Block>) TWISTING_BLACKSTONE_BRICKS,
                (RegistryObject<? extends Block>) CHORAL_END_STONE_BRICKS,
                (RegistryObject<? extends Block>) CRACKED_END_STONE_BRICKS,
                (RegistryObject<? extends Block>) CHISELED_END_STONE_BRICKS,
                POTTED_TWISTED_SAPLING,
                (RegistryObject<? extends Block>) CRACKED_BASALT_TILES,
                (RegistryObject<? extends Block>) CHISELED_BASALT_TILES,
                SUNSTONE,
                MOONSTONE,
                MOLTEN_NETHER_BRICKS,
                (RegistryObject<? extends Block>) HEAVY_END_STONE_BRICKS,
                (RegistryObject<? extends Block>) HEAVY_CRACKED_END_STONE_BRICKS,
                REDSTONE_CAGE_LANTERN,
                GLOWSTONE_CAGE_LANTERN,
                ALGAL_CAGE_LANTERN,
                (RegistryObject<? extends Block>) DRIPSTONE_PILLAR,
                (RegistryObject<? extends Block>) CHISELED_DRIPSTONE,
                (RegistryObject<? extends Block>) HEAVY_DRIPSTONE_BRICKS,
                (RegistryObject<? extends Block>) DRIPSTONE_LAMP,
                (RegistryObject<? extends Block>) CALCITE_PILLAR,
                (RegistryObject<? extends Block>) CHISELED_CALCITE,
                (RegistryObject<? extends Block>) HEAVY_CALCITE_BRICKS,
                (RegistryObject<? extends Block>) CALCITE_LAMP,
                (RegistryObject<? extends Block>) TUFF_PILLAR,
                (RegistryObject<? extends Block>) CHISELED_TUFF,
                (RegistryObject<? extends Block>) HEAVY_TUFF_BRICKS,
                (RegistryObject<? extends Block>)  TUFF_LAMP,
                HELIODOR_ROD,
                EKANITE_ROD,
                MONAZITE_ROD,
                (RegistryObject<? extends Block>) UNOBTANIUM_BLOCK,
                NETHER_BRASS_PILLAR,
                NETHER_BRASS_CHAIN,
                NETHER_BRASS_LANTERN,
                ESOTERRACK_PILLAR,
                ONYX_PILLAR,
                CHISELED_WARDSTONE,
                WARDSTONE_PILLAR,
                WARDSTONE_LAMP,
                STONE_NUB,
                SMOOTH_STONE_NUB,
                SANDSTONE_NUB,
                ANDESITE_NUB,
                GRANITE_NUB,
                DIORITE_NUB,
                BLACKSTONE_NUB,
                DEEPSLATE_NUB,
                BONE_NUB,
                NUB_OF_ENDER,
                IRON_NUB,
                GOLD_NUB,
                DIAMOND_NUB,
                EMERALD_NUB,
                NETHERITE_NUB,
                HAZARD_SIGN
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(MiscRegistry.NUBS);

        tagBlocks(BlockTags.MINEABLE_WITH_AXE,
                ACACIA_TOTEM_WING,
                BLANK_ACACIA_TOTEM,
                (RegistryObject<? extends Block>) CHARCOAL_BLOCK,
                GRINNING_ACACIA_TOTEM,
                PLACID_ACACIA_TOTEM,
                COD_LOG,
                COD_SCALES,
                ROTTEN_FLESH_BLOCK,
                SALMON_LOG,
                SALMON_SCALES,
                SHOCKED_ACACIA_TOTEM,
                (RegistryObject<? extends Block>) SPOOL,
                STRIPPED_TWISTED_LOG,
                STRIPPED_TWISTED_WOOD,
                TWISTED_BUTTON,
                TWISTED_DOOR,
                TWISTED_FENCE,
                TWISTED_FENCE_GATE,
                TWISTED_LOG,
                TWISTED_PRESSURE_PLATE,
                TWISTED_TRAPDOOR,
                TWISTED_WOOD,
                (RegistryObject<? extends Block>) OAK_RAILING,
                (RegistryObject<? extends Block>) DARK_OAK_RAILING,
                (RegistryObject<? extends Block>) SPRUCE_RAILING,
                (RegistryObject<? extends Block>) TWISTED_RAILING,
                (RegistryObject<? extends Block>) BIRCH_RAILING,
                (RegistryObject<? extends Block>) ACACIA_RAILING,
                (RegistryObject<? extends Block>) WARPED_RAILING,
                (RegistryObject<? extends Block>) CRIMSON_RAILING,
                (RegistryObject<? extends Block>) JUNGLE_RAILING,
                (RegistryObject<? extends Block>) MANGROVE_RAILING
            );
    }

}
