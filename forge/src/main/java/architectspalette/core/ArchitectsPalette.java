package architectspalette.core;

import net.minecraftforge.fml.common.Mod;

@Mod(APConstants.MOD_ID)
public class ArchitectsPalette {
    
    public ArchitectsPalette() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        APConstants.LOG.info("Hello Forge world!");
        APCommonClass.init();
        
    }
}