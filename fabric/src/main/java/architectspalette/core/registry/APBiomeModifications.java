package architectspalette.core.registry;

import architectspalette.core.config.APConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static architectspalette.core.APConstants.modLoc;

public class APBiomeModifications {

    final static ResourceKey<PlacedFeature> EKANITE_CLUSTER = key("ekanite_cluster_placed");

    public static void init() {
        if (APConfig.worldGenCheck(APConfig.NETHER_CRYSTAL_TOGGLE)) {
            addUnderground("add_ekanite_cluster", EKANITE_CLUSTER, Biomes.WARPED_FOREST);
        }

    }

    public static void addUnderground(String id, ResourceKey<PlacedFeature> feature, ResourceKey<Biome> biome) {
        addFeature(id, GenerationStep.Decoration.UNDERGROUND_DECORATION, feature, biome);
    }

    private static void addFeature(String id, GenerationStep.Decoration decoration, ResourceKey<PlacedFeature> resourceKey, ResourceKey<Biome> biome) {
        BiomeModifications.create(modLoc(id)).add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(biome), context -> context.getGenerationSettings().addFeature(decoration, resourceKey));
    }

    // (ender) dont @ me this is simpler
    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, modLoc(name));
    }
}
