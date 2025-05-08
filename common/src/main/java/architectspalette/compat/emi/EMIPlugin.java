package architectspalette.compat.emi;

import architectspalette.compat.BlockInfoBuilder;
import architectspalette.content.blocks.BigBrickBlock;
import architectspalette.content.blocks.CageLanternBlock;
import architectspalette.core.integration.VerticalSlabs;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APRecipes;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

import static architectspalette.core.APConstants.rl;
import static architectspalette.core.registry.APBlocks.*;

public class EMIPlugin implements EmiPlugin {
    static final EmiStack ICON = EmiStack.of(APBlocks.WARPSTONE.get());
    public static final EmiRecipeCategory WARPING_CATEGORY = new EmiRecipeCategory(rl("warping"), ICON, ICON);

    @Override
    public void register(EmiRegistry registry) {
        registry.removeEmiStacks((it) -> !VerticalSlabs.isVisible(it.getItemStack().getItem()));

        registry.addCategory(WARPING_CATEGORY);

        RecipeManager manager = registry.getRecipeManager();
        for (var recipe : manager.getAllRecipesFor(APRecipes.WARPING.get())) {
            registry.addRecipe(new WarpingEMIRecipe(recipe));
        }

        builder(registry)
                .add(CHISELED_ABYSSALINE_BRICKS, CHISELED_HADALINE_BRICKS)
                .registerInfo("chiseled_chargeable");
        builder(registry)
                .add(ABYSSALINE, ABYSSALINE_PILLAR, ABYSSALINE_LAMP_BLOCK, ABYSSALINE_PLATING)
                .add(ABYSSALINE_BRICKS, ABYSSALINE_TILES)
                .add(HADALINE, HADALINE_PILLAR, HADALINE_LAMP_BLOCK, HADALINE_PLATING)
                .add(HADALINE_BRICKS, HADALINE_TILES)
                .registerInfo("chargeable");
        builder(registry)
                .add(PLACID_ACACIA_TOTEM, GRINNING_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM)
                .registerInfo("totem_carving");
        builder(registry)
                .add(FLINT_BLOCK, FLINT_PILLAR)
                .add(FLINT_TILES)
                .registerInfo("flint_damage");
        builder(registry)
                .add(MOONSTONE, SUNSTONE)
                .registerInfo("celestial_stones");
        builder(registry)
                .add(NETHER_BRASS, CUT_NETHER_BRASS, SMOOTH_NETHER_BRASS)
                .add(NETHER_BRASS_PILLAR)
                .registerInfo("nether_brass");
        builder(registry)
                .add(block -> block instanceof BigBrickBlock)
                .registerInfo("heavy_bricks");
        builder(registry)
                .add(block -> block instanceof CageLanternBlock)
                .registerInfo("cage_lanterns");
        builder(registry)
                .add(WARDSTONE, WARDSTONE_BRICKS)
                .add(WARDSTONE_PILLAR, CHISELED_WARDSTONE, WARDSTONE_LAMP)
                .registerInfo("wardstone");
    }

    protected static BlockInfoBuilder builder(EmiRegistry register) {
        return new BlockInfoBuilder((ingredients, text, id) -> register.addRecipe(
                new EmiInfoRecipe(
                        ingredients.stream().map((i) -> EmiIngredient.of(Ingredient.of(i))).toList(),
                        List.of(text),
                        id
                )
        ));
    }
}
