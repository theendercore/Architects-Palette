package architectspalette.core.event;

import architectspalette.content.blocks.util.APWeatheringCopper;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

public class OxidizableEventHandler {
    public static void init() {
        APWeatheringCopper.NEXT_BY_BLOCK.get().forEach(OxidizableBlocksRegistry::registerOxidizableBlockPair);
        APWeatheringCopper.WAXED_BY_BLOCK.get().forEach(OxidizableBlocksRegistry::registerWaxableBlockPair);
    }
}
