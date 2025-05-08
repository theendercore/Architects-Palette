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
import org.jetbrains.annotations.NotNull;

// (ender) I used SingleRecipeInput since I didn't think it needs to have more than one item input,
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
    public boolean matches(SingleRecipeInput input, @NotNull Level level) {
        return this.input.test(input.getItem(0)) && (this.dimension.compareTo(level.dimension().location()) == 0);
    }

    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider provider) {
        return this.getResultItem(provider).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return this.output;
    }

    public ItemStack getResult() {
        return this.output;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(this.input);
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return APRecipes.WARPING.get();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
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
        public @NotNull MapCodec<WarpingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, WarpingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}