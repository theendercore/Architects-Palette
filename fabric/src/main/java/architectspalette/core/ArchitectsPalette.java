package architectspalette.core;

import architectspalette.core.event.CreativeModeTabEventHandler;
import net.fabricmc.api.ModInitializer;

public class ArchitectsPalette implements ModInitializer {

    @Override
    public void onInitialize() {
        APConstants.LOGGER.info("Hello Fabric world!");

        APCommonClass.earlyInit();
        APCommonClass.init();

        CreativeModeTabEventHandler.init();
    }
}
