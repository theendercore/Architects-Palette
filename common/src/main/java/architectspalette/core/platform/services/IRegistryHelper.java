package architectspalette.core.platform.services;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public interface IRegistryHelper {

    List<Supplier<? extends Item>> CREATIVE_TAB_ITEMS = new ArrayList<>();

    <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type);

    default <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> type) {
        Supplier<T> block = registerBlockNoItem(name, type);
        registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type);

//    <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> type);

//    <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> type);
}
