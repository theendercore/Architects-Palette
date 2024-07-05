package architectspalette.core.datagen.worldgen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static architectspalette.core.registry.APBlocks.TWISTED_LEAVES;
import static architectspalette.core.util.KeyMaker.configuredFeature;
import static architectspalette.core.util.KeyMaker.placedFeature;

public class APFeatureCreator {

    public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        APFeatures.getFeatureWrappers().forEach((featureWrapper -> {
            context.register(
                    configuredFeature(featureWrapper.name()),
                    featureWrapper.configuredFeature()
            );
        }));
        context.register(
                configuredFeature("twisted_tree"),
                new ConfiguredFeature<>(Feature.TREE,
                        new TreeConfiguration.TreeConfigurationBuilder(
                                BlockStateProvider.simple(APBlocks.TWISTED_LOG.get().defaultBlockState()),
                                new ForkingTrunkPlacer(5, 2, 2),
                                BlockStateProvider.simple(TWISTED_LEAVES.get().defaultBlockState()),
                                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                                new TwoLayersFeatureSize(1, 0, 2)
                        ).ignoreVines().build()
                )
        );
    }

    public static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
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
