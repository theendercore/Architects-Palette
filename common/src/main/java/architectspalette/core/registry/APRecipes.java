package architectspalette.core.registry;

import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.platform.Services;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class APRecipes {

    public static final Supplier<RecipeSerializer<WarpingRecipe>> WARPING_SERIALIZER = Services.REGISTRY.registerRecipeSerializer("warping", WarpingRecipe.Serializer::new);
    public static final Supplier<RecipeType<WarpingRecipe>> WARPING = Services.REGISTRY.registerRecipeType("warping", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "warping";
        }
    });
}
