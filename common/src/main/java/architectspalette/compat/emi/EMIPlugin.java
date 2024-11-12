package architectspalette.compat.emi;

import architectspalette.content.blocks.BigBrickBlock;
import architectspalette.content.blocks.CageLanternBlock;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APRecipes;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.modLoc;
import static architectspalette.core.registry.APBlocks.*;

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

        builder()
                .add(CHISELED_ABYSSALINE_BRICKS, CHISELED_HADALINE_BRICKS)
                .registerInfo(registry, "chiseled_chargeable");
        builder()
                .add(ABYSSALINE, ABYSSALINE_PILLAR, ABYSSALINE_LAMP_BLOCK, ABYSSALINE_PLATING)
                .add(ABYSSALINE_BRICKS, ABYSSALINE_TILES)
                .add(HADALINE, HADALINE_PILLAR, HADALINE_LAMP_BLOCK, HADALINE_PLATING)
                .add(HADALINE_BRICKS, HADALINE_TILES)
                .registerInfo(registry, "chargeable");
        builder()
                .add(PLACID_ACACIA_TOTEM, GRINNING_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM)
                .registerInfo(registry, "totem_carving");
        builder()
                .add(FLINT_BLOCK, FLINT_PILLAR)
                .add(FLINT_TILES)
                .registerInfo(registry, "flint_damage");
        builder()
                .add(MOONSTONE, SUNSTONE)
                .registerInfo(registry, "celestial_stones");
        builder()
                .add(NETHER_BRASS, CUT_NETHER_BRASS, SMOOTH_NETHER_BRASS)
                .add(NETHER_BRASS_PILLAR)
                .registerInfo(registry, "nether_brass");
        builder()
                .add(block -> block instanceof BigBrickBlock)
                .registerInfo(registry, "heavy_bricks");
        builder()
                .add(block -> block instanceof CageLanternBlock)
                .registerInfo(registry, "cage_lanterns");
        builder()
                .add(WARDSTONE, WARDSTONE_BRICKS)
                .add(WARDSTONE_PILLAR, CHISELED_WARDSTONE, WARDSTONE_LAMP)
                .registerInfo(registry, "wardstone");
    }

    protected static BlockListBuilder builder() {
        return new BlockListBuilder();
    }

    protected static class BlockListBuilder {
        protected final List<Block> blocks = new ArrayList<>();

        protected BlockListBuilder add(BlockNode... nodes) {
            for (BlockNode node : nodes)
                node.forEach((n) -> blocks.add(n.get()));
            return this;
        }

        protected BlockListBuilder add(StoneBlockSet... sets) {
            for (StoneBlockSet set : sets) {
                set.forEach(blocks::add);
            }
            return this;
        }

        protected BlockListBuilder add(Predicate<Block> filter) {
            for (Block entry : Services.REGISTRY.getModBlocks()) {
                if (filter.test(entry)) blocks.add(entry);
            }
            return this;
        }

        @SafeVarargs
        private BlockListBuilder add(Supplier<? extends Block>... blockList) {
            for (Supplier<? extends Block> block : blockList) {
                blocks.add(block.get());
            }
            return this;
        }

        protected void registerInfo(EmiRegistry register, String infoString) {
            register.addRecipe(new EmiInfoRecipe(
                    blocks.stream().map((i) -> EmiIngredient.of(Ingredient.of(i))).toList(),
                    List.of(Component.translatable(MOD_ID + ".info." + infoString)),
                    modLoc("info/" + infoString)
            ));
            blocks.clear();
        }
    }
}
