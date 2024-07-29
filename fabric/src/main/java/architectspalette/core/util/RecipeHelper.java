package architectspalette.core.util;

import architectspalette.core.datagen.WarpingRecipeBuilder;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static architectspalette.core.APConstants.modLoc;
import static architectspalette.core.registry.util.BlockNode.BlockType.*;
import static net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS;
import static net.minecraft.data.recipes.RecipeProvider.*;

public interface RecipeHelper {
    private static ResourceLocation smeltingName(ItemLike item, ItemLike from) {
        return modLoc("smelting/" + getItemName(item) + "_from_" + getItemName(from) + "_smelting");
    }

    private static ResourceLocation smokingName(ItemLike item, ItemLike from) {
        return modLoc("smoking/" + getItemName(item) + "_from_" + getItemName(from) + "_smoking");
    }

    private static ResourceLocation smokingName(ItemLike item, String from) {
        return modLoc("smoking/" + getItemName(item) + "_from_" + from + "_smoking");
    }

    private static ResourceLocation warpingName(ItemLike item, ItemLike from) {
        return modLoc("warping/" + getItemName(item) + "_from_" + getItemName(from) + "_warping");
    }

    private static ResourceLocation warpingName(ItemLike item, String from) {
        return modLoc("warping/" + getItemName(item) + "_from_" + from + "_warping");
    }

    private static ResourceLocation blastingName(ItemLike item, ItemLike from) {
        return modLoc("blasting/" + getItemName(item) + "_from_" + getItemName(from) + "_blasting");
    }

    Map<String, Boolean> stonecuttingMap = new HashMap<>();

    private static ResourceLocation cuttingName(ItemLike item, ItemLike from) {
        var string = getItemName(item);
        if (stonecuttingMap.put(string, true) != null) {
            string += "_from_" + getItemName(from);
        }
        return modLoc("stonecutting/" + string);
    }

    static void netherWarpingRecipe(RecipeOutput output, ItemLike result, ItemLike from) {
        quickWarpingRecipe(output, result, from, Level.NETHER);
    }

    static void netherWarpingRecipe(RecipeOutput output, ItemLike result, TagKey<Item> from) {
        quickWarpingRecipe(output, result, from, from.location().getPath(), Level.NETHER);
    }

    static void quickWarpingRecipe(RecipeOutput output, ItemLike result, TagKey<Item> from, String inputName, ResourceKey<Level> dimension) {
        new WarpingRecipeBuilder(Ingredient.of(from), result, dimension.location())
                .unlockedBy("has_" + inputName, has(from))
                .save(output, warpingName(result.asItem(), inputName));
    }

    static void quickWarpingRecipe(RecipeOutput output, ItemLike result, ItemLike from, ResourceKey<Level> dimension) {
        new WarpingRecipeBuilder(Ingredient.of(from), result, dimension.location())
                .unlockedBy(getHasName(from), has(from))
                .save(output, warpingName(result.asItem(), from));
    }


