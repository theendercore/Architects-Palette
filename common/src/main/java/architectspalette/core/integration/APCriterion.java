package architectspalette.core.integration;

import architectspalette.core.integration.advancement.CarveTotemTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import static architectspalette.core.APConstants.rl;


public class APCriterion {
    // (ender) this is here to just initialize things
    public static void init() {
    }

    public static CarveTotemTrigger CARVE_TOTEM = register(rl("carve_totem"), new CarveTotemTrigger());

    // (ender) there doesn't seam to be a loader specific way to register criteria
    public static <T extends CriterionTrigger<?>> T register(ResourceLocation name, T criterion) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, name, criterion);
    }
}
