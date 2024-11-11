/*
package architectspalette.compat.emi;

import architectspalette.core.crafting.WarpingRecipe;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.modLoc;

public class WarpingEMIRecipe extends BasicEmiRecipe {
    private final RecipeHolder<WarpingRecipe> RECIPE;
    public static final EmiTexture PORTAL_TEXTURE = new EmiTexture(modLoc("textures/gui/warping_recipe.png"), 0, 0, 117, 57);

    public WarpingEMIRecipe(RecipeHolder<WarpingRecipe> recipe) {
        super(EMIPlugin.WARPING_CATEGORY, recipe.id(), 117, 57);
        RECIPE = recipe;
        this.inputs.add(EmiIngredient.of(recipe.value().getInput()));
        this.outputs.add(EmiStack.of(recipe.value().getResult()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(PORTAL_TEXTURE, 0, 0);
        widgets.addSlot(inputs.getFirst(), 16, 22);
        widgets.addSlot(outputs.getFirst(), 87, 22).recipeContext(this);
        Component dimensionName = Component.translatable(MOD_ID + ".info.dimension." + RECIPE.value().getDimension().toString().replace(":", "."));
        Component tossPrompt = Component.translatable(MOD_ID + ".info.warping_toss_description", dimensionName);

        widgets.addTooltipText(List.of(tossPrompt), 49, 12, 18, 35);
    }
}
*/
