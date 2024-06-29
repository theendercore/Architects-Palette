package architectspalette.core.event;

import architectspalette.core.config.APConfig;
import architectspalette.core.integration.APTrades;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static architectspalette.core.APConstants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class WanderingTradesEventHandler {

    @SubscribeEvent
    public static void onWanderingTradesLoaded(WandererTradesEvent event) {
        if (APConfig.WANDERER_TRADES_ENABLED.get()) {
            List<VillagerTrades.ItemListing> generic = event.getGenericTrades();
            List<VillagerTrades.ItemListing> rare = event.getRareTrades();
            APTrades.WONDERING_TRADES.forEach((level, trades) -> {
                if (level == 1) generic.addAll(trades);
                else if (level > 1) rare.addAll(trades);
            });
//            generic.add(new ItemsForEmeralds(new ItemStack(APBlocks.SUNSTONE.get()), 2, 6, 20, 2, 0f));
//            generic.add(new ItemsForEmeralds(new ItemStack(APBlocks.MOONSTONE.get()), 2, 6, 20, 2, 0f));
        }
    }
}
