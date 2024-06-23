package architectspalette.core.datagen;

import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.BlockNode.BlockType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static architectspalette.core.ArchitectsPalette.rl;
import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.APItems.*;
import static architectspalette.core.registry.util.BlockNode.BlockType.*;
import static net.minecraft.data.recipes.RecipeCategory.*;

public class APRecipes extends RecipeProvider {
    public APRecipes(PackOutput pack, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pack, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        BlockNode.forAllBaseNodes((node) -> processBlockNode(output, node));

        //Warping recipes
        quickWarpingRecipe(output, ESOTERRACK.get(), Blocks.ANDESITE, Level.NETHER);
        quickWarpingRecipe(output, ONYX.get(), Blocks.GRANITE, Level.NETHER);
        quickWarpingRecipe(output, NEBULITE.get(), Blocks.DIORITE, Level.NETHER);
        quickWarpingRecipe(output, MOONSHALE.get(), Blocks.STONE, Level.NETHER);
        quickWarpingRecipe(output, MOONSHALE.getChild(BRICKS).get(), Blocks.STONE_BRICKS, Level.NETHER);
        quickWarpingRecipe(output, CRATERSTONE.get(), Blocks.COBBLESTONE, Level.NETHER);

        //Base recipes for blocks
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, SHEET_METAL.get(), 64)
                .pattern("x")
                .pattern("x")
                .define('x', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(output);

        quickStonecuttingRecipe(output, Items.IRON_INGOT, SHEET_METAL.get(), 4);
        quickStonecuttingRecipe(output, Items.IRON_BLOCK, SHEET_METAL.get(), 36);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, TREAD_PLATE.get(), 4)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', Items.IRON_NUGGET)
                .define('y', PLATING_BLOCK.get())
                .unlockedBy(getHasName(PLATING_BLOCK.get()), has(PLATING_BLOCK.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, HAZARD_BLOCK.get(), 4)
                .pattern("xy")
                .pattern("yx")
                .define('x', Blocks.YELLOW_CONCRETE)
                .define('y', Blocks.BLACK_CONCRETE)
                .unlockedBy(getHasName(Blocks.YELLOW_CONCRETE), has(Blocks.YELLOW_CONCRETE))
                .save(output);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, BREAD_BLOCK.get(), 9)
                .pattern("xxx")
                .define('x', Blocks.HAY_BLOCK)
                .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
                .save(output);

