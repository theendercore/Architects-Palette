package architectspalette.core.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static architectspalette.core.APConstants.mcLoc;
import static architectspalette.core.APConstants.modLoc;

public class KeyMaker {
    public static ResourceKey<PlacedFeature> placedFeature(String name) {
        return modKey(Registries.PLACED_FEATURE, name);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeature(String name) {
        return modKey(Registries.CONFIGURED_FEATURE, name);
    }

    public static ResourceKey<CreativeModeTab> vanillaTab(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc(name));
    }

    public static <T> ResourceKey<T> modKey(ResourceKey<? extends Registry<T>> resourceKey, String name) {
        return ResourceKey.create(resourceKey, modLoc(name));
    }

    public static <T> TagKey<T> modTag(ResourceKey<? extends Registry<T>> key, String name) {
        return tag(key, modLoc(name));
    }

    public static <T> TagKey<T> tag(ResourceKey<? extends Registry<T>> key, ResourceLocation name) {
        return TagKey.create(key, name);
    }
}
