package architectspalette.core;

import net.fabricmc.api.ClientModInitializer;

public class ArchitectsPaletteClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        APCommonClass.initClient();
    }
}
