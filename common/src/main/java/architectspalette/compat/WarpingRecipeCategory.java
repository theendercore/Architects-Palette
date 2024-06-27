package architectspalette.compat;

import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.registry.APBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Arrays;
import java.util.List;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.modLoc;

public class WarpingRecipeCategory implements IRecipeCategory<RecipeHolder<WarpingRecipe>> {

    public static final ResourceLocation UID = modLoc("warping");

    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public WarpingRecipeCategory(IGuiHelper helper) {
        title = Component.translatable(MOD_ID + ".info.warping_recipe_title");
        background = helper.createDrawable(modLoc("textures/gui/warping_recipe.png"), 0, 0, 117, 57);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(APBlocks.WARPSTONE.get()));
    }

    @Override
    public RecipeType<RecipeHolder<WarpingRecipe>> getRecipeType() {
        return JEIPlugin.WARPING.get();
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<WarpingRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 22)
                .addItemStacks(Arrays.asList(recipe.value().getInput().getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 87, 22)
                .addItemStack(recipe.value().getResult());
    }


    @Override
    public List<Component> getTooltipStrings(RecipeHolder<WarpingRecipe> recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> strings = new java.util.ArrayList<>();
        if (pointInBox(mouseX, mouseY, 49, 12, 18, 35)) {
            ResourceLocation targetDimension = recipe.value().getDimension();
            Component dimensionName = Component.translatable(MOD_ID + ".info.dimension." + targetDimension.toString().replace(":", "."));
            Component tossPrompt = Component.translatable(MOD_ID + ".info.warping_toss_description", dimensionName);

            strings.add(tossPrompt);

        }

        return strings;
    }

    private static boolean pointInBox(double x, double y, double left, double top, double width, double height) {
        return (x >= left && x <= left + width && y >= top && y <= top + height);
    }

}
