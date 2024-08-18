package architectspalette.core.datagen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.APTags;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import com.ibm.icu.impl.Pair;
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
import java.util.stream.Stream;

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
        makeAbyssalineRecipes(exporter);
        makeHadalineRecipes(exporter);
        //
        makeAlgalBrickRecipes(exporter);
        makeOreBrickRecipes(exporter);
        makeFlintRecipes(exporter);
        makePolishedPackedIceRecipes(exporter);
        makeSunmetalRecipes(exporter);
        makeOsseousRecipes(exporter);
        makeWitheredRecipes(exporter);
        makeEntwineRecipes(exporter);
        //
        //
        makeBlackstoneRecipes(exporter);
        //
        makeTwistedWoodRecipes(exporter);
        //
        makeCageLanternRecipes(exporter);
        //
        makeBoardRecipes(exporter);
        railingRecipes(exporter);
        makeDripstoneRecipes(exporter);
        makeCalciteRecipes(exporter);
        makeTuffRecipes(exporter);
        makeNetherBrassRecipes(exporter);
        makeEsoterrackRecipes(exporter);
        makeOnyxRecipes(exporter);
        makeWardstoneRecipes(exporter);
        makeAncientPlatingRecipes(exporter);
        makeNubRecipes(exporter);
        makeOracleRecipes(exporter);


        makeWarpingRecipes(exporter);
        smeltingRecipes(exporter);

        quickPillarRecipe(exporter, GILDED_SANDSTONE_PILLAR.get(), GILDED_SANDSTONE.get());
        quickPillarRecipe(exporter, OLIVESTONE_PILLAR.get(), OLIVESTONE_BRICK.get());
    }

    private void miscRecipes(RecipeOutput exporter) {
        //Plating & Pipe
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

        // Spool
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, SPOOL.get(), 2)
                .pattern("wfw")
                .define('f', ItemTags.WOODEN_FENCES)
                .define('w', Blocks.WHITE_WOOL)
                .unlockedBy(hasName(SPOOL), hasItem(SPOOL))
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


        // ENDER_PEARL_BLOCK
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


        //UNOBTANIUM
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


        // Hazard Sign
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HAZARD_SIGN.get(), 4)
                .pattern(" i ")
                .pattern("nin")
                .define('i', Items.IRON_INGOT)
                .define('n', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(exporter);
        quickStoneCutting(exporter, HAZARD_SIGN.get(), Items.IRON_BLOCK, 16);

        // Threaded Plate
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, TREAD_PLATE.get(), 4)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', Items.IRON_NUGGET)
                .define('y', PLATING_BLOCK.get())
                .unlockedBy(hasName(PLATING_BLOCK), hasItem(PLATING_BLOCK))
                .save(exporter);

        // Sheet Metal
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, SHEET_METAL.get(), 64)
                .pattern("x")
                .pattern("x")
                .define('x', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(exporter);
        quickStoneCutting(exporter, SHEET_METAL.get(), Items.IRON_INGOT, 4);
        quickStoneCutting(exporter, SHEET_METAL.get(), Items.IRON_BLOCK, 36);

        // Hazard Block
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HAZARD_BLOCK.get(), 4)
                .pattern("xy")
                .pattern("yx")
                .define('x', Blocks.YELLOW_CONCRETE)
                .define('y', Blocks.BLACK_CONCRETE)
                .unlockedBy(getHasName(Blocks.YELLOW_CONCRETE), has(Blocks.YELLOW_CONCRETE))
                .save(exporter);

        // Bread Block
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BREAD_BLOCK.get(), 9)
                .pattern("xxx")
                .define('x', Blocks.HAY_BLOCK)
                .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
                .save(exporter);

        // Cerebral Blocks
        brickRecipe(exporter, CEREBRAL_BLOCK.get(), CEREBRAL_PLATE.get(), 8);
        ShapelessRecipeBuilder.shapeless(MISC, CEREBRAL_PLATE.get(), 4)
                .requires(Blocks.TUFF)
                .requires(Items.QUARTZ)
                .requires(Items.CHARCOAL)
                .unlockedBy(getHasName(Items.TUFF), has(Items.TUFF))
                .save(exporter);

        // Moonshale Special
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MOONSHALE.getChild(SPECIAL).get(), 2)
                .pattern("s")
                .pattern("b")
                .pattern("s")
                .define('s', Blocks.STONE_SLAB)
                .define('b', MOONSHALE)
                .unlockedBy(getHasName(MOONSHALE), has(MOONSHALE.get()))
                .save(exporter);
    }

    private void makeAbyssalineRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ABYSSALINE.get(), 12)
                .pattern("O#O")
                .pattern("# #")
                .pattern("O#O")
                .define('#', Items.PRISMARINE_SHARD)
                .define('O', Items.OBSIDIAN)
                .unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
                .save(exporter);

        brickRecipe(exporter, ABYSSALINE_BRICKS.get(), ABYSSALINE.get(), 4);
        quickStoneCutting(exporter, ABYSSALINE_BRICKS.get(), ABYSSALINE.get());
        quickStoneCutting(exporter, ABYSSALINE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), ABYSSALINE.get(), 2);

        brickRecipe(exporter, ABYSSALINE_TILES.get(), ABYSSALINE_BRICKS.get(), 4);
        quickStoneCutting(exporter, ABYSSALINE_TILES.get(), ABYSSALINE_BRICKS.get());
        quickStoneCutting(exporter, ABYSSALINE_TILES.get(), ABYSSALINE.get());
        quickStoneCutting(exporter, ABYSSALINE_TILES.getPart(StoneBlockSet.SetComponent.SLAB), ABYSSALINE_BRICKS.get(), 2);
        quickStoneCutting(exporter, ABYSSALINE_TILES.getPart(StoneBlockSet.SetComponent.SLAB), ABYSSALINE.get(), 2);

        quickChiseledRecipe(exporter, CHISELED_ABYSSALINE_BRICKS.get(), ABYSSALINE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), ABYSSALINE_BRICKS.get());
        quickStoneCutting(exporter, CHISELED_ABYSSALINE_BRICKS.get(), ABYSSALINE.get());

        quickPillarRecipe(exporter, ABYSSALINE_PILLAR.get(), ABYSSALINE.get());

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ABYSSALINE_LAMP_BLOCK.get(), 8)
                .pattern("OCO")
                .pattern("#C#")
                .pattern("OCO")
                .define('#', Items.PRISMARINE_SHARD)
                .define('O', Items.OBSIDIAN)
                .define('C', Items.PRISMARINE_CRYSTALS)
                .unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ABYSSALINE_PLATING.get(), 8)
                .pattern("xxx")
                .pattern("x x")
                .pattern("xxx")
                .define('x', ABYSSALINE.get())
                .unlockedBy(getHasName(ABYSSALINE.get()), has(ABYSSALINE.get()))
                .save(exporter);
        quickStoneCutting(exporter, ABYSSALINE_PLATING.get(), ABYSSALINE.get());
    }

    private void makeHadalineRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, HADALINE_BRICKS.get(), HADALINE.get(), 4);
        quickStoneCutting(exporter, HADALINE_BRICKS.get(), HADALINE.get());
        quickStoneCutting(exporter, HADALINE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), HADALINE.get(), 2);

        brickRecipe(exporter, HADALINE_TILES.get(), HADALINE_BRICKS.get(), 4);
        quickStoneCutting(exporter, HADALINE_TILES.get(), HADALINE_BRICKS.get());
        quickStoneCutting(exporter, HADALINE_TILES.get(), HADALINE.get());
        quickStoneCutting(exporter, HADALINE_TILES.getPart(StoneBlockSet.SetComponent.SLAB), HADALINE_BRICKS.get(), 2);
        quickStoneCutting(exporter, HADALINE_TILES.getPart(StoneBlockSet.SetComponent.SLAB), HADALINE.get(), 2);

        quickChiseledRecipe(exporter, CHISELED_HADALINE_BRICKS.get(), HADALINE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), HADALINE_BRICKS.get());
        quickStoneCutting(exporter, CHISELED_HADALINE_BRICKS.get(), HADALINE.get());

        quickPillarRecipe(exporter, HADALINE_PILLAR.get(), HADALINE_BRICKS.get());
        quickStoneCutting(exporter, HADALINE_PILLAR.get(), HADALINE.get());

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HADALINE_PLATING.get(), 8)
                .pattern("xxx")
                .pattern("x x")
                .pattern("xxx")
                .define('x', HADALINE.get())
                .unlockedBy(getHasName(HADALINE.get()), has(HADALINE.get()))
                .save(exporter);
        quickStoneCutting(exporter, HADALINE_PLATING.get(), HADALINE.get());

    }

    private void makeAlgalBrickRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(MISC, ALGAL_BLEND.get(), 2)
                .requires(Items.CLAY_BALL)
                .requires(Items.KELP)
                .unlockedBy(getHasName(Items.CLAY_BALL), has(Items.CLAY_BALL))
                .save(exporter);

        brickRecipe(exporter, APBlocks.ALGAL_BRICK.get(), APItems.ALGAL_BRICK.get(), 4);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ALGAL_LAMP.get(), 1)
                .pattern("xdx")
                .pattern("ddd")
                .pattern("xdx")
                .define('d', Items.GLOWSTONE_DUST)
                .define('x', APItems.ALGAL_BRICK.get())
                .unlockedBy(getHasName(APItems.ALGAL_BRICK.get()), has(APItems.ALGAL_BRICK.get()))
                .save(exporter);

        quickChiseledRecipe(exporter, CHISELED_ALGAL_BRICKS.get(), APBlocks.ALGAL_BRICK.getPart(StoneBlockSet.SetComponent.SLAB), APBlocks.ALGAL_BRICK.get());

        ShapelessRecipeBuilder.shapeless(MISC, OVERGROWN_ALGAL_BRICK.get(), 1)
                .requires(APBlocks.ALGAL_BRICK.get())
                .requires(Items.KELP)
                .unlockedBy(getHasName(APBlocks.ALGAL_BRICK.get()), has(APBlocks.ALGAL_BRICK.get()))
                .save(exporter);
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

    private void makeFlintRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, FLINT_BLOCK.get(), Items.FLINT, 4);
        brickRecipe(exporter, FLINT_TILES.get(), FLINT_BLOCK.get(), 4);
        quickStoneCutting(exporter, FLINT_TILES.get(), FLINT_BLOCK.get());

        quickStoneCutting(exporter, FLINT_TILES.getPart(StoneBlockSet.SetComponent.SLAB), FLINT_BLOCK.get(), 2);
        quickStoneCutting(exporter, FLINT_TILES.getPart(StoneBlockSet.SetComponent.STAIRS), FLINT_BLOCK.get());
        quickStoneCutting(exporter, FLINT_TILES.getPart(StoneBlockSet.SetComponent.WALL), FLINT_BLOCK.get());

        quickPillarRecipe(exporter, FLINT_PILLAR.get(), FLINT_BLOCK.get());
    }

    private void makePolishedPackedIceRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, POLISHED_PACKED_ICE.get(), Blocks.PACKED_ICE, 4);
        quickStoneCutting(exporter, POLISHED_PACKED_ICE.get(), Blocks.PACKED_ICE);

        quickStoneCutting(exporter, POLISHED_PACKED_ICE.getPart(StoneBlockSet.SetComponent.SLAB), Blocks.PACKED_ICE, 2);
        quickStoneCutting(exporter, POLISHED_PACKED_ICE.getPart(StoneBlockSet.SetComponent.STAIRS), Blocks.PACKED_ICE);
        quickStoneCutting(exporter, POLISHED_PACKED_ICE.getPart(StoneBlockSet.SetComponent.WALL), Blocks.PACKED_ICE);

        quickChiseledRecipe(exporter, CHISELED_PACKED_ICE.get(), POLISHED_PACKED_ICE.getPart(StoneBlockSet.SetComponent.SLAB), POLISHED_PACKED_ICE.get());
        quickStoneCutting(exporter, CHISELED_PACKED_ICE.get(), Blocks.PACKED_ICE);

        quickPillarRecipe(exporter, PACKED_ICE_PILLAR.get(), POLISHED_PACKED_ICE.get());
        quickStoneCutting(exporter, PACKED_ICE_PILLAR.get(), Blocks.PACKED_ICE);
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
                .pattern("d#d")
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

    private void makeEntwineRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ENTWINE_ROD.get(), 4)
                .pattern("XYX")
                .define('X', Items.IRON_NUGGET)
                .define('Y', Items.ENDER_PEARL)
                .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.ENDER_PEARL))
                .save(exporter);

        brickRecipe(exporter, ENTWINE.get(), ENTWINE_ROD.get(), 4);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ENTWINE_BARS.get(), 16)
                .pattern("###")
                .pattern("###")
                .define('#', ENTWINE_ROD.get())
                .unlockedBy(getHasName(ENTWINE_ROD.get()), has(ENTWINE_ROD.get()))
                .save(exporter);

        quickChiseledRecipe(exporter, CHISELED_ENTWINE.get(), ENTWINE.getPart(StoneBlockSet.SetComponent.SLAB), ENTWINE.get());


        quickPillarRecipe(exporter, ENTWINE_PILLAR.get(), ENTWINE.get());
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


    private void makeTwistedWoodRecipes(RecipeOutput exporter) {
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


    private void makeCageLanternRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ALGAL_CAGE_LANTERN.get(), 1)
                .pattern(" g ")
                .pattern("blb")
                .define('g', Items.GLASS_PANE)
                .define('l', Items.GLOWSTONE_DUST)
                .define('b', APItems.ALGAL_BRICK.get())
                .unlockedBy(getHasName(APItems.ALGAL_BRICK.get()), has(APItems.ALGAL_BRICK.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, GLOWSTONE_CAGE_LANTERN.get(), 1)
                .pattern(" g ")
                .pattern("blb")
                .define('g', Items.GLASS_PANE)
                .define('l', Items.GLOWSTONE_DUST)
                .define('b', APTags.WITHERED_BONES)
                .unlockedBy(getHasName(APItems.WITHERED_BONE.get()), has(APTags.WITHERED_BONES))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, REDSTONE_CAGE_LANTERN.get(), 1)
                .pattern(" g ")
                .pattern("blb")
                .define('g', Items.GLASS_PANE)
                .define('l', Items.REDSTONE)
                .define('b', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(exporter);
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

    private void makeDripstoneRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, DRIPSTONE_BRICKS.get(), Blocks.DRIPSTONE_BLOCK, 4);
        quickStoneCutting(exporter, DRIPSTONE_BRICKS.get(), Blocks.DRIPSTONE_BLOCK);

        quickStoneCutting(exporter, DRIPSTONE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), Blocks.DRIPSTONE_BLOCK, 2);
        quickStoneCutting(exporter, DRIPSTONE_BRICKS.getPart(StoneBlockSet.SetComponent.STAIRS), Blocks.DRIPSTONE_BLOCK);
        quickStoneCutting(exporter, DRIPSTONE_BRICKS.getPart(StoneBlockSet.SetComponent.WALL), Blocks.DRIPSTONE_BLOCK);

        quickChiseledRecipe(exporter, CHISELED_DRIPSTONE.get(), DRIPSTONE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), DRIPSTONE_BRICKS.get());
        quickStoneCutting(exporter, CHISELED_DRIPSTONE.get(), Blocks.DRIPSTONE_BLOCK);

        quickPillarRecipe(exporter, DRIPSTONE_PILLAR.get(), DRIPSTONE_BRICKS.get());
        quickStoneCutting(exporter, DRIPSTONE_PILLAR.get(), Blocks.DRIPSTONE_BLOCK);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HEAVY_DRIPSTONE_BRICKS.get(), 9)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', DRIPSTONE_BRICKS.get())
                .unlockedBy(getHasName(DRIPSTONE_BRICKS.get()), has(DRIPSTONE_BRICKS.get()))
                .save(exporter);
        quickStoneCutting(exporter, HEAVY_DRIPSTONE_BRICKS.get(), DRIPSTONE_BRICKS.get());

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, DRIPSTONE_LAMP.get(), 1)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', DRIPSTONE_BRICKS.get())
                .define('y', Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(DRIPSTONE_BRICKS.get()), has(DRIPSTONE_BRICKS.get()))
                .save(exporter);
    }

    private void makeCalciteRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, CALCITE_BRICKS.get(), Blocks.CALCITE, 4);
        quickStoneCutting(exporter, CALCITE_BRICKS.get(), Blocks.CALCITE);

        quickStoneCutting(exporter, CALCITE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), Blocks.CALCITE, 2);
        quickStoneCutting(exporter, CALCITE_BRICKS.getPart(StoneBlockSet.SetComponent.STAIRS), Blocks.CALCITE);
        quickStoneCutting(exporter, CALCITE_BRICKS.getPart(StoneBlockSet.SetComponent.WALL), Blocks.CALCITE);

        quickChiseledRecipe(exporter, CHISELED_CALCITE.get(), CALCITE_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), CALCITE_BRICKS.get());
        quickStoneCutting(exporter, CHISELED_CALCITE.get(), Blocks.CALCITE);

        quickPillarRecipe(exporter, CALCITE_PILLAR.get(), CALCITE_BRICKS.get());
        quickStoneCutting(exporter, CALCITE_PILLAR.get(), Blocks.CALCITE);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HEAVY_CALCITE_BRICKS.get(), 9)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CALCITE_BRICKS.get())
                .unlockedBy(getHasName(CALCITE_BRICKS.get()), has(CALCITE_BRICKS.get()))
                .save(exporter);
        quickStoneCutting(exporter, HEAVY_CALCITE_BRICKS.get(), CALCITE_BRICKS.get());

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, CALCITE_LAMP.get(), 1)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', CALCITE_BRICKS.get())
                .define('y', Items.GLOW_INK_SAC)
                .unlockedBy(getHasName(CALCITE_BRICKS.get()), has(CALCITE_BRICKS.get()))
                .save(exporter);
    }

    private void makeTuffRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, TUFF_BRICKS.get(), 8)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.TUFF)
                .unlockedBy(getHasName(Blocks.TUFF), has(Blocks.TUFF))
                .save(exporter);
        quickStoneCutting(exporter, TUFF_BRICKS.get(), Blocks.TUFF);

        quickStoneCutting(exporter, TUFF_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), Blocks.TUFF, 2);
        quickStoneCutting(exporter, TUFF_BRICKS.getPart(StoneBlockSet.SetComponent.STAIRS), Blocks.TUFF);
        quickStoneCutting(exporter, TUFF_BRICKS.getPart(StoneBlockSet.SetComponent.WALL), Blocks.TUFF);

        quickPillarRecipe(exporter, TUFF_PILLAR.get(), TUFF_BRICKS.get());
        quickStoneCutting(exporter, TUFF_PILLAR.get(), Blocks.TUFF);

        quickChiseledRecipe(exporter, CHISELED_TUFF.get(), TUFF_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), TUFF_BRICKS.get());
        quickStoneCutting(exporter, CHISELED_TUFF.get(), Blocks.TUFF);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HEAVY_TUFF_BRICKS.get(), 9)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', TUFF_BRICKS.get())
                .unlockedBy(getHasName(TUFF_BRICKS.get()), has(TUFF_BRICKS.get()))
                .save(exporter);
        quickStoneCutting(exporter, HEAVY_TUFF_BRICKS.get(), TUFF_BRICKS.get());

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, TUFF_LAMP.get(), 1)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', Blocks.TUFF)
                .define('y', Items.GLOW_LICHEN)
                .unlockedBy(getHasName(Blocks.TUFF), has(Blocks.TUFF))
                .save(exporter);
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

    private void makeEsoterrackRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, ESOTERRACK_BRICKS.get(), ESOTERRACK.get(), 4);

        quickStoneCutting(exporter, ESOTERRACK_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), ESOTERRACK.get(), 2);
        quickStoneCutting(exporter, ESOTERRACK_BRICKS.getPart(StoneBlockSet.SetComponent.STAIRS), ESOTERRACK.get());
        quickStoneCutting(exporter, ESOTERRACK_BRICKS.getPart(StoneBlockSet.SetComponent.WALL), ESOTERRACK.get());

        quickPillarRecipe(exporter, ESOTERRACK_PILLAR.get(), ESOTERRACK_BRICKS.get());
        quickStoneCutting(exporter, ESOTERRACK_PILLAR.get(), ESOTERRACK.get());
    }

    private void makeOnyxRecipes(RecipeOutput exporter) {
        brickRecipe(exporter, ONYX_BRICKS.get(), ONYX.get(), 4);

        quickStoneCutting(exporter, ONYX_BRICKS.getPart(StoneBlockSet.SetComponent.SLAB), ONYX.get(), 2);
        quickStoneCutting(exporter, ONYX_BRICKS.getPart(StoneBlockSet.SetComponent.STAIRS), ONYX.get());
        quickStoneCutting(exporter, ONYX_BRICKS.getPart(StoneBlockSet.SetComponent.WALL), ONYX.get());

        quickPillarRecipe(exporter, ONYX_PILLAR.get(), ONYX_BRICKS.get());
        quickStoneCutting(exporter, ONYX_PILLAR.get(), ONYX.get());
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

    private void makeAncientPlatingRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ANCIENT_PLATING.get(), 64)
                .pattern("ini")
                .pattern("nsn")
                .pattern("ini")
                .define('i', APItems.NETHER_BRASS.get())
                .define('n', NETHER_BRASS_NUGGET.get())
                .define('s', Items.NETHERITE_SCRAP)
                .unlockedBy(getHasName(APItems.NETHER_BRASS.get()), has(APItems.NETHER_BRASS.get()))
                .save(exporter);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ANCIENT_PLATING.getPart(StoneBlockSet.SetComponent.FENCE), 6)
                .pattern("#I#")
                .pattern("#I#")
                .define('I', NETHER_BRASS_NUGGET.get())
                .define('#', ANCIENT_PLATING.get())
                .unlockedBy(getHasName(NETHER_BRASS_NUGGET.get()), has(NETHER_BRASS_NUGGET.get()))
                .save(exporter);

    }

    private void makeNubRecipes(RecipeOutput exporter) {
        Stream.of(
                Pair.of(STONE_NUB, Blocks.STONE),
                Pair.of(SMOOTH_STONE_NUB, Blocks.SMOOTH_STONE),
                Pair.of(SANDSTONE_NUB, Blocks.SANDSTONE),
                Pair.of(ANDESITE_NUB, Blocks.POLISHED_ANDESITE),
                Pair.of(GRANITE_NUB, Blocks.POLISHED_GRANITE),
                Pair.of(DIORITE_NUB, Blocks.POLISHED_DIORITE),
                Pair.of(BLACKSTONE_NUB, Blocks.POLISHED_BLACKSTONE),
                Pair.of(DEEPSLATE_NUB, Blocks.POLISHED_DEEPSLATE),
                Pair.of(BONE_NUB, Blocks.BONE_BLOCK),
                Pair.of(NUB_OF_ENDER, Items.ENDER_EYE),
                Pair.of(IRON_NUB, Items.IRON_INGOT),
                Pair.of(GOLD_NUB, Items.GOLD_INGOT),
                Pair.of(DIAMOND_NUB, Items.DIAMOND),
                Pair.of(EMERALD_NUB, Items.EMERALD),
                Pair.of(NETHERITE_NUB, Items.NETHERITE_INGOT),
                //Copper Nubs
                Pair.of(COPPER_NUB, Blocks.CUT_COPPER),
                Pair.of(WAXED_COPPER_NUB, Blocks.WAXED_CUT_COPPER),
                Pair.of(EXPOSED_COPPER_NUB, Blocks.EXPOSED_CUT_COPPER),
                Pair.of(WAXED_EXPOSED_COPPER_NUB, Blocks.WAXED_EXPOSED_CUT_COPPER),
                Pair.of(WEATHERED_COPPER_NUB, Blocks.WEATHERED_CUT_COPPER),
                Pair.of(WAXED_WEATHERED_COPPER_NUB, Blocks.WAXED_WEATHERED_CUT_COPPER),
                Pair.of(OXIDIZED_COPPER_NUB, Blocks.OXIDIZED_CUT_COPPER),
                Pair.of(WAXED_OXIDIZED_COPPER_NUB, Blocks.WAXED_OXIDIZED_CUT_COPPER)
        ).forEach((pair) -> quickStoneCutting(exporter, pair.first.get(), pair.second.asItem(), 2));

        //Waxing recipes
        Stream.of(
                Pair.of(WAXED_COPPER_NUB, COPPER_NUB),
                Pair.of(WAXED_EXPOSED_COPPER_NUB, EXPOSED_COPPER_NUB),
                Pair.of(WAXED_WEATHERED_COPPER_NUB, WEATHERED_COPPER_NUB),
                Pair.of(WAXED_OXIDIZED_COPPER_NUB, OXIDIZED_COPPER_NUB)
        ).forEach((pair) -> quickWaxing(exporter, pair.first.get(), pair.second.get()));
    }

    private void makeOracleRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(MISC, ORACLE_JELLY.get(), 4)
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(exporter);

        brickRecipe(exporter, ORACLE_BLOCK.get(), ORACLE_JELLY.get(), 8);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(SPECIAL).get(), 2)
                .pattern("xyx")
                .define('x', CEREBRAL_PLATE.get())
                .define('y', ORACLE_BLOCK.get())
                .unlockedBy(getHasName(ORACLE_BLOCK), has(ORACLE_BLOCK.get()))
                .save(exporter);


        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(LAMP).get(), 2)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', ORACLE_JELLY.get())
                .define('y', Items.END_ROD)
                .unlockedBy(getHasName(ORACLE_BLOCK), has(ORACLE_BLOCK.get()))
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
        quickSmeltingRecipe(exporter, HEAVY_CRACKED_END_STONE_BRICKS.get(), HEAVY_END_STONE_BRICKS.get());
        quickSmeltingRecipe(exporter, HEAVY_CRACKED_STONE_BRICKS.get(), HEAVY_STONE_BRICKS.get());


        quickSmokingRecipe(exporter, APBlocks.CHARCOAL_BLOCK.get(), ItemTags.LOGS_THAT_BURN);


        quickBlastingRecipe(exporter, SUNMETAL_BRICK.get(), SUNMETAL_BLEND.get());
        quickBlastingRecipe(exporter, APItems.NETHER_BRASS.get(), BRASS_BLEND.get());
        quickBlastingRecipe(exporter, SMOOTH_NETHER_BRASS.get(), APBlocks.NETHER_BRASS.get());
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

}
