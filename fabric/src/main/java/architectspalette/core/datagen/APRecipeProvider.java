package architectspalette.core.datagen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.APTags;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.APItems.*;
import static architectspalette.core.registry.util.BlockNode.BlockType.*;
import static architectspalette.core.util.RecipeHelper.*;
import static net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS;
import static net.minecraft.data.recipes.RecipeCategory.MISC;

public class APRecipeProvider extends FabricRecipeProvider {
    public APRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    public void buildRecipes(RecipeOutput exporter) {
        BlockNode.forAllBaseNodes((node) -> processBlockNode(exporter, node));
        StoneBlockSet.forAllSets((set) -> processStoneBlockSet(exporter, set));

        miscRecipes(exporter);

        makeWarpingRecipes(exporter);
        smeltingRecipes(exporter);
        makePillarRecipes(exporter);
        makeOreBrickRecipes(exporter);
        makeBoardRecipes(exporter);
        railingRecipes(exporter);
        makeWoodRecipes(exporter);

        makeWardstoneRecipes(exporter);
        makeFlintRecipes(exporter);
        makeWitheredRecipes(exporter);
        makeOsseousRecipes(exporter);
        makeNetherBrassRecipes(exporter);
        makeSunmetalRecipes(exporter);
        makeBlackstoneRecipes(exporter);
    }

    private void miscRecipes(RecipeOutput exporter) {
        //Base recipes for blocks
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, SHEET_METAL.get(), 64)
                .pattern("x")
                .pattern("x")
                .define('x', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(exporter);

        quickStonecuttingRecipe(exporter, Items.IRON_INGOT, SHEET_METAL.get(), 4);
        quickStonecuttingRecipe(exporter, Items.IRON_BLOCK, SHEET_METAL.get(), 36);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, TREAD_PLATE.get(), 4)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', Items.IRON_NUGGET)
                .define('y', PLATING_BLOCK.get())
                .unlockedBy(hasName(PLATING_BLOCK), hasItem(PLATING_BLOCK))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HAZARD_BLOCK.get(), 4)
                .pattern("xy")
                .pattern("yx")
                .define('x', Blocks.YELLOW_CONCRETE)
                .define('y', Blocks.BLACK_CONCRETE)
                .unlockedBy(getHasName(Blocks.YELLOW_CONCRETE), has(Blocks.YELLOW_CONCRETE))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BREAD_BLOCK.get(), 9)
                .pattern("xxx")
                .define('x', Blocks.HAY_BLOCK)
                .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
                .save(exporter);

        brickRecipe(exporter, ORACLE_BLOCK.get(), ORACLE_JELLY.get(), 8);
        brickRecipe(exporter, CEREBRAL_BLOCK.get(), CEREBRAL_PLATE.get(), 8);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(SPECIAL).get(), 2)
                .pattern("xyx")
                .define('x', CEREBRAL_PLATE.get())
                .define('y', ORACLE_BLOCK.get())
                .unlockedBy(getHasName(ORACLE_BLOCK), has(ORACLE_BLOCK.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, SPOOL.get(), 2)
                .pattern("wfw")
                .define('f', ItemTags.WOODEN_FENCES)
                .define('w', Blocks.WHITE_WOOL)
                .unlockedBy(hasName(SPOOL), hasItem(SPOOL))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(LAMP).get(), 2)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', ORACLE_JELLY.get())
                .define('y', Items.END_ROD)
                .unlockedBy(getHasName(ORACLE_BLOCK), has(ORACLE_BLOCK.get()))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, ORACLE_JELLY.get(), 4)
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, CEREBRAL_PLATE.get(), 4)
                .requires(Blocks.TUFF)
                .requires(Items.QUARTZ)
                .requires(Items.CHARCOAL)
                .unlockedBy(getHasName(Items.TUFF), has(Items.TUFF))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MOONSHALE.getChild(SPECIAL).get(), 2)
                .pattern("s")
                .pattern("b")
                .pattern("s")
                .define('s', Blocks.STONE_SLAB)
                .define('b', MOONSHALE)
                .unlockedBy(getHasName(MOONSHALE), has(MOONSHALE.get()))
                .save(exporter);

