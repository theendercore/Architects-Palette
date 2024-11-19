package architectspalette.core.event;

import architectspalette.core.config.APConfig;
import architectspalette.core.integration.APTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static architectspalette.core.integration.APTrades.PROFESSION_MAP;

public class TradingEventHandler {

    @SubscribeEvent
    public static void onTradesLoaded(VillagerTradesEvent event) {
        VillagerProfession profession = event.getType();
        Map<Integer, LinkedList<VillagerTrades.ItemListing>> tradeMap = PROFESSION_MAP.get(profession);
        if (tradeMap == null) return;
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        trades.forEach((level, list) -> {
            if (tradeMap.containsKey(level)) {
                list.addAll(tradeMap.get(level));
            }
        });
    }

    @SubscribeEvent
    public static void onWanderingTradesLoaded(WandererTradesEvent event) {
        if (!APConfig.WANDERER_TRADES_ENABLED.get()) return;
        List<VillagerTrades.ItemListing> generic = event.getGenericTrades();
//      (ender) I just left it in here in case there is a need for rare trades later
//        List<VillagerTrades.ItemListing> rare = event.getRareTrades();
        APTrades.WONDERING_TRADES.forEach((level, trades) -> {
            generic.addAll(trades);
//            if (level == 1) generic.addAll(trades);
//            else if (level > 1) rare.addAll(trades);
        });
    }
}
