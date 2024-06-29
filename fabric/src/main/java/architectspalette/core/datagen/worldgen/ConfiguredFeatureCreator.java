package architectspalette.core.datagen.worldgen;

import architectspalette.core.registry.APFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static architectspalette.core.APConstants.modLoc;

public class ConfiguredFeatureCreator {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        APFeatures.getFeatureWrappers().forEach((featureWrapper -> {
            context.register(
                    key(featureWrapper.name()),
                    featureWrapper.configuredFeature()
            );
        }));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, modLoc(name));
    }
}

