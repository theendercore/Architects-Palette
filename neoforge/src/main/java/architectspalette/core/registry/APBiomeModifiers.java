package architectspalette.core.registry;

import architectspalette.core.config.APConfig;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import static architectspalette.core.APConstants.MOD_ID;

public class APBiomeModifiers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZER =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MOD_ID);
    public static DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<APUndergroundDecorationBiomeModifier>> UNDERGROUND_DECORATION_MODIFIER =
            BIOME_MODIFIER_SERIALIZER.register("underground_decoration", () -> APUndergroundDecorationBiomeModifier.CODEC);

    public record APUndergroundDecorationBiomeModifier(
            HolderSet<Biome> biomes,
            Holder<PlacedFeature> feature,
            GenerationStep.Decoration step
    ) implements BiomeModifier {
        public static final MapCodec<APUndergroundDecorationBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                Biome.LIST_CODEC.fieldOf("biomes").forGetter(APUndergroundDecorationBiomeModifier::biomes),
                PlacedFeature.CODEC.fieldOf("feature").forGetter(APUndergroundDecorationBiomeModifier::feature),
                GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(APUndergroundDecorationBiomeModifier::step)
        ).apply(builder, APUndergroundDecorationBiomeModifier::new));

        @Override
        public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
            if (phase == Phase.ADD && biomes.contains(biome) && APConfig.worldGenCheck(APConfig.NETHER_CRYSTAL_TOGGLE)) {
                builder.getGenerationSettings().addFeature(this.step, feature);
            }
        }

        @Override
        public @NotNull MapCodec<? extends BiomeModifier> codec() {
            return CODEC;
        }
    }
}
