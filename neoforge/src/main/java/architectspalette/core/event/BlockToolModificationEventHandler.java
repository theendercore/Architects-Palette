package architectspalette.core.event;

import architectspalette.content.blocks.StrippableBlock;
import architectspalette.content.blocks.util.APWeatheringCopper;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.level.BlockEvent;

public class BlockToolModificationEventHandler {
    public static void addCustomStripping(BlockEvent.BlockToolModificationEvent event) {
        var state = event.getFinalState();
        var block = state.getBlock();
        var ability = event.getItemAbility();
        if (block instanceof APWeatheringCopper) {
            if (ability == ItemAbilities.AXE_WAX_OFF) {
                APWeatheringCopper.getUnWaxed(state).ifPresent(event::setFinalState);
            } else if (ability == ItemAbilities.AXE_SCRAPE) {
                APWeatheringCopper.getPrevious(state).ifPresent(event::setFinalState);
            }
        } else if (state.getBlock() instanceof StrippableBlock strippable && ability == ItemAbilities.AXE_STRIP) {
            event.setFinalState(strippable.getStripedBlockState(state));
        }
    }
}
