package architectspalette.core.integration;

import architectspalette.core.integration.advancement.CarveTotemTrigger;
import architectspalette.core.platform.Services;

import java.util.function.Supplier;


public class APCriterion {
    public static Supplier<CarveTotemTrigger> CARVE_TOTEM = Services.REGISTRY.registerCriterion("carve_totem", CarveTotemTrigger::new);

    // (ender) this is here to just initialize things
    public static void init() {
    }
}