package architectspalette.compat.jei;

import architectspalette.compat.BlockInfoBuilder;
import architectspalette.content.blocks.BigBrickBlock;
import architectspalette.content.blocks.CageLanternBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import architectspalette.core.crafting.WarpingRecipe;
import architectspalette.core.integration.VerticalSlabs;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static architectspalette.core.APConstants.rl;
import static architectspalette.core.registry.APBlocks.*;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static final Supplier<RecipeType<RecipeHolder<WarpingRecipe>>> WARPING = () -> RecipeType.createFromVanilla(APRecipes.WARPING.get());
    protected static final ResourceLocation PLUGIN_ID = rl("jei_plugin");

  /*  protected static void addItemInfo(IRecipeRegistration register, Supplier<? extends ItemLike> item, String infoString) {
        addItemInfo(register, item.get(), infoString);
    }

    protected static void addItemInfo(IRecipeRegistration register, StoneBlockSet stoneSet, String infoString) {
        stoneSet.forEach((block -> addItemInfo(register, block, infoString)));
    }

    protected static void addItemInfo(IRecipeRegistration register, ItemLike item, String infoString) {
        register.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, Component.translatable(MOD_ID + ".info." + infoString));
    }*/

    protected static BlockInfoBuilder builder(IRecipeRegistration register) {
        return new BlockInfoBuilder((ingredients, text, id) ->
                register.addIngredientInfo(ingredients, VanillaTypes.ITEM_STACK, text)
        );
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WarpingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
//        registration.addRecipeCatalyst(new ItemStack(Blocks.NETHER_PORTAL.asItem()), WarpingRecipeCategory.UID);
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if (!VerticalSlabs.areVisible()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(
                    VanillaTypes.ITEM_STACK,
                    Services.REGISTRY.getModBlocks().stream().filter(b -> b instanceof VerticalSlabBlock).map(ItemStack::new).toList()
            );
        }
        //Register recipes
        assert Minecraft.getInstance().level != null;
        registration.addRecipes(WARPING.get(), Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(APRecipes.WARPING.get()));

        //Register item info
        builder(registration)
                .add(CHISELED_ABYSSALINE_BRICKS, CHISELED_HADALINE_BRICKS)
                .registerInfo("chiseled_chargeable");
        builder(registration)
                .add(ABYSSALINE, ABYSSALINE_PILLAR, ABYSSALINE_LAMP_BLOCK, ABYSSALINE_PLATING)
                .add(ABYSSALINE_BRICKS, ABYSSALINE_TILES)
                .add(HADALINE, HADALINE_PILLAR, HADALINE_LAMP_BLOCK, HADALINE_PLATING)
                .add(HADALINE_BRICKS, HADALINE_TILES)
                .registerInfo("chargeable");
        builder(registration)
                .add(PLACID_ACACIA_TOTEM, GRINNING_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM)
                .registerInfo("totem_carving");
        builder(registration)
                .add(FLINT_BLOCK, FLINT_PILLAR)
                .add(FLINT_TILES)
                .registerInfo("flint_damage");
        builder(registration)
                .add(MOONSTONE, SUNSTONE)
                .registerInfo("celestial_stones");
        builder(registration)
                .add(NETHER_BRASS, CUT_NETHER_BRASS, SMOOTH_NETHER_BRASS)
                .add(NETHER_BRASS_PILLAR)
                .registerInfo("nether_brass");
        builder(registration)
                .add(block -> block instanceof BigBrickBlock)
                .registerInfo("heavy_bricks");
        builder(registration)
                .add(block -> block instanceof CageLanternBlock)
                .registerInfo("cage_lanterns");
        builder(registration)
                .add(WARDSTONE, WARDSTONE_BRICKS)
                .add(WARDSTONE_PILLAR, CHISELED_WARDSTONE, WARDSTONE_LAMP)
                .registerInfo("wardstone");
    }
}
