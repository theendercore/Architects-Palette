package architectspalette.core.datagen.worldgen;

import architectspalette.core.registry.APFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static architectspalette.core.util.KeyMaker.configuredFeature;

public class ConfiguredFeatureCreator {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        APFeatures.getFeatureWrappers().forEach((featureWrapper -> {
            context.register(
                    configuredFeature(featureWrapper.name()),
                    featureWrapper.configuredFeature()
            );
        }));
    }
}
