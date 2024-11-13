package architectspalette.core.integration;

import architectspalette.core.config.APConfig;
import architectspalette.core.platform.Services;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static architectspalette.core.APConstants.LOGGER;
import static architectspalette.core.integration.VerticalSlabs.VERTICAL_SLABS_CONDITION;

public class APVerticalSlabsCondition implements ResourceCondition {
    public static final APVerticalSlabsCondition INSTANCE = new APVerticalSlabsCondition();
    public static final MapCodec<APVerticalSlabsCondition> CODEC = MapCodec.unit(INSTANCE).stable();
    // (ender) I put this here since there is no reason to make a file if its only one condition
    private static final ResourceConditionType<APVerticalSlabsCondition> condition = ResourceConditionType.create(VERTICAL_SLABS_CONDITION, CODEC);

    public static void registerCondition() {
        ResourceConditions.register(condition);
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider registryLookup) {
        if (APConfig.VERTICAL_SLABS_FORCED.get()) return true;
        if (Services.PLATFORM.isModLoaded(VerticalSlabs.QUARK_ID)) {
            var codec = ResourceConditions.getConditionType(ResourceLocation.tryParse("quark:flag")).codec();
            if (codec == null) return false;
            var optCondition = codec.codec().decode(JsonOps.INSTANCE, VerticalSlabs.conditionObj());
            if (optCondition.isError()) {
                LOGGER.error("Failed to parse Vertical Slabs condition: {}", optCondition.error());
                return false;
            }
            optCondition.getOrThrow().getFirst().test(registryLookup);
        }
        return false;
    }

    @Override
    public ResourceConditionType<?> getType() {
        return condition;
    }

}