        brickRecipe(ORACLE_BLOCK.get(), ORACLE_JELLY.get(), 8, output);
        brickRecipe(CEREBRAL_BLOCK.get(), CEREBRAL_PLATE.get(), 8, output);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(SPECIAL).get(), 2)
                .pattern("xyx")
                .define('x', CEREBRAL_PLATE.get())
                .define('y', ORACLE_BLOCK.get())
                .unlockedBy(getHasName(ORACLE_BLOCK.get()), has(ORACLE_BLOCK.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, ORACLE_BLOCK.getChild(LAMP).get(), 2)
                .pattern(" x ")
                .pattern("xyx")
                .pattern(" x ")
                .define('x', ORACLE_JELLY.get())
                .define('y', Items.END_ROD)
                .unlockedBy(getHasName(ORACLE_BLOCK.get()), has(ORACLE_BLOCK.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(MISC, ORACLE_JELLY.get(), 4)
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(output);

        ShapelessRecipeBuilder.shapeless(MISC, CEREBRAL_PLATE.get(), 4)
                .requires(Blocks.TUFF)
                .requires(Items.QUARTZ)
                .requires(Items.CHARCOAL)
                .unlockedBy(getHasName(Items.TUFF), has(Items.TUFF))
                .save(output);

        quickBlastingRecipe(output, SUNMETAL_BRICK.get(), SUNMETAL_BLEND.get());
        quickBlastingRecipe(output, APItems.NETHER_BRASS.get(), BRASS_BLEND.get());

        quickSmeltingRecipe(output, MOONSHALE, CRATERSTONE);
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, MOONSHALE.getChild(SPECIAL).get(), 2)
                .pattern("s")
                .pattern("b")
                .pattern("s")
                .define('s', Blocks.STONE_SLAB)
                .define('b', MOONSHALE)
                .unlockedBy(getHasName(MOONSHALE), has(MOONSHALE))
                .save(output);

    }

    private static void processBlockNode(RecipeOutput output, BlockNode node) {
        String hasBase = "has_" + node.getName();
        node.forEach((n -> {
            if (n.parent != null && !n.getFlag(BlockNode.ExcludeFlag.RECIPES)) {
                Block block = n.get();
                Block parent = n.parent.get();

                int stoneCuttingCount = getStoneCuttingCount(n.type);
                switch(n.type) {
                    case BRICKS, POLISHED -> {
                        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 4)
                                .pattern("xx")
                                .pattern("xx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output);
                        //Tile conversion recipe
                        var tiles = n.getSibling(TILES);
                        if (tiles != null) {
                            //brickRecipe(block, tiles.get(), 4, output);
                            ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', tiles.get())
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                    .save(output, modId(n.getName() + "_from_" + tiles.getId().getPath()));
                        }
                    }
                    case CRACKED -> {
                        SimpleCookingRecipeBuilder.smelting(Ingredient.of(parent), BUILDING_BLOCKS, block, .1f, 200)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output, smeltingName(block, parent));
                    }
                    case MOSSY -> {
                    }
                    case TILES -> {
                        //Brick conversion recipe
                        var bricks = n.getSibling(BRICKS);
                        if (bricks == null) bricks = n.getSibling(POLISHED);
                        if (bricks != null) {
                            ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', bricks.get())
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                    .save(output, modId(n.getName() + "_from_" + bricks.getId().getPath()));
                        }
                        //Default recipe if there are no bricks
                        else {
                            ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 4)
                                    .pattern("xx")
                                    .pattern("xx")
                                    .define('x', parent)
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                    .save(output);
                        }
                    }
                    case CHISELED -> {
                        var slab = n.getSibling(SLAB);
                        if (slab != null) {
                            ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 1)
                                    .pattern("x")
                                    .pattern("x")
                                    .define('x', slab.get())
                                    .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                    .save(output);
                        }
                    }
                    case PILLAR -> {
                        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 2)
                                .pattern("x")
                                .pattern("x")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output);
                    }
                    case NUB -> {

                    }
                    case SLAB -> {
                        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 6)
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output);
                    }
                    case VERTICAL_SLAB -> {
                        //Special case with stoneCuttingCount here. The slabs make their own conditional stonecutting recipe
                        stoneCuttingCount = 0;
                        var slab = n.getSibling(SLAB).get();
                        //Craft from slabs
                        //I have no idea how this shit works
                        ConditionalRecipe.builder().mainCondition(APVerticalSlabsCondition.instance)
                                .recipe((c) -> {
                                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 3)
                                            .pattern("x")
                                            .pattern("x")
                                            .pattern("x")
                                            .group("vertical_slab")
                                            .define('x', slab)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                            .save(c);
                                })
                                .save(output, modId("vslabs/" + n.getName()));
                        //Revert to slab from vslab
                        ConditionalRecipe.builder().mainCondition(APVerticalSlabsCondition.instance)
                                .recipe((c) -> {
                                    ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, slab, 1)
                                            .group("vertical_slab_revert")
                                            .requires(block)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                            .save(c);
                                })
                                .save(output, modId("vslabs/" + n.getName() + "_revert"));
                        //Stonecut from block to vslab
                        ConditionalRecipe.builder().mainCondition(APVerticalSlabsCondition.instance)
                                .recipe((c) -> {
                                    SingleItemRecipeBuilder.stonecutting(getStonecuttingIngredients(n), BUILDING_BLOCKS, block, 2)
                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                            .save(c);
                                })
                                .save(output, modId("vslabs/stonecutting/" + n.getName()));


                    }
                    case STAIRS -> {
                        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 4)
                                .pattern("x  ")
                                .pattern("xx ")
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output);
                    }
                    case WALL -> {
                        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 6)
                                .pattern("xxx")
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output);
                    }
                    case FENCE -> {
                        //TODO: fence recipe
                    }
                    case PLATING -> {
                        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 8)
                                .pattern("xxx")
                                .pattern("x x")
                                .pattern("xxx")
                                .define('x', parent)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output);
                    }
                    case DARK ->
                        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 8)
                                .pattern("xxx")
                                .pattern("xdx")
                                .pattern("xxx")
                                .define('x', parent)
                                .define('d', Items.BLACK_DYE)
                                .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                                .save(output);
                }
                if (stoneCuttingCount > 0 && (node.tool != BlockNode.Tool.AXE || n.type == NUB)) {
                    SingleItemRecipeBuilder.stonecutting(getStonecuttingIngredients(n), BUILDING_BLOCKS, block, stoneCuttingCount)
                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
                            .save(output, cuttingName(block, parent));
                }

            }

        }));
    }

    private static ResourceLocation smeltingName(ItemLike item, ItemLike from) {
        return rl("smelting/" + getItemName(item));
    }

    private static final Map<String, Boolean> stonecuttingMap = new HashMap<>();
    private static ResourceLocation cuttingName(ItemLike item, ItemLike from) {
        var string = getItemName(item);
        if (stonecuttingMap.put(string, true) != null) {
            string += "_from_" + getItemName(from);
        }
        return rl("stonecutting/" + string);
    }
    private static ResourceLocation warpingName(Block item, Block from) {
        return rl("warping/" + getItemName(item) + "_from_" + getItemName(from) + "_warping");
    }
    private static ResourceLocation blastingName(ItemLike item, ItemLike from) {
        return rl("blasting/" + getItemName(item) + "_from_" + getItemName(from) + "_blasting");
    }

    private static void quickWarpingRecipe(RecipeOutput output, Block result, Block from, ResourceKey<Level> dimension) {
        new WarpingRecipeBuilder(result.asItem(), dimension, Ingredient.of(from))
                .unlockedBy(getHasName(from), has(from))
                .save(output, warpingName(result, from));
    }

    private static void quickBlastingRecipe(RecipeOutput output, ItemLike result, ItemLike from) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(from), BUILDING_BLOCKS, result, .1f, 100)
                .unlockedBy(getHasName(from), has(from))
                .save(output, blastingName(result, from));
    }
    private static void quickSmeltingRecipe(RecipeOutput output, ItemLike result, ItemLike from) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(from), BUILDING_BLOCKS, result, .1f, 200)
                .unlockedBy(getHasName(from), has(from))
                .save(output, smeltingName(result, from));
    }

    private static void quickStonecuttingRecipe(RecipeOutput output, ItemLike from, ItemLike result) {
        quickStonecuttingRecipe(output, result, from, 1);
    }
    private static void quickStonecuttingRecipe(RecipeOutput output, ItemLike from, ItemLike result, int amount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), BUILDING_BLOCKS, result, amount)
                .unlockedBy(getHasName(from), has(from))
                .save(output, cuttingName(result, from));
    }

    private static Ingredient getStonecuttingIngredients(BlockNode node) {
        //Traverse the tree in reverse until we hit a step that doesn't use stonecutting.
        var list = new ArrayList<BlockNode>();
        var last = node;
        while (last.parent != null && getStoneCuttingCount(last.type) > 0) {
            list.add(last.parent);
            last = last.parent;
        }
        var stream = list.stream().map((n) -> new ItemStack(n.get().asItem()));
        return Ingredient.of(stream);
    }

    private static Ingredient getParentIngredients(BlockNode node) {
        var stream = node.getParents().stream().map((n) -> new ItemStack(n.get().asItem()));
        return Ingredient.of(stream);
    }

    private static ResourceLocation modId(String name) {
        return rl(name);
    }

    private static int getStoneCuttingCount(BlockType type) {
        return switch (type) {
            case BASE, CRACKED, MOSSY, LAMP, DARK, SPECIAL -> 0;
            case BRICKS, FENCE, TILES, CHISELED, PILLAR, STAIRS, WALL, PLATING, POLISHED -> 1;
            case NUB, SLAB, VERTICAL_SLAB -> 2;
        };
    }


    private static void brickRecipe(ItemLike result, ItemLike ingredient, int count, RecipeOutput output) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, result, count)
                .pattern("xx")
                .pattern("xx")
                .define('x', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output);
    }
}
