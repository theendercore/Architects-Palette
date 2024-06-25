package architectspalette.core.crafting;

import architectspalette.core.registry.APRecipes;
import architectspalette.core.registry.APSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class WarpingHandler {

    //OLD, see mixin/ItemEntityMixin for info
 /*   public static void onDimensionsChanged (ItemEntity itemIn, ServerLevel server) {
        ResourceKey<Level> dimension = server.dimension();
        ResourceLocation dimensionId = dimension.location();
        ItemStack item = itemIn.getItem();
        for (Recipe<?> recipe : itemIn.level.getRecipeManager().getAllRecipesFor(WarpingRecipe.TYPE)) {
            if (recipe instanceof WarpingRecipe) {
                WarpingRecipe warpingRecipe = (WarpingRecipe) recipe;
                 //compareTo returns 0 if they're the same
                boolean dimensionPassed = dimensionId.compareTo(warpingRecipe.getDimension()) == 0;
                dimensionPassed = dimensionPassed || (itemIn.level.dimension().location().compareTo(warpingRecipe.getDimension()) == 0);
                if (dimensionPassed && (warpingRecipe.getInput().test(item))) {
                    itemIn.level.playSound(null, itemIn.getX(), itemIn.getY(), itemIn.getZ(), APSounds.ITEM_WARPS.get(), SoundSource.BLOCKS, 1F, 1F);
                    int n = item.getCount();
                    ItemStack out = warpingRecipe.getResultItem().copy();
                    out.setCount(n);
                    itemIn.setItem(out);
                    break;
                }
            }
        }
    }*/

    public static SingleRecipeInput transformationInv;

    private static Optional<RecipeHolder<WarpingRecipe>> getRecipe(ItemStack item, Level world) {
        transformationInv = new SingleRecipeInput(item);
        return world.getRecipeManager().getRecipeFor(APRecipes.WARPING.get(), transformationInv, world);
    }

    public static ItemStack getTransformedItem(ItemStack itemIn, Level world) {
        Optional<RecipeHolder<WarpingRecipe>> recipe = getRecipe(itemIn, world);
        return recipe.map(warpingRecipe -> warpingRecipe.value().assemble(transformationInv, world.getServer().registryAccess())).orElse(null);
    }

    public static void warpItem(ItemEntity itemIn, ServerLevel worldIn) {
        ItemStack baseStack = itemIn.getItem();
        ItemStack recipeStack = new ItemStack(baseStack.getItem(), 1);
        ItemStack resultStack = getTransformedItem(recipeStack, worldIn);
        if (resultStack != null) {
            resultStack.setCount(baseStack.getCount());
            itemIn.setItem(resultStack);
            itemIn.level().playSound(null, itemIn.getX(), itemIn.getY(), itemIn.getZ(), APSounds.ITEM_WARPS.get(), SoundSource.BLOCKS, 1F, 1F);
        }
    }
}
