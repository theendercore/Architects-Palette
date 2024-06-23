package architectspalette.core.datagen;

import architectspalette.core.crafting.WarpingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class WarpingRecipeBuilder implements RecipeBuilder {
    private final Ingredient input;
    private final Item output;
    private final ResourceLocation dimension;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public WarpingRecipeBuilder(Ingredient input, ItemLike output, ResourceLocation dimension) {
        this.input = input;
        this.output = output.asItem();
        this.dimension = dimension;
    }

    public static WarpingRecipeBuilder warping(Ingredient input, ItemLike output, ResourceLocation dimension) {
        return new WarpingRecipeBuilder(input, output , dimension);
    }


    public WarpingRecipeBuilder unlockedBy(String p_176810_, Criterion<?> p_298188_) {
        this.criteria.put(p_176810_, p_298188_);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.output;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation name) {
        this.ensureValid(name);
        Advancement.Builder advancement$builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(name))
                .rewards(AdvancementRewards.Builder.recipe(name))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        WarpingRecipe warpingRecipe = new WarpingRecipe(this.input, new ItemStack(this.output), this.dimension);
        output.accept(name, warpingRecipe, advancement$builder.build(name.withPrefix("recipes/warping/")));
    }

    private void ensureValid(ResourceLocation name) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + name);
        }
    }
}
