package architectspalette.core.integration;

import architectspalette.core.config.APConfig;
import architectspalette.core.platform.Services;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static architectspalette.core.APConstants.LOGGER;
import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.integration.VerticalSlabs.VERTICAL_SLABS_CONDITION;

public class APVerticalSlabsCondition implements ICondition {
    public static final APVerticalSlabsCondition INSTANCE = new APVerticalSlabsCondition();
    public static final MapCodec<APVerticalSlabsCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    // (ender) I put this here since there is no reason to make a file if its only one condition
    public static void registerCondition(final IEventBus modEventBus) {
        var CONDITION = DeferredRegister.create(ForgeRegistries.CONDITION_SERIALIZERS, MOD_ID);
        CONDITION.register(VERTICAL_SLABS_CONDITION.getPath(), () -> APVerticalSlabsCondition.CODEC);
        CONDITION.register(modEventBus);
    }

    /*
     * Original logic from Abnormals Core
     * https://github.com/team-abnormals/abnormals-core/blob/264b7ca6df505743f1c969547dbf2bc8e71b04d5/src/main/java/com/minecraftabnormals/abnormals_core/core/api/conditions/QuarkFlagRecipeCondition.java
     */
    // (ender) so this is no longer just stolen from Abnormals Core, like I took the original code and re-wrote it, so IDK
    @Override
    public boolean test(IContext context, DynamicOps<?> ops) {
        if (APConfig.VERTICAL_SLABS_FORCED.get()) return true;
        if (Services.PLATFORM.isModLoaded(VerticalSlabs.QUARK_ID)) {
            var codec = ForgeRegistries.CONDITION_SERIALIZERS.get().getValue(ResourceLocation.tryParse("quark:flag"));
            if (codec == null) return false;
            var optCondition = codec.codec().decode(JsonOps.INSTANCE, VerticalSlabs.conditionObj());
            if (optCondition.isError()) {
                LOGGER.error("Failed to parse Vertical Slabs condition: {}", optCondition.error());
                return false;
            }
            optCondition.getOrThrow().getFirst().test(context, ops);
        }
        return false;
    }

    @Override
    public MapCodec<? extends APVerticalSlabsCondition> codec() {
        return CODEC;
    }
}
