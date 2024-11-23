package architectspalette.core.event;

import architectspalette.content.blocks.StrippableBlock;
import architectspalette.content.blocks.util.APWeatheringCopper;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.level.BlockEvent;

public class BlockToolModificationEventHandler {
    public static void addCustomStripping(BlockEvent.BlockToolModificationEvent event) {
        var state = event.getFinalState();
        if (!(state.getBlock() instanceof StrippableBlock block)) return;
        var isCopper = block instanceof APWeatheringCopper;

        var ability = event.getItemAbility();
        if (isCopper) {
            if (ability == ItemAbilities.AXE_WAX_OFF) {
                var oBlock = APWeatheringCopper.getUnWaxed(state);
                oBlock.ifPresent(event::setFinalState);
            } else if (ability == ItemAbilities.AXE_SCRAPE) {
                var oBlock = APWeatheringCopper.getPrevious(state);
                oBlock.ifPresent(event::setFinalState);
            }
        } else if (ability == ItemAbilities.AXE_STRIP) {
            event.setFinalState(block.getStripedBlockState(state.getValues()));
        }
    }
}
