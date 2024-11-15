package architectspalette.core.crafting;

import architectspalette.core.registry.APRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

// (ender) I used SingleRecipeInput since I didn't think it needs to have mor then one item input,
// but you can blame me if this breaks stuff
public class WarpingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation dimension;

    public WarpingRecipe(Ingredient input, ItemStack output, ResourceLocation dimension) {
        this.input = input;
        this.output = output;
        this.dimension = dimension;
    }

    public ResourceLocation getDimension() {
        return this.dimension;
    }

    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.input.test(input.getItem(0)) && (this.dimension.compareTo(level.dimension().location()) == 0);
    }

    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider provider) {
        return this.getResultItem(provider).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output;
    }

    public ItemStack getResult() {
        return this.output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(this.input);
    }

    @Override
    public RecipeType<?> getType() {
        return APRecipes.WARPING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return APRecipes.WARPING_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<WarpingRecipe> {
        public static final MapCodec<WarpingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                                ResourceLocation.CODEC.fieldOf("dimension").forGetter(recipe -> recipe.dimension)
                        )
                        .apply(instance, WarpingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, WarpingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                recipe -> recipe.input,
                ItemStack.STREAM_CODEC,
                recipe -> recipe.output,
                ResourceLocation.STREAM_CODEC,
                recipe -> recipe.dimension,
                WarpingRecipe::new
        );

        @Override
        public MapCodec<WarpingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WarpingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}