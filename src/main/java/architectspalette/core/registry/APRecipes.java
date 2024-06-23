package architectspalette.core.registry;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.crafting.WarpingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class APRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ArchitectsPalette.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArchitectsPalette.MOD_ID);

    public  static final RegistryObject<RecipeSerializer<WarpingRecipe>> WARPING_SERIALIZER = RECIPE_SERIALIZERS.register("warping", WarpingRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<WarpingRecipe>> WARPING = RECIPE_TYPES.register("warping", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "warping";
        }
    });
}
