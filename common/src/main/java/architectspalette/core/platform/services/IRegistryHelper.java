package architectspalette.core.platform.services;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.mcLoc;

public interface IRegistryHelper {

    Map<ResourceKey<CreativeModeTab>, ArrayList<Supplier<? extends ItemLike>>> CREATIVE_TAB_ITEMS_MAP = new HashMap<>();

    default void addCreativeTabItems(ResourceKey<CreativeModeTab> tab, Supplier<? extends Item> item) {
        var itemList = CREATIVE_TAB_ITEMS_MAP.get(tab);
        if (itemList == null) CREATIVE_TAB_ITEMS_MAP.put(tab, new ArrayList<>(List.of(item)));
        else {
            itemList.add(item);
            CREATIVE_TAB_ITEMS_MAP.put(tab, itemList);
        }
    }

    default <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type) {
        return registerItem(name, type, ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc("ingredients")));
    }

    <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type, ResourceKey<CreativeModeTab> group);


    default <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> type) {
        Supplier<T> block = registerBlockNoItem(name, type);
        registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type);

//    <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> type);

//    <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> type);
}
