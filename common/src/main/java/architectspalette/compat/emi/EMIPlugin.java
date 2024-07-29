package architectspalette.compat.emi;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APRecipes;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.crafting.RecipeManager;

import static architectspalette.core.APConstants.modLoc;

public class EMIPlugin implements EmiPlugin {
    static final EmiStack ICON = EmiStack.of(APBlocks.WARPSTONE.get());
    public static final EmiRecipeCategory WARPING_CATEGORY = new EmiRecipeCategory(modLoc("warping"), ICON, ICON);


    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(WARPING_CATEGORY);

        RecipeManager manager = registry.getRecipeManager();
        for (var recipe : manager.getAllRecipesFor(APRecipes.WARPING.get())) {
            registry.addRecipe(new WarpingEMIRecipe(recipe));
        }
    }
}
