package architectspalette.core.datagen;

import architectspalette.content.blocks.NubBlock;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APTags;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static architectspalette.core.registry.APBlocks.*;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        microBlocks();
        woodenTags();
        miningTags();
    }

    private void microBlocks() {
        var walls = getOrCreateTagBuilder(BlockTags.WALLS);
        var slabs = getOrCreateTagBuilder(BlockTags.SLABS);
        var stairs = getOrCreateTagBuilder(BlockTags.STAIRS);
        var nubs = getOrCreateTagBuilder(APTags.NUBS);
        nubs.forceAddTag(APTags.COPPER_NUBS);
        var copperNubs = getOrCreateTagBuilder(APTags.COPPER_NUBS);


        Services.REGISTRY.getModBlocks().forEach((block) -> {
            if (block instanceof WallBlock) walls.add(block);
            else if (block instanceof SlabBlock) slabs.add(block);
            else if (block instanceof StairBlock) stairs.add(block);
            else if (block instanceof NubBlock && !(block instanceof NubBlock.CopperNubBlock)) nubs.add(block);
            if (block instanceof NubBlock.CopperNubBlock) copperNubs.add(block);
        });
    }

    private void woodenTags() {
        getOrCreateTag(BlockTags.WOODEN_TRAPDOORS, TWISTED_TRAPDOOR);
        getOrCreateTag(BlockTags.WOODEN_STAIRS)
                .add(TWISTED_PLANKS.getPart(StoneBlockSet.SetComponent.STAIRS));
        getOrCreateTag(BlockTags.WOODEN_SLABS)
                .add(TWISTED_PLANKS.getPart(StoneBlockSet.SetComponent.SLAB));
        getOrCreateTag(BlockTags.WOODEN_PRESSURE_PLATES, TWISTED_PRESSURE_PLATE);
        getOrCreateTag(BlockTags.WOODEN_FENCES, TWISTED_FENCE);
        getOrCreateTag(BlockTags.WOODEN_DOORS, TWISTED_DOOR);
        getOrCreateTag(BlockTags.WOODEN_BUTTONS, TWISTED_BUTTON);
        getOrCreateTag(BlockTags.FENCE_GATES, TWISTED_FENCE_GATE);
    }

    private void miningTags() {
        StoneBlockSet.forAllSets(set -> {
            var tag = getOrCreateTagBuilder(set.miningTag);
            set.forEach(tag::add);
            if (set.miningLevel != null) {
                var levelTag = getOrCreateTagBuilder(set.miningLevel);
                set.forEach(levelTag::add);
            }
        });

        BlockNode.forAllBaseNodes((node) -> {
            node.forEach((n) -> {
                var tag = getOrCreateTagBuilder(n.tool.getToolTag());
                tag.add(n.get());
                //TODO: Mining level, other tags
                if (n.tool.getMiningTag() != null) {
                    var levelTag = getOrCreateTagBuilder(n.tool.getMiningTag());
                    levelTag.add(n.get());
                }
            });
        });

        pickaxeTag();
        axeTag();
        shovelTag();
        hoeTag();

        needsStoneToolTag();
    }

    private void pickaxeTag() {
        getOrCreateTag(BlockTags.MINEABLE_WITH_PICKAXE,
                ABYSSALINE,
                CHISELED_ABYSSALINE_BRICKS,
                ABYSSALINE_PILLAR,
                ABYSSALINE_LAMP_BLOCK,
                HADALINE,
                PIPE,
                OLIVESTONE_PILLAR,
                CRACKED_OLIVESTONE_BRICKS,
                CRACKED_OLIVESTONE_TILES,
                CHISELED_OLIVESTONE,
                ILLUMINATED_OLIVESTONE,
                CRACKED_ALGAL_BRICKS,
                CHISELED_ALGAL_BRICKS,
                ALGAL_LAMP,
                FLINT_BLOCK,
                FLINT_PILLAR,
                CHISELED_PACKED_ICE,
                PACKED_ICE_PILLAR,
                CHISELED_SUNMETAL_BLOCK,
                SUNMETAL_PILLAR,
                SUNMETAL_BARS,
                OSSEOUS_PILLAR,
                OSSEOUS_SKULL,
                LIT_OSSEOUS_SKULL,
                WITHERED_BONE_BLOCK,
                WITHERED_OSSEOUS_PILLAR,
                WITHERED_OSSEOUS_SKULL,
                LIT_WITHERED_OSSEOUS_SKULL,
                WITHER_LAMP,
                ENTWINE_PILLAR,
                CHISELED_ENTWINE,
                ENTWINE_BARS,
                HEAVY_STONE_BRICKS,
                HEAVY_MOSSY_STONE_BRICKS,
                HEAVY_CRACKED_STONE_BRICKS,
                RUNIC_GLOWSTONE,
                SCUTE_BLOCK,
                ROTTEN_FLESH_BLOCK,
                GILDED_SANDSTONE_PILLAR,
                CHISELED_GILDED_SANDSTONE,
                WEEPING_BLACKSTONE,
                TWISTING_BLACKSTONE,
                WEEPING_BLACKSTONE_BRICKS,
                TWISTING_BLACKSTONE_BRICKS,
                CHORAL_END_STONE_BRICKS,
                CRACKED_END_STONE_BRICKS,
                CHISELED_END_STONE_BRICKS,
                POTTED_TWISTED_SAPLING,
                CRACKED_BASALT_TILES,
                CHISELED_BASALT_TILES,
                SUNSTONE,
                MOONSTONE,
                MOLTEN_NETHER_BRICKS,
                HEAVY_END_STONE_BRICKS,
                HEAVY_CRACKED_END_STONE_BRICKS,
                REDSTONE_CAGE_LANTERN,
                GLOWSTONE_CAGE_LANTERN,
                ALGAL_CAGE_LANTERN,
                DRIPSTONE_PILLAR,
                CHISELED_DRIPSTONE,
                HEAVY_DRIPSTONE_BRICKS,
                DRIPSTONE_LAMP,
                CALCITE_PILLAR,
                CHISELED_CALCITE,
                HEAVY_CALCITE_BRICKS,
                CALCITE_LAMP,
                TUFF_PILLAR,
                CHISELED_TUFF,
                HEAVY_TUFF_BRICKS,
                TUFF_LAMP,
                HELIODOR_ROD,
                EKANITE_ROD,
                MONAZITE_ROD,
                UNOBTANIUM_BLOCK,
                NETHER_BRASS_PILLAR,
                NETHER_BRASS_CHAIN,
                NETHER_BRASS_LANTERN,
                ESOTERRACK_PILLAR,
                ONYX_PILLAR,
                CHISELED_WARDSTONE,
                WARDSTONE_PILLAR,
                WARDSTONE_LAMP,
                HAZARD_SIGN
        )
                .add(
                        ABYSSALINE_PLATING.get(),
                        CHISELED_HADALINE_BRICKS.get(),
                        HADALINE_PILLAR.get(),
                        HADALINE_LAMP_BLOCK.get(),
                        HADALINE_PLATING.get()
                )
                .forceAddTag(APTags.NUBS);
    }

    private void axeTag() {
        getOrCreateTag(BlockTags.MINEABLE_WITH_AXE,
                BLANK_ACACIA_TOTEM,
                CHARCOAL_BLOCK,
                GRINNING_ACACIA_TOTEM,
                PLACID_ACACIA_TOTEM,
                COD_LOG,
                COD_SCALES,
                ROTTEN_FLESH_BLOCK,
                SALMON_LOG,
                SALMON_SCALES,
                SHOCKED_ACACIA_TOTEM,
                SPOOL,
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
                OAK_RAILING,
                DARK_OAK_RAILING,
                SPRUCE_RAILING,
                TWISTED_RAILING,
                BIRCH_RAILING,
                ACACIA_RAILING,
                WARPED_RAILING,
                CRIMSON_RAILING,
                JUNGLE_RAILING,
                MANGROVE_RAILING
        ).add(ACACIA_TOTEM_WING.get());
    }

    private void shovelTag() {
        getOrCreateTag(BlockTags.MINEABLE_WITH_SHOVEL, COARSE_SNOW);
    }

    private void hoeTag() {
        getOrCreateTag(BlockTags.MINEABLE_WITH_HOE,
                ENDER_PEARL_BLOCK,
                TWISTED_LEAVES
        );
    }

    private void needsStoneToolTag() {
        getOrCreateTag(BlockTags.NEEDS_STONE_TOOL)
                .forceAddTag(APTags.COPPER_NUBS);
    }

    @SafeVarargs
    private FabricTagProvider<Block>.FabricTagBuilder getOrCreateTag(TagKey<Block> tagKey, Supplier<Block>... suppliers) {
        var tag = getOrCreateTagBuilder(tagKey);
        Arrays.stream(suppliers).map(Supplier::get).forEach(tag::add);
        return tag;
    }
}
