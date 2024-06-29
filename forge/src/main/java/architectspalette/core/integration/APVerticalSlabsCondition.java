package architectspalette.core.integration;

import architectspalette.core.config.APConfig;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.VERTICAL_SLABS_CONDITION;

public class APVerticalSlabsCondition implements ICondition {
    public static final APVerticalSlabsCondition INSTANCE = new APVerticalSlabsCondition();
    public static final MapCodec<APVerticalSlabsCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    // (ender) ender remember to re implement this later, I swear you better not forget
    /*
     * Original logic from Abnormals Core
     * https://github.com/team-abnormals/abnormals-core/blob/264b7ca6df505743f1c969547dbf2bc8e71b04d5/src/main/java/com/minecraftabnormals/abnormals_core/core/api/conditions/QuarkFlagRecipeCondition.java
     */
    @Override
    public boolean test(IContext context, DynamicOps<?> ops) {
//		if(ModList.get().isLoaded(VerticalSlabBlock.QUARK_ID)) {
//			JsonObject dummyObject = new JsonObject();
//			dummyObject.addProperty("type", "quark:flag");
//			dummyObject.addProperty("flag", "vertical_slabs");
//			return CraftingHelper.getCondition(dummyObject).test(context);
//		}
        return APConfig.VERTICAL_SLABS_FORCED.get();
    }

    @Override
    public MapCodec<? extends APVerticalSlabsCondition> codec() {
        return CODEC;
    }

    // (ender) I put this here since there is no reason to make a file if its only one condition
    public static void registerCondition(final IEventBus modEventBus) {
        var CONDITION = DeferredRegister.create(ForgeRegistries.CONDITION_SERIALIZERS, MOD_ID);
        CONDITION.register(VERTICAL_SLABS_CONDITION.getPath(), () -> APVerticalSlabsCondition.CODEC);
        CONDITION.register(modEventBus);
    }
}
