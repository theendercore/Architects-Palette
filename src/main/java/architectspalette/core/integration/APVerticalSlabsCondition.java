package architectspalette.core.integration;

import architectspalette.content.blocks.VerticalSlabBlock;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.fml.ModList;

public class APVerticalSlabsCondition implements ICondition {
	public static final APVerticalSlabsCondition INSTANCE = new APVerticalSlabsCondition();
	public static final MapCodec<APVerticalSlabsCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	// ender remember to re implement this later, I swear you better not forget
	/*
	 * Original logic from Abnormals Core
	 * https://github.com/team-abnormals/abnormals-core/blob/264b7ca6df505743f1c969547dbf2bc8e71b04d5/src/main/java/com/minecraftabnormals/abnormals_core/core/api/conditions/QuarkFlagRecipeCondition.java
	 */
//	@Override
//	public boolean test(IContext context) {
//		if(ModList.get().isLoaded(VerticalSlabBlock.QUARK_ID)) {
//			JsonObject dummyObject = new JsonObject();
//			dummyObject.addProperty("type", "quark:flag");
//			dummyObject.addProperty("flag", "vertical_slabs");
//			return CraftingHelper.getCondition(dummyObject).test(context);
//		}
//		return APConfig.VERTICAL_SLABS_FORCED.get();
//	}

	@Override
	public boolean test(IContext context, DynamicOps<?> ops) {
		return ModList.get().isLoaded(VerticalSlabBlock.QUARK_ID);
	}

	@Override
	public MapCodec<? extends APVerticalSlabsCondition> codec() {
		return CODEC;
	}
}
