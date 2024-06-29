package architectspalette.core.datagen.worldgen;

import architectspalette.core.registry.APFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static architectspalette.core.APConstants.modLoc;

public class PlacedFeatureProvider {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var cfgFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        APFeatures.getFeatureWrappers().forEach((featureWrapper -> {
            context.register(
                    key(featureWrapper.name() + "_placed"),
                    new PlacedFeature(
                            cfgFeatures.get(configuredKey(featureWrapper.name())).get(),
                            featureWrapper.placement()
                    )
            );
        }));
    }

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, modLoc(name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, modLoc(name));
    }
}
