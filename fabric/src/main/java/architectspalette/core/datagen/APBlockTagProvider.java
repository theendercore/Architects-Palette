package architectspalette.core.datagen;

import architectspalette.content.blocks.NubBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APTags;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static architectspalette.core.registry.APBlocks.*;

public class APBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public APBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        microBlocks();
        woodenTags();
        miscTags();
        abyssalineTags();
        miscModTags();
        compatabilityTags();
        miningTags();
    }

    private void microBlocks() {
        var walls = getOrCreateTagBuilder(BlockTags.WALLS);
        var slabs = getOrCreateTagBuilder(BlockTags.SLABS);
        var stairs = getOrCreateTagBuilder(BlockTags.STAIRS);
        var nubs = getOrCreateTagBuilder(APTags.NUBS);
        nubs.forceAddTag(APTags.COPPER_NUBS);
        var copperNubs = getOrCreateTagBuilder(APTags.COPPER_NUBS);
        var verticalSlabs = getOrCreateTag(ModdedTags.QUARK_VERTICAL_SLAB);


        Services.REGISTRY.getModBlocks().forEach((block) -> {
            if (block instanceof WallBlock) walls.add(block);
            else if (block instanceof SlabBlock) slabs.add(block);
            else if (block instanceof StairBlock) stairs.add(block);
            else if (block instanceof NubBlock && !(block instanceof NubBlock.CopperNubBlock)) nubs.add(block);
            if (block instanceof NubBlock.CopperNubBlock) copperNubs.add(block);
            if (block instanceof VerticalSlabBlock) verticalSlabs.add(block);
        });
    }

    private void woodenTags() {
        // Add boards to all the stuff wood stuff
        boards.forEach(n -> n.forEach(block -> {
            var tag = switch (block.type) {
                case SLAB -> BlockTags.WOODEN_SLABS;
                case VERTICAL_SLAB -> null; //(ender) does quark have wooden vertical slabs tag?
                case STAIRS -> BlockTags.WOODEN_STAIRS;
                case WALL -> null; //(ender) this is here for later
                case FENCE -> BlockTags.WOODEN_FENCES;
                default -> null;
            };
            if (tag == null) return;
            getOrCreateTag(tag, block);
        }));


        // processed
        getOrCreateTag(BlockTags.PLANKS, TWISTED_PLANKS);
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
        // logs
        getOrCreateTag(APTags.TWISTED_LOGS, TWISTED_LOG, STRIPPED_TWISTED_LOG, STRIPPED_TWISTED_WOOD, TWISTED_WOOD);
        getOrCreateTag(BlockTags.LOGS_THAT_BURN).forceAddTag(APTags.TWISTED_LOGS);

        // leaves / plant
        getOrCreateTag(BlockTags.LEAVES, TWISTED_LEAVES);
        getOrCreateTag(BlockTags.SAPLINGS, TWISTED_SAPLING);
    }

    private void miscTags() {
        var dragonImmune = getOrCreateTag(BlockTags.DRAGON_IMMUNE,
                ENTWINE_PILLAR,
                ENTWINE_BARS,
                CHISELED_ENTWINE,
                HEAVY_END_STONE_BRICKS,
                HEAVY_CRACKED_END_STONE_BRICKS,
                CHISELED_END_STONE_BRICKS,
                CRACKED_END_STONE_BRICKS,
                CHORAL_END_STONE_BRICKS
        ).add(
                Blocks.END_STONE_BRICKS,
                Blocks.END_STONE_BRICK_WALL,
                Blocks.END_STONE_BRICK_STAIRS,
                Blocks.END_STONE_BRICK_SLAB
        );
        ENTWINE.forEach(dragonImmune::add);

        getOrCreateTag(BlockTags.FIRE, NETHER_BRASS_FIRE);
        getOrCreateTag(BlockTags.FLOWER_POTS, POTTED_TWISTED_SAPLING);

        var mushroomGrow = getOrCreateTag(BlockTags.MUSHROOM_GROW_BLOCK);
        MYONITE.forEach(mushroomGrow::add);
        MYONITE_BRICK.forEach(mushroomGrow::add);
        MUSHY_MYONITE_BRICK.forEach(mushroomGrow::add);

        getOrCreateTag(BlockTags.WALL_POST_OVERRIDE)
                .forceAddTag(APTags.CAGE_LANTERNS);

        getOrCreateTag(BlockTags.FENCES).add(ANCIENT_PLATING.getPart(StoneBlockSet.SetComponent.FENCE));

    }

    private void miscModTags() {
        getOrCreateTag(APTags.CAGE_LANTERNS, REDSTONE_CAGE_LANTERN, GLOWSTONE_CAGE_LANTERN, ALGAL_CAGE_LANTERN);
        getOrCreateTag(APTags.CRYSTAL_REPLACEABLE)
                .add(
                        Blocks.NETHER_WART_BLOCK,
                        Blocks.WARPED_WART_BLOCK,
                        Blocks.TWISTING_VINES,
                        Blocks.TWISTING_VINES_PLANT,
                        Blocks.WEEPING_VINES,
                        Blocks.WEEPING_VINES_PLANT
                )
                .forceAddTag(BlockTags.REPLACEABLE);

        var greenFire = getOrCreateTag(APTags.GREEN_FIRE_SUPPORTING, NETHER_BRASS_PILLAR);
        NETHER_BRASS.forEach(greenFire::add);
        CUT_NETHER_BRASS.forEach(greenFire::add);
        SMOOTH_NETHER_BRASS.forEach(greenFire::add);
        var wizardBlocks = getOrCreateTag(APTags.WIZARD_BLOCKS,
                CHISELED_WARDSTONE,
                WARDSTONE_PILLAR,
                WARDSTONE_LAMP
        );

        WARDSTONE.forEach(wizardBlocks::add);
        WARDSTONE_BRICKS.forEach(wizardBlocks::add);

    }

    private void compatabilityTags() {
        // fabric
        getOrCreateTag(ConventionalBlockTags.STONES, MYONITE);
        // forge
        getOrCreateTag(ModdedTags.STORAGE_BLOCKS_ENDER_PEARLS, ENDER_PEARL_BLOCK);
    }

    private void abyssalineTags() {
        var abyssaline = getOrCreateTag(APTags.ABYSSALINE,
                ABYSSALINE,
                CHISELED_ABYSSALINE_BRICKS,
                ABYSSALINE_PILLAR,
                ABYSSALINE_LAMP_BLOCK
        ).add(ABYSSALINE_PLATING.get());
        ABYSSALINE_BRICKS.forEach(abyssaline::add);
        ABYSSALINE_TILES.forEach(abyssaline::add);

        var hadaline = getOrCreateTag(APTags.HADALINE, HADALINE)
                .add(
                        CHISELED_HADALINE_BRICKS.get(),
                        HADALINE_PILLAR.get(),
                        HADALINE_LAMP_BLOCK.get(),
                        HADALINE_PLATING.get()
                );
        HADALINE_BRICKS.forEach(hadaline::add);
        HADALINE_TILES.forEach(hadaline::add);
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

        BlockNode.forAllBaseNodes((node) -> node.forEach((n) -> {
            var tag = getOrCreateTagBuilder(n.tool.getToolTag());
            tag.add(n.get());
            //TODO: Mining level, other tags
            if (n.tool.getMiningTag() != null) {
                var levelTag = getOrCreateTagBuilder(n.tool.getMiningTag());
                levelTag.add(n.get());
            }
        }));

        pickaxeTag();
        axeTag();
        shovelTag();
        hoeTag();

        needsStoneToolTag();
        needsIronToolTag();
        needsDiamondToolTag();
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
                MANGROVE_RAILING,
                CHERRY_RAILING,
                BAMBOO_RAILING
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
        var needsStone = getOrCreateTag(BlockTags.NEEDS_STONE_TOOL,
                ENTWINE_PILLAR,
                CHISELED_ENTWINE,
                ENTWINE_BARS,
                FLINT_BLOCK,
                FLINT_PILLAR,
                SUNMETAL_BARS,
                SUNMETAL_PILLAR,
                CHISELED_SUNMETAL_BLOCK,
                CHISELED_WARDSTONE,
                WARDSTONE_PILLAR,
                WARDSTONE_LAMP
        )
                .forceAddTag(APTags.COPPER_NUBS);

        ENTWINE.forEach(needsStone::add);
        FLINT_TILES.forEach(needsStone::add);
        SUNMETAL.forEach(needsStone::add);
        WARDSTONE.forEach(needsStone::add);
        WARDSTONE_BRICKS.forEach(needsStone::add);
    }

    private void needsIronToolTag() {
        var needsIron = getOrCreateTag(BlockTags.NEEDS_IRON_TOOL,
                PIPE,
                UNOBTANIUM_BLOCK,
                NETHER_BRASS_PILLAR,
                NETHER_BRASS_CHAIN,
                NETHER_BRASS_LANTERN,
                HAZARD_BLOCK
        );
        PLATING_BLOCK.forEach(needsIron::add);
        NETHER_BRASS.forEach(needsIron::add);
        CUT_NETHER_BRASS.forEach(needsIron::add);
        SMOOTH_NETHER_BRASS.forEach(needsIron::add);
        ANCIENT_PLATING.forEach(needsIron::add);

    }

    private void needsDiamondToolTag() {
        getOrCreateTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .forceAddTag(APTags.ABYSSALINE)
                .forceAddTag(APTags.HADALINE);
    }

    @SafeVarargs
    private FabricTagProvider<Block>.FabricTagBuilder getOrCreateTag(TagKey<Block> tagKey, Supplier<Block>... suppliers) {
        var tag = getOrCreateTagBuilder(tagKey);
        Arrays.stream(suppliers).map(Supplier::get).forEach(tag::add);
        return tag;
    }
}
