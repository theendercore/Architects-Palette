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
import net.minecraft.world.item.crafting.Ingredient;
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


        makeWarpingRecipes(exporter);
        smeltingRecipes(exporter);
        makePillarRecipes(exporter);
        makeOreBrickRecipes(exporter);
        makeBoardRecipes(exporter);
        railingRecipes(exporter);
        makeWoodRecipes(exporter);


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
                .unlockedBy(getHasName(PLATING_BLOCK.get()), has(PLATING_BLOCK.get()))
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

        brickRecipe(ORACLE_BLOCK.get(), ORACLE_JELLY.get(), 8, exporter);
        brickRecipe(CEREBRAL_BLOCK.get(), CEREBRAL_PLATE.get(), 8, exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(SPECIAL).get(), 2)
                .pattern("xyx")
                .define('x', CEREBRAL_PLATE.get())
                .define('y', ORACLE_BLOCK.get())
                .unlockedBy(getHasName(ORACLE_BLOCK.get()), has(ORACLE_BLOCK.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, SPOOL.get(), 2)
                .pattern("wfw")
                .define('f', ItemTags.WOODEN_FENCES)
                .define('w', Blocks.WHITE_WOOL)
                .unlockedBy(getHasName(SPOOL.get()), has(SPOOL.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(LAMP).get(), 2)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', ORACLE_JELLY.get())
                .define('y', Items.END_ROD)
                .unlockedBy(getHasName(ORACLE_BLOCK.get()), has(ORACLE_BLOCK.get()))
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
                .unlockedBy(getHasName(MOONSHALE), has(MOONSHALE))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, UNOBTANIUM_BLOCK.get(), 1)
                .pattern("xx")
                .pattern("xx")
                .define('x', UNOBTANIUM.get())
                .unlockedBy(getHasName(UNOBTANIUM_BLOCK.get()), has(UNOBTANIUM_BLOCK.get()))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, UNOBTANIUM.get(), 5)
                .requires(UNOBTANIUM_BLOCK.get())
                .unlockedBy(getHasName(UNOBTANIUM.get()), has(UNOBTANIUM.get()))
                .save(exporter);

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

        ShapelessRecipeBuilder.shapeless(MISC, Items.BONE_MEAL, 4)
                .requires(WITHERED_BONE.get())
                .unlockedBy(getHasName(WITHERED_BONE.get()), has(WITHERED_BONE.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ROTTEN_FLESH_BLOCK.get(), 1)
                .pattern("xxx")
                .pattern("xxx")
                .pattern("xxx")
                .define('x', Items.ROTTEN_FLESH)
                .unlockedBy(getHasName(ROTTEN_FLESH_BLOCK.get()), has(ROTTEN_FLESH_BLOCK.get()))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(MISC, Items.ROTTEN_FLESH, 9)
                .requires(ROTTEN_FLESH_BLOCK.get())
                .unlockedBy(getHasName(ROTTEN_FLESH_BLOCK.get()), has(ROTTEN_FLESH_BLOCK.get()))
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
        quickPillarRecipe(exporter, NETHER_BRASS_PILLAR.get(), APBlocks.NETHER_BRASS.get());
        quickPillarRecipe(exporter, OLIVESTONE_PILLAR.get(), OLIVESTONE_BRICK.get());
        quickPillarRecipe(exporter, ONYX_PILLAR.get(), ONYX_BRICKS.get());
        quickPillarRecipe(exporter, OSSEOUS_PILLAR.get(), OSSEOUS_BRICK.get());
        quickPillarRecipe(exporter, PACKED_ICE_PILLAR.get(), POLISHED_PACKED_ICE.get());
        quickPillarRecipe(exporter, SUNMETAL_PILLAR.get(), SUNMETAL.get());
        quickPillarRecipe(exporter, TUFF_PILLAR.get(), TUFF_BRICKS.get());
        quickPillarRecipe(exporter, WARDSTONE_PILLAR.get(), WARDSTONE.get());
        quickPillarRecipe(exporter, WITHERED_OSSEOUS_PILLAR.get(), WITHERED_OSSEOUS_BRICK.get());
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
        woodFromLogs(exporter,  TWISTED_WOOD.get(), TWISTED_LOG.get());
        woodFromLogs(exporter,  STRIPPED_TWISTED_WOOD.get(), STRIPPED_TWISTED_LOG.get());

        trapdoorBuilder(TWISTED_TRAPDOOR.get(), Ingredient.of(TWISTED_PLANKS.get()))
                .unlockedBy(getHasName(TWISTED_PLANKS.get()), has(TWISTED_PLANKS.get()))
                .group("wooden_trapdoor").save(exporter);
        pressurePlateBuilder(RecipeCategory.REDSTONE,TWISTED_PRESSURE_PLATE.get(), Ingredient.of(TWISTED_PLANKS.get()))
                .unlockedBy(getHasName(TWISTED_PLANKS.get()), has(TWISTED_PLANKS.get()))
                .group("pressurePlate").save(exporter);
        planksFromLogs(exporter, TWISTED_PLANKS.get(), APTags.TWISTED_LOGS_ITEM, 4);
        fenceBuilder( TWISTED_FENCE.get(), Ingredient.of(TWISTED_PLANKS.get()))
                .unlockedBy(getHasName(TWISTED_PLANKS.get()), has(TWISTED_PLANKS.get()))
                .group("wooden_fence").save(exporter);
        fenceGateBuilder(TWISTED_FENCE_GATE.get(), Ingredient.of(TWISTED_PLANKS.get()))
                .unlockedBy(getHasName(TWISTED_PLANKS.get()), has(TWISTED_PLANKS.get()))
                .group("wooden_fence_gate").save(exporter);
        doorBuilder(TWISTED_DOOR.get(), Ingredient.of(TWISTED_PLANKS.get()))
                .unlockedBy(getHasName(TWISTED_PLANKS.get()), has(TWISTED_PLANKS.get()))
                .group("wooden_door").save(exporter);
        buttonBuilder(TWISTED_BUTTON.get(), Ingredient.of(TWISTED_PLANKS.get()))
                .unlockedBy(getHasName(TWISTED_PLANKS.get()), has(TWISTED_PLANKS.get()))
                .group("wooden_button").save(exporter);
    }
}
