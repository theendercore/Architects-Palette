package architectspalette.core.datagen.worldgen;

import architectspalette.core.registry.APFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static architectspalette.core.util.KeyMaker.configuredFeature;
import static architectspalette.core.util.KeyMaker.placedFeature;

public class PlacedFeatureProvider {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var cfgFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        APFeatures.getFeatureWrappers().forEach((featureWrapper -> {
            context.register(
                    placedFeature(featureWrapper.name() + "_placed"),
                    new PlacedFeature(
                            cfgFeatures.get(configuredFeature(featureWrapper.name())).get(),
                            featureWrapper.placement()
                    )
            );
        }));
    }
}
