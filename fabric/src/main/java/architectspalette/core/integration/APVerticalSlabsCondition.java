package architectspalette.core.integration;

import architectspalette.core.config.APConfig;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.Nullable;

import static architectspalette.core.APConstants.VERTICAL_SLABS_CONDITION;

public class APVerticalSlabsCondition implements ResourceCondition {
    public static final APVerticalSlabsCondition INSTANCE = new APVerticalSlabsCondition();
    public static final MapCodec<APVerticalSlabsCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    @Override
    public boolean test(@Nullable HolderLookup.Provider registryLookup) {
        //do the quark check here
        return APConfig.VERTICAL_SLABS_FORCED.get();
    }

    @Override
    public ResourceConditionType<?> getType() {
        return condition;
    }

    // (ender) I put this here since there is no reason to make a file if its only one condition
    private static final ResourceConditionType<APVerticalSlabsCondition> condition = ResourceConditionType.create(VERTICAL_SLABS_CONDITION, CODEC);

    public static void registerCondition() {
        ResourceConditions.register(condition);
    }

}
