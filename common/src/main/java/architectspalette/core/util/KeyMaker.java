package architectspalette.core.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static architectspalette.core.APConstants.mcLoc;
import static architectspalette.core.APConstants.modLoc;

public class KeyMaker {
    public static ResourceKey<PlacedFeature> placedFeature(String name) {
        return key(Registries.PLACED_FEATURE, name);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeature(String name) {
        return key(Registries.CONFIGURED_FEATURE, name);
    }

    public static ResourceKey<CreativeModeTab> vanillaTab(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc(name));
    }


    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> resourceKey, String name) {
        return ResourceKey.create(resourceKey, modLoc(name));
    }
}
