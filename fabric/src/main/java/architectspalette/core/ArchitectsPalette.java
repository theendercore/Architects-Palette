package architectspalette.core;

import net.fabricmc.api.ModInitializer;

public class ArchitectsPalette implements ModInitializer {
    
    @Override
    public void onInitialize() {
        APConstants.LOGGER.info("Hello Fabric world!");
        APCommonClass.init();
    }
}
