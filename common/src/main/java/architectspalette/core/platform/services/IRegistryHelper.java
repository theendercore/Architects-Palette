package architectspalette.core.platform.services;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface IRegistryHelper {

    Map<ResourceKey<CreativeModeTab>, ArrayList<Supplier<? extends ItemLike>>> CREATIVE_TAB_ITEMS_MAP = new HashMap<>();

    void resisterConfig();  // (ender) for some reason you cant import (ModConfig.Type, IConfigSpec<?>) in common

    default void addCreativeTabItems(ResourceKey<CreativeModeTab> tab, Supplier<? extends Item> item) {
        var itemList = CREATIVE_TAB_ITEMS_MAP.get(tab);
        if (itemList == null) CREATIVE_TAB_ITEMS_MAP.put(tab, new ArrayList<>(List.of(item)));
        else {
            itemList.add(item);
            CREATIVE_TAB_ITEMS_MAP.put(tab, itemList);
        }
    }

    <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type, @Nullable ResourceKey<CreativeModeTab> group);

    default <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> type, @Nullable ResourceKey<CreativeModeTab> group) {
        Supplier<T> block = registerBlockNoItem(name, type);
        registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()), group);
        return block;
    }

    <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type);

    <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> type);

    <T extends ParticleType<?>> Supplier<T> registerParticleType(String name, Supplier<T> type);

    <T extends SimpleParticleType> Supplier<T> registerSimpleParticleType(String name);

    <T extends Feature<?>> Supplier<T> registerFeature(String name, Supplier<T> type);
//    <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> type);

//    <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> type);
}
