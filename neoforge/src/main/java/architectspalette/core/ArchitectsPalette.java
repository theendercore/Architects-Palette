package architectspalette.core;


import net.minecraft.world.level.block.StairBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(APConstants.MOD_ID)
public class ArchitectsPalette {

    public ArchitectsPalette(IEventBus eventBus) {
        // Use NeoForge to bootstrap the Common mod.
        APConstants.LOGGER.info("Hello NeoForge world!");
//        APCommon.init();
    }
}