//        UNOBTANIUM
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, UNOBTANIUM_BLOCK.get(), 1)
                .pattern("xx")
                .pattern("xx")
                .define('x', UNOBTANIUM.get())
                .unlockedBy(hasName(UNOBTANIUM_BLOCK), hasItem(UNOBTANIUM_BLOCK))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, UNOBTANIUM.get(), 5)
                .requires(UNOBTANIUM_BLOCK.get())
                .unlockedBy(getHasName(UNOBTANIUM.get()), has(UNOBTANIUM.get()))
                .save(exporter);

        // ender pearl
        ShapelessRecipeBuilder.shapeless(MISC, ENDER_PEARL_BLOCK.get(), 1)
                .requires(Items.ENDER_PEARL)
                .requires(Items.ENDER_PEARL)
                .requires(Items.ENDER_PEARL)
                .requires(Items.ENDER_PEARL)
                .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.ENDER_PEARL))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, Items.ENDER_PEARL, 4)
                .requires(ENDER_PEARL_BLOCK.get())
                .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.ENDER_PEARL))
                .save(exporter);


        // rotten blocks
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ROTTEN_FLESH_BLOCK.get(), 1)
                .pattern("xxx")
                .pattern("xxx")
                .pattern("xxx")
                .define('x', Items.ROTTEN_FLESH)
                .unlockedBy(hasName(ROTTEN_FLESH_BLOCK), hasItem(ROTTEN_FLESH_BLOCK))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, Items.ROTTEN_FLESH, 9)
                .requires(ROTTEN_FLESH_BLOCK.get())
                .unlockedBy(hasName(ROTTEN_FLESH_BLOCK), hasItem(ROTTEN_FLESH_BLOCK))
                .save(exporter);

        // Plating & Piping
        brickRecipe(exporter, PIPE.get(), PLATING_BLOCK.get(), 4);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, PLATING_BLOCK.get(), 8)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', Items.IRON_NUGGET)
                .define('y', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, SUNSTONE.get(), 4)
                .pattern("XY")
                .pattern("YX")
                .define('X', Items.BASALT)
                .define('Y', SUNMETAL_BLEND.get())
                .unlockedBy(getHasName(SUNMETAL_BLEND.get()), has(SUNMETAL_BLEND.get()))
                .save(exporter);
    }

    private void smeltingRecipes(RecipeOutput exporter) {
        quickSmeltingRecipe(exporter, MOONSHALE, CRATERSTONE);
        quickSmeltingRecipe(exporter, APItems.ALGAL_BRICK.get(), ALGAL_BLEND.get());
        quickSmeltingRecipe(exporter, Items.BLACK_DYE, WITHERED_BONE.get());
        quickSmeltingRecipe(exporter, APItems.NETHER_BRASS.get(), BRASS_BLEND.get());
        quickSmeltingRecipe(exporter, SMOOTH_NETHER_BRASS.get(), APBlocks.NETHER_BRASS.get());
        quickSmeltingRecipe(exporter, SUNMETAL_BRICK.get(), SUNMETAL_BLEND.get());
        quickSmeltingRecipe(exporter, WARDSTONE_BRICK.get(), WARDSTONE_BLEND.get());

        //Crack recipes
        quickSmeltingRecipe(exporter, CRACKED_ALGAL_BRICKS.get(), APBlocks.ALGAL_BRICK.get());
        quickSmeltingRecipe(exporter, CRACKED_BASALT_TILES.get(), BASALT_TILES.get());
        quickSmeltingRecipe(exporter, CRACKED_END_STONE_BRICKS.get(), Blocks.END_STONE_BRICKS);
        quickSmeltingRecipe(exporter, CRACKED_OLIVESTONE_BRICKS.get(), OLIVESTONE_BRICK.get());
        quickSmeltingRecipe(exporter, CRACKED_OLIVESTONE_TILES.get(), OLIVESTONE_TILE.get());
        quickSmeltingRecipe(exporter, HEAVY_CRACKED_END_STONE_BRICKS.get(), Blocks.END_STONE_BRICKS);
        quickSmeltingRecipe(exporter, HEAVY_CRACKED_STONE_BRICKS.get(), HEAVY_STONE_BRICKS.get());


        quickSmokingRecipe(exporter, APBlocks.CHARCOAL_BLOCK.get(), ItemTags.LOGS_THAT_BURN);


        quickBlastingRecipe(exporter, SUNMETAL_BRICK.get(), SUNMETAL_BLEND.get());
        quickBlastingRecipe(exporter, APItems.NETHER_BRASS.get(), BRASS_BLEND.get());
        quickBlastingRecipe(exporter, SMOOTH_NETHER_BRASS.get(), APBlocks.NETHER_BRASS.get());
    }

    private void makePillarRecipes(RecipeOutput exporter) {
        quickPillarRecipe(exporter, ABYSSALINE_PILLAR.get(), ABYSSALINE_BRICKS.get());
        quickPillarRecipe(exporter, CALCITE_PILLAR.get(), CALCITE_BRICKS.get());
        quickPillarRecipe(exporter, DRIPSTONE_PILLAR.get(), DRIPSTONE_BRICKS.get());
        quickPillarRecipe(exporter, ENTWINE_PILLAR.get(), ENTWINE.get());
        quickPillarRecipe(exporter, ESOTERRACK_PILLAR.get(), ESOTERRACK.get());
        quickPillarRecipe(exporter, GILDED_SANDSTONE_PILLAR.get(), GILDED_SANDSTONE.get());
        quickPillarRecipe(exporter, HADALINE_PILLAR.get(), HADALINE_BRICKS.get());
        quickPillarRecipe(exporter, OLIVESTONE_PILLAR.get(), OLIVESTONE_BRICK.get());
        quickPillarRecipe(exporter, ONYX_PILLAR.get(), ONYX_BRICKS.get());
        quickPillarRecipe(exporter, PACKED_ICE_PILLAR.get(), POLISHED_PACKED_ICE.get());
        quickPillarRecipe(exporter, TUFF_PILLAR.get(), TUFF_BRICKS.get());
    }

    private void makeOreBrickRecipes(RecipeOutput exporter) {
        oreBrickRecipe(exporter, COAL_BRICKS.get(), Items.COAL);
        oreBrickRecipe(exporter, DIAMOND_BRICKS.get(), Items.DIAMOND);
        oreBrickRecipe(exporter, EMERALD_BRICKS.get(), Items.EMERALD);
        oreBrickRecipe(exporter, GOLD_BRICKS.get(), Items.GOLD_INGOT);
        oreBrickRecipe(exporter, IRON_BRICKS.get(), Items.IRON_INGOT);
        oreBrickRecipe(exporter, LAPIS_BRICKS.get(), Items.LAPIS_LAZULI);
        oreBrickRecipe(exporter, REDSTONE_BRICKS.get(), Items.REDSTONE);
    }

    private void makeBoardRecipes(RecipeOutput exporter) {
        boardRecipe(exporter, ACACIA_BOARDS.get(), Blocks.ACACIA_PLANKS);
        boardRecipe(exporter, BIRCH_BOARDS.get(), Blocks.BIRCH_PLANKS);
        boardRecipe(exporter, CRIMSON_BOARDS.get(), Blocks.CRIMSON_PLANKS);
        boardRecipe(exporter, DARK_OAK_BOARDS.get(), Blocks.DARK_OAK_PLANKS);
        boardRecipe(exporter, JUNGLE_BOARDS.get(), Blocks.JUNGLE_PLANKS);
        boardRecipe(exporter, MANGROVE_BOARDS.get(), Blocks.MANGROVE_PLANKS);
        boardRecipe(exporter, OAK_BOARDS.get(), Blocks.OAK_PLANKS);
        boardRecipe(exporter, SPRUCE_BOARDS.get(), Blocks.SPRUCE_PLANKS);
        boardRecipe(exporter, TWISTED_BOARDS.get(), TWISTED_PLANKS.get());
        boardRecipe(exporter, WARPED_BOARDS.get(), Blocks.WARPED_PLANKS);
    }

    private void railingRecipes(RecipeOutput exporter) {
        railingRecipe(exporter, ACACIA_RAILING.get(), Blocks.ACACIA_PLANKS);
        railingRecipe(exporter, BIRCH_RAILING.get(), Blocks.BIRCH_PLANKS);
        railingRecipe(exporter, CRIMSON_RAILING.get(), Blocks.CRIMSON_PLANKS);
        railingRecipe(exporter, DARK_OAK_RAILING.get(), Blocks.DARK_OAK_PLANKS);
        railingRecipe(exporter, JUNGLE_RAILING.get(), Blocks.JUNGLE_PLANKS);
        railingRecipe(exporter, MANGROVE_RAILING.get(), Blocks.MANGROVE_PLANKS);
        railingRecipe(exporter, OAK_RAILING.get(), Blocks.OAK_PLANKS);
        railingRecipe(exporter, SPRUCE_RAILING.get(), Blocks.SPRUCE_PLANKS);
        railingRecipe(exporter, TWISTED_RAILING.get(), TWISTED_PLANKS.get());
        railingRecipe(exporter, WARPED_RAILING.get(), Blocks.WARPED_PLANKS);
    }

    private void makeWarpingRecipes(RecipeOutput exporter) {
        netherWarpingRecipe(exporter, ESOTERRACK.get(), Blocks.ANDESITE);
        netherWarpingRecipe(exporter, ONYX.get(), Blocks.GRANITE);
        netherWarpingRecipe(exporter, NEBULITE.get(), Blocks.DIORITE);
        netherWarpingRecipe(exporter, MOONSHALE.get(), Blocks.STONE);
        netherWarpingRecipe(exporter, MOONSHALE.getChild(BRICKS).get(), Blocks.STONE_BRICKS);
        netherWarpingRecipe(exporter, CRATERSTONE.get(), Blocks.COBBLESTONE);

        netherWarpingRecipe(exporter, WARPSTONE.get(), Blocks.CLAY);
        netherWarpingRecipe(exporter, UNOBTANIUM.get(), Items.NETHERITE_INGOT);

        netherWarpingRecipe(exporter, TWISTED_SAPLING.get(), ItemTags.SAPLINGS);
        netherWarpingRecipe(exporter, TWISTED_PLANKS.get(), ItemTags.PLANKS);
        netherWarpingRecipe(exporter, TWISTED_LOG.get(), ItemTags.LOGS);
        netherWarpingRecipe(exporter, TWISTED_LEAVES.get(), ItemTags.LEAVES);

        netherWarpingRecipe(exporter, MOONSTONE.get(), SUNSTONE.get());

        netherWarpingRecipe(exporter, HADALINE_TILES.get(), ABYSSALINE_TILES.get());
        netherWarpingRecipe(exporter, HADALINE_PLATING.get(), ABYSSALINE_PLATING.get());
        netherWarpingRecipe(exporter, HADALINE_PILLAR.get(), ABYSSALINE_PILLAR.get());
        netherWarpingRecipe(exporter, HADALINE_LAMP_BLOCK.get(), ABYSSALINE_LAMP_BLOCK.get());
        netherWarpingRecipe(exporter, HADALINE.get(), ABYSSALINE.get());
        netherWarpingRecipe(exporter, HADALINE_BRICKS.get(), ABYSSALINE_BRICKS.get());
        netherWarpingRecipe(exporter, CHISELED_HADALINE_BRICKS.get(), CHISELED_ABYSSALINE_BRICKS.get());

        netherWarpingRecipe(exporter, ENTRAILS.get(), ROTTEN_FLESH_BLOCK.get());
    }

    private void makeWoodRecipes(RecipeOutput exporter) {
        // (ender) this is messy, but it works
        woodFromLogs(exporter, TWISTED_WOOD.get(), TWISTED_LOG.get());
        woodFromLogs(exporter, STRIPPED_TWISTED_WOOD.get(), STRIPPED_TWISTED_LOG.get());

        trapdoorBuilder(TWISTED_TRAPDOOR.get(), ingredient(TWISTED_PLANKS))
                .unlockedBy(hasName(TWISTED_PLANKS), hasItem(TWISTED_PLANKS))
                .group("wooden_trapdoor").save(exporter);
        pressurePlateBuilder(RecipeCategory.REDSTONE, TWISTED_PRESSURE_PLATE.get(), ingredient(TWISTED_PLANKS))
                .unlockedBy(hasName(TWISTED_PLANKS), hasItem(TWISTED_PLANKS))
                .group("pressurePlate").save(exporter);
        planksFromLogs(exporter, TWISTED_PLANKS.get(), APTags.TWISTED_LOGS_ITEM, 4);
        fenceBuilder(TWISTED_FENCE.get(), ingredient(TWISTED_PLANKS))
                .unlockedBy(hasName(TWISTED_PLANKS), hasItem(TWISTED_PLANKS))
                .group("wooden_fence").save(exporter);
        fenceGateBuilder(TWISTED_FENCE_GATE.get(), ingredient(TWISTED_PLANKS))
                .unlockedBy(hasName(TWISTED_PLANKS), hasItem(TWISTED_PLANKS))
                .group("wooden_fence_gate").save(exporter);
        doorBuilder(TWISTED_DOOR.get(), ingredient(TWISTED_PLANKS))
                .unlockedBy(hasName(TWISTED_PLANKS), hasItem(TWISTED_PLANKS))
                .group("wooden_door").save(exporter);
        buttonBuilder(TWISTED_BUTTON.get(), ingredient(TWISTED_PLANKS))
                .unlockedBy(hasName(TWISTED_PLANKS), hasItem(TWISTED_PLANKS))
                .group("wooden_button").save(exporter);
    }

    private void makeWardstoneRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(MISC, WARDSTONE_BLEND.get(), 4)
                .requires(Items.NETHER_WART)
                .requires(Items.LAPIS_LAZULI)
                .requires(Items.NETHER_WART)
                .unlockedBy(getHasName(Items.NETHER_WART), has(Items.NETHER_WART))
                .save(exporter);

        brickRecipe(exporter, WARDSTONE.get(), WARDSTONE_BRICK.get(), 4);
        quickChiseledRecipe(exporter, CHISELED_WARDSTONE.get(), WARDSTONE.getPart(StoneBlockSet.SetComponent.SLAB), WARDSTONE.get());

        quickStoneCutting(exporter, WARDSTONE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), WARDSTONE.get(), 2);
        quickStoneCutting(exporter, WARDSTONE_BRICKS.getPart(StoneBlockSet.SetComponent.STAIRS), WARDSTONE.get());
        quickStoneCutting(exporter, WARDSTONE_BRICKS.getPart(StoneBlockSet.SetComponent.WALL), WARDSTONE.get());

        quickStoneCutting(exporter, WARDSTONE_BRICKS.get(), WARDSTONE.get());
        brickRecipe(exporter, WARDSTONE_BRICKS.get(), WARDSTONE.get(), 4);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, WARDSTONE_LAMP.get(), 4)
                .pattern("xxx")
                .pattern("x x")
                .pattern("xxx")
                .define('x', WARDSTONE_BRICKS.get())
                .unlockedBy(hasName(WARDSTONE_BRICKS), hasItem(WARDSTONE_BRICKS))
                .save(exporter);
        quickPillarRecipe(exporter, WARDSTONE_PILLAR.get(), WARDSTONE.get());
    }

    private void makeFlintRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, FLINT_BLOCK.get(), Items.FLINT, 4);
        brickRecipe(exporter, FLINT_TILES.get(), FLINT_BLOCK.get(), 4);
        quickStoneCutting(exporter, FLINT_TILES.get(), FLINT_BLOCK.get());

        quickStoneCutting(exporter, FLINT_TILES.getPart(StoneBlockSet.SetComponent.SLAB), FLINT_BLOCK.get(), 2);
        quickStoneCutting(exporter, FLINT_TILES.getPart(StoneBlockSet.SetComponent.STAIRS), FLINT_BLOCK.get());
        quickStoneCutting(exporter, FLINT_TILES.getPart(StoneBlockSet.SetComponent.WALL), FLINT_BLOCK.get());

        quickPillarRecipe(exporter, FLINT_PILLAR.get(), FLINT_BLOCK.get());
    }

    private void makeWitheredRecipes(RecipeOutput exporter) {
        // withered bone
        ShapelessRecipeBuilder.shapeless(MISC, Items.BONE_MEAL, 4)
                .requires(WITHERED_BONE.get())
                .unlockedBy(getHasName(WITHERED_BONE.get()), has(WITHERED_BONE.get()))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, WITHERED_BONE_BLOCK.get(), 3)
                .requires(WITHERED_BONE.get(), 9)
                .unlockedBy(getHasName(WITHERED_BONE.get()), has(WITHERED_BONE.get()))
                .save(exporter);
        ShapelessRecipeBuilder.shapeless(MISC, WITHERED_BONE.get(), 3)
                .requires(WITHERED_BONE_BLOCK.get())
                .unlockedBy(hasName(WITHERED_BONE_BLOCK), hasItem(WITHERED_BONE_BLOCK))
                .save(exporter);

        //wither lamp
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, WITHER_LAMP.get())
                .pattern("#d#")
                .pattern("#d#")
                .pattern("#d#")
                .define('#', APTags.WITHERED_BONES)
                .define('d', Items.GLOWSTONE_DUST)
                .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Items.GLOWSTONE_DUST))
                .save(exporter);

        // withered osseous blocks
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIT_WITHERED_OSSEOUS_SKULL.get())
                .pattern("X")
                .pattern("Y")
                .define('X', WITHERED_OSSEOUS_SKULL.get())
                .define('Y', Items.SOUL_TORCH)
                .unlockedBy(hasName(WITHERED_OSSEOUS_SKULL), hasItem(WITHERED_OSSEOUS_SKULL))
                .save(exporter);

        quickStoneCutting(exporter, WITHERED_OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.SLAB), WITHERED_BONE_BLOCK.get(), 2);
        quickStoneCutting(exporter, WITHERED_OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.STAIRS), WITHERED_BONE_BLOCK.get());
        quickStoneCutting(exporter, WITHERED_OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.WALL), WITHERED_BONE_BLOCK.get());
        quickStoneCutting(exporter, WITHERED_OSSEOUS_BRICK.get(), WITHERED_BONE_BLOCK.get());

        brickRecipe(exporter, WITHERED_OSSEOUS_BRICK.get(), WITHERED_BONE_BLOCK.get(), 4);
        quickStoneCutting(exporter, WITHERED_OSSEOUS_SKULL.get(), WITHERED_BONE_BLOCK.get());
        quickChiseledRecipe(exporter, WITHERED_OSSEOUS_SKULL.get(), WITHERED_OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.SLAB), WITHERED_OSSEOUS_BRICK.get());

        quickPillarRecipe(exporter, WITHERED_OSSEOUS_PILLAR.get(), WITHERED_OSSEOUS_BRICK.get());
        quickStoneCutting(exporter, WITHERED_OSSEOUS_PILLAR.get(), WITHERED_BONE_BLOCK.get());
    }

    private void makeOsseousRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, LIT_OSSEOUS_SKULL.get(), 1)
                .pattern("X")
                .pattern("Y")
                .define('X', OSSEOUS_SKULL.get())
                .define('Y', Items.TORCH)
                .unlockedBy(hasName(OSSEOUS_SKULL), hasItem(OSSEOUS_SKULL))
                .save(exporter);

        quickStoneCutting(exporter, OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.SLAB), Items.BONE_BLOCK, 2);
        quickStoneCutting(exporter, OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.STAIRS), Items.BONE_BLOCK);
        quickStoneCutting(exporter, OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.WALL), Items.BONE_BLOCK);
        quickStoneCutting(exporter, OSSEOUS_BRICK.get(), Items.BONE_BLOCK);

        brickRecipe(exporter, OSSEOUS_BRICK.get(), Items.BONE_BLOCK, 4);
        quickStoneCutting(exporter, OSSEOUS_SKULL.get(), Items.BONE_BLOCK);
        quickChiseledRecipe(exporter, OSSEOUS_SKULL.get(), OSSEOUS_BRICK.getPart(StoneBlockSet.SetComponent.SLAB), OSSEOUS_BRICK.get());

        quickPillarRecipe(exporter, OSSEOUS_PILLAR.get(), OSSEOUS_BRICK.get());
        quickStoneCutting(exporter, OSSEOUS_PILLAR.get(), Items.BONE_BLOCK);
    }

    private void makeNetherBrassRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(MISC, BRASS_BLEND.get(), 4)
                .requires(Items.SOUL_SAND, 2)
                .requires(Items.IRON_NUGGET)
                .requires(Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.SOUL_SAND), has(Items.SOUL_SAND))
                .save(exporter);

        ShapedRecipeBuilder.shaped(MISC, APItems.NETHER_BRASS.get(), 1)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', NETHER_BRASS_NUGGET.get())
                .unlockedBy(getHasName(NETHER_BRASS_NUGGET.get()), has(NETHER_BRASS_NUGGET.get()))
                .unlockedBy(getHasName(APItems.NETHER_BRASS.get()), has(APItems.NETHER_BRASS.get()))
                .save(exporter);
        ShapelessRecipeBuilder.shapeless(MISC, NETHER_BRASS_NUGGET.get(), 9)
                .requires(APItems.NETHER_BRASS.get())
                .unlockedBy(getHasName(APItems.NETHER_BRASS.get()), has(APItems.NETHER_BRASS.get()))
                .save(exporter);

        brickRecipe(exporter, APBlocks.NETHER_BRASS.get(), APItems.NETHER_BRASS.get(), 4);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, NETHER_BRASS_CHAIN.get(), 3)
                .pattern("n")
                .pattern("I")
                .pattern("n")
                .define('n', NETHER_BRASS_NUGGET.get())
                .define('I', APItems.NETHER_BRASS.get())
                .unlockedBy(getHasName(APItems.NETHER_BRASS.get()), has(APItems.NETHER_BRASS.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, APItems.NETHER_BRASS_TORCH.get(), 4)
                .pattern("n")
                .pattern("s")
                .define('n', NETHER_BRASS_NUGGET.get())
                .define('s', Items.STICK)
                .unlockedBy(getHasName(APItems.NETHER_BRASS.get()), has(APItems.NETHER_BRASS.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, NETHER_BRASS_LANTERN.get(), 1)
                .pattern("nnn")
                .pattern("ntn")
                .pattern("nnn")
                .define('n', NETHER_BRASS_NUGGET.get())
                .define('t', APItems.NETHER_BRASS_TORCH.get())
                .unlockedBy(getHasName(NETHER_BRASS_NUGGET.get()), has(NETHER_BRASS_NUGGET.get()))
                .unlockedBy(getHasName(APItems.NETHER_BRASS_TORCH.get()), has(APItems.NETHER_BRASS_TORCH.get()))
                .save(exporter);

        brickRecipe(exporter, CUT_NETHER_BRASS.get(), APBlocks.NETHER_BRASS.get(), 4);
        quickStoneCutting(exporter, CUT_NETHER_BRASS.get(), APBlocks.NETHER_BRASS.get());

        quickStoneCutting(exporter, CUT_NETHER_BRASS.getPart(StoneBlockSet.SetComponent.SLAB), APBlocks.NETHER_BRASS.get(), 2);
        quickStoneCutting(exporter, CUT_NETHER_BRASS.getPart(StoneBlockSet.SetComponent.STAIRS), APBlocks.NETHER_BRASS.get());
        quickStoneCutting(exporter, CUT_NETHER_BRASS.getPart(StoneBlockSet.SetComponent.WALL), APBlocks.NETHER_BRASS.get());

        quickPillarRecipe(exporter, NETHER_BRASS_PILLAR.get(), APBlocks.NETHER_BRASS.get());
    }

    private void makeSunmetalRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(MISC, SUNMETAL_BLEND.get(), 4)
                .requires(Items.SOUL_SAND, 2)
                .requires(Items.GOLD_NUGGET, 2)
                .unlockedBy(getHasName(Items.SOUL_SAND), has(Items.SOUL_SAND))
                .save(exporter);

        ShapedRecipeBuilder.shaped(MISC, SUNMETAL_BARS.get(), 16)
                .pattern("bbb")
                .pattern("bbb")
                .define('b', SUNMETAL_BRICK.get())
                .unlockedBy(getHasName(SUNMETAL_BRICK.get()), has(SUNMETAL_BRICK.get()))
                .save(exporter);

        brickRecipe(exporter, SUNMETAL.get(), SUNMETAL_BRICK.get(), 4);

        quickChiseledRecipe(exporter, CHISELED_SUNMETAL_BLOCK.get(), SUNMETAL.getPart(StoneBlockSet.SetComponent.SLAB), SUNMETAL.get());

        quickPillarRecipe(exporter, SUNMETAL_PILLAR.get(), SUNMETAL_BRICK.get());
    }

    private void makeBlackstoneRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(MISC, WEEPING_BLACKSTONE.get(), 1)
                .requires(Items.BLACKSTONE)
                .requires(Items.WEEPING_VINES)
                .unlockedBy(getHasName(Items.WEEPING_VINES), has(Items.WEEPING_VINES))
                .save(exporter);
        ShapelessRecipeBuilder.shapeless(MISC, WEEPING_BLACKSTONE_BRICKS.get(), 1)
                .requires(Items.POLISHED_BLACKSTONE_BRICKS)
                .requires(Items.WEEPING_VINES)
                .unlockedBy(getHasName(Items.WEEPING_VINES), has(Items.WEEPING_VINES))
                .save(exporter);
        quickStoneCutting(exporter, WEEPING_BLACKSTONE_BRICKS.get(), WEEPING_BLACKSTONE.get(), 1);

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, TWISTING_BLACKSTONE.get(), 1)
                .requires(Items.BLACKSTONE)
                .requires(Items.TWISTING_VINES)
                .unlockedBy(getHasName(Items.TWISTING_VINES), has(Items.TWISTING_VINES))
                .save(exporter);
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, TWISTING_BLACKSTONE_BRICKS.get(), 1)
                .requires(Items.POLISHED_BLACKSTONE_BRICKS)
                .requires(Items.TWISTING_VINES)
                .unlockedBy(getHasName(Items.TWISTING_VINES), has(Items.TWISTING_VINES))
                .save(exporter);
        quickStoneCutting(exporter, TWISTING_BLACKSTONE_BRICKS.get(), TWISTING_BLACKSTONE.get(), 1);

    }
}
