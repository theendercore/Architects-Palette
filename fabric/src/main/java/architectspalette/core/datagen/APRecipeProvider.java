package architectspalette.core.datagen;

import architectspalette.core.registry.APItems;
import architectspalette.core.registry.util.BlockNode;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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

        //Warping recipes
        quickWarpingRecipe(exporter, ESOTERRACK.get(), Blocks.ANDESITE, Level.NETHER);
        quickWarpingRecipe(exporter, ONYX.get(), Blocks.GRANITE, Level.NETHER);
        quickWarpingRecipe(exporter, NEBULITE.get(), Blocks.DIORITE, Level.NETHER);
        quickWarpingRecipe(exporter, MOONSHALE.get(), Blocks.STONE, Level.NETHER);
        quickWarpingRecipe(exporter, MOONSHALE.getChild(BRICKS).get(), Blocks.STONE_BRICKS, Level.NETHER);
        quickWarpingRecipe(exporter, CRATERSTONE.get(), Blocks.COBBLESTONE, Level.NETHER);

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

        quickBlastingRecipe(exporter, SUNMETAL_BRICK.get(), SUNMETAL_BLEND.get());
        quickBlastingRecipe(exporter, APItems.NETHER_BRASS.get(), BRASS_BLEND.get());

        quickSmeltingRecipe(exporter, MOONSHALE, CRATERSTONE);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MOONSHALE.getChild(SPECIAL).get(), 2)
                .pattern("s")
                .pattern("b")
                .pattern("s")
                .define('s', Blocks.STONE_SLAB)
                .define('b', MOONSHALE)
                .unlockedBy(getHasName(MOONSHALE), has(MOONSHALE))
                .save(exporter);
    }
}
