package architectspalette.core.integration;

import architectspalette.core.integration.advancement.CarveTotemTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import static architectspalette.core.ArchitectsPalette.rl;

public class APCriterion {

    public static CarveTotemTrigger CARVE_TOTEM = register(rl("carve_totem"),new CarveTotemTrigger());

    //(ender) I don't know if there is a forge registry for this
    public static <T extends CriterionTrigger<?>> T register(ResourceLocation name, T criterion) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, name, criterion);
    }
}