    static void quickStonecuttingRecipe(RecipeOutput output, ItemLike from, ItemLike result, int amount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(from), BUILDING_BLOCKS, result, amount)
                .unlockedBy(getHasName(from), has(from))
                .save(output, cuttingName(result, from));
    }

    static void quickSmeltingRecipe(RecipeOutput output, ItemLike result, ItemLike from) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(from), BUILDING_BLOCKS, result, .1f, 200)
                .unlockedBy(getHasName(from), has(from))
                .save(output, smeltingName(result, from));
    }

    static void quickSmokingRecipe(RecipeOutput output, ItemLike result, ItemLike from) {
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(from), BUILDING_BLOCKS, result, .1f, 100)
                .unlockedBy(getHasName(from), has(from))
                .save(output, smokingName(result, from));
    }

    static void quickSmokingRecipe(RecipeOutput output, ItemLike result, TagKey<Item> from) {
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(from), BUILDING_BLOCKS, result, .1f, 100)
                .unlockedBy("has_" + from.location().getPath(), has(from))
                .save(output, smokingName(result, from.location().getPath()));
    }

    static void quickBlastingRecipe(RecipeOutput output, ItemLike result, ItemLike from) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(from), BUILDING_BLOCKS, result, .1f, 100)
                .unlockedBy(getHasName(from), has(from))
                .save(output, blastingName(result, from));
    }

    static void quickPillarRecipe(RecipeOutput output, ItemLike result, ItemLike base) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, result, 2)
                .pattern("x")
                .pattern("x")
                .define('x', base)
                .unlockedBy(getHasName(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), BUILDING_BLOCKS, result, 1)
                .unlockedBy(getHasName(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output, cuttingName(result, base));

    }

    static void oreBrickRecipe(RecipeOutput output, ItemLike result, ItemLike ingredient) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, result, 8)
                .pattern("xxx")
                .pattern("xox")
                .pattern("xxx")
                .define('x', Items.STONE_BRICKS)
                .define('o', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output);
    }

    static void boardRecipe(RecipeOutput output, ItemLike result, ItemLike ingredient) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, result, 9)
                .pattern("xxx")
                .pattern("xxx")
                .pattern("xxx")
                .define('x', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output);
    }

    static void railingRecipe(RecipeOutput output, ItemLike result, ItemLike ingredient) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, result, 3)
                .pattern("xsx")
                .define('s', Items.STICK)
                .define('x', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output);
    }

    static void brickRecipe(ItemLike result, ItemLike ingredient, int count, RecipeOutput output) {
        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, result, count)
                .pattern("xx")
                .pattern("xx")
                .define('x', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output);
    }

    private static int getStoneCuttingCount(BlockNode.BlockType type) {
        return switch (type) {
            case BASE, CRACKED, MOSSY, LAMP, DARK, SPECIAL -> 0;
            case BRICKS, FENCE, TILES, CHISELED, PILLAR, STAIRS, WALL, PLATING, POLISHED -> 1;
            case NUB, SLAB, VERTICAL_SLAB -> 2;
        };
    }

    private static int getStoneCuttingCount(StoneBlockSet.SetComponent type) {
        return switch (type) {
            case BLOCK -> 0;
            case FENCE, PILLAR, STAIRS, WALL -> 1;
            case NUB, SLAB, VERTICAL_SLAB -> 2;
        };
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

    static void processStoneBlockSet(RecipeOutput output, StoneBlockSet set) {
        var base = set.getPart(StoneBlockSet.SetComponent.BLOCK);
        set.forEachPart((part, block) -> {
            String hasBase = "has_" + Objects.requireNonNull(Services.REGISTRY.getId(() -> base)).getPath();
            int stoneCuttingCount = getStoneCuttingCount(part);
            switch (part) {
                case SLAB -> {
                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 6)
                            .pattern("xxx")
                            .define('x', base)
                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(base))
                            .save(output);
                }
                case VERTICAL_SLAB -> {
                    stoneCuttingCount = 0;
                    /*do stuff later*/
                }
                case STAIRS -> {
                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 4)
                            .pattern("x  ")
                            .pattern("xx ")
                            .pattern("xxx")
                            .define('x', base)
                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(base))
                            .save(output);
                }
                case WALL -> {
                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 6)
                            .pattern("xxx")
                            .pattern("xxx")
                            .define('x', base)
                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(base))
                            .save(output);
                }
                case PILLAR -> {
                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 2)
                            .pattern("x")
                            .pattern("x")
                            .define('x', base)
                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(base))
                            .save(output);
                }
                case FENCE, NUB, BLOCK -> {
                }
            }

            if (stoneCuttingCount > 0 && (set.hasStoneCuttingRecipes || part == StoneBlockSet.SetComponent.NUB)) {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), BUILDING_BLOCKS, block, stoneCuttingCount)
                        .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(base))
                        .save(output, cuttingName(block, base));
            }

        });
    }

    static void processBlockNode(RecipeOutput output, BlockNode node) {
        String hasBase = "has_" + node.getName();
        node.forEach((n -> {
            if (n.parent != null && !n.getFlag(BlockNode.ExcludeFlag.RECIPES)) {
                Block block = n.get();
                Block parent = n.parent.get();

                int stoneCuttingCount = getStoneCuttingCount(n.type);
                switch (n.type) {
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
                                    .save(output, modLoc(n.getName() + "_from_" + Services.REGISTRY.getId(tiles).getPath()));
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
                                    .save(output, modLoc(n.getName() + "_from_" + Services.REGISTRY.getId(bricks).getPath()));
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

//                        ConditionalRecipe.builder().mainCondition(APVerticalSlabsCondition.INSTANCE)
//                                .recipe((c) -> {
//                                    ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 3)
//                                            .pattern("x")
//                                            .pattern("x")
//                                            .pattern("x")
//                                            .group("vertical_slab")
//                                            .define('x', slab)
//                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
//                                            .save(c);
//                                })
//                                .save(output, modLoc("vslabs/" + n.getName()));
//                        //Revert to slab from vslab
//                        ConditionalRecipe.builder().mainCondition(APVerticalSlabsCondition.INSTANCE)
//                                .recipe((c) -> {
//                                    ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, slab, 1)
//                                            .group("vertical_slab_revert")
//                                            .requires(block)
//                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
//                                            .save(c);
//                                })
//                                .save(output, modLoc("vslabs/" + n.getName() + "_revert"));
//                        //Stonecut from block to vslab
//                        ConditionalRecipe.builder().mainCondition(APVerticalSlabsCondition.INSTANCE)
//                                .recipe((c) -> {
//                                    SingleItemRecipeBuilder.stonecutting(getStonecuttingIngredients(n), BUILDING_BLOCKS, block, 2)
//                                            .unlockedBy(hasBase, InventoryChangeTrigger.TriggerInstance.hasItems(node.get()))
//                                            .save(c);
//                                })
//                                .save(output, modLoc("vslabs/stonecutting/" + n.getName()));
//
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
                    case DARK -> ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, block, 8)
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

}
