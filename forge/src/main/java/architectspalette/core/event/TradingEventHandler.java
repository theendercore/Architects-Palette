package architectspalette.core.event;

import architectspalette.core.config.APConfig;
import architectspalette.core.integration.APTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.integration.APTrades.PROFESSION_MAP;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class TradingEventHandler {

    @SubscribeEvent
    public static void onTradesLoaded(VillagerTradesEvent event) {
        VillagerProfession profession = event.getType();
        Map<Integer, LinkedList<VillagerTrades.ItemListing>> prof_trades = PROFESSION_MAP.get(profession);
        if (prof_trades != null) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.forEach((level, list) -> {
                        if (prof_trades.containsKey(level)) {
                            list.addAll(prof_trades.get(level));
                        }
                    }
            );
        }
    }

    @SubscribeEvent
    public static void onWanderingTradesLoaded(WandererTradesEvent event) {
        if (APConfig.WANDERER_TRADES_ENABLED.get()) {
            List<VillagerTrades.ItemListing> generic = event.getGenericTrades();
            List<VillagerTrades.ItemListing> rare = event.getRareTrades();
            APTrades.WONDERING_TRADES.forEach((level, trades) -> {
                if (level == 1) generic.addAll(trades);
                else if (level > 1) rare.addAll(trades);
            });
        }
    }
}
