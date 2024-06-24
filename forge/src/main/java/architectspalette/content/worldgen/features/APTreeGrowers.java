package architectspalette.content.worldgen.features;

import architectspalette.core.ArchitectsPalette;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class APTreeGrowers {
    private static final ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(Registries.CONFIGURED_FEATURE, ArchitectsPalette.rl("twisted_tree"));
    public static final TreeGrower WARPED_TREE = new TreeGrower("warped",Optional.empty(), Optional.of(key), Optional.empty());
}
