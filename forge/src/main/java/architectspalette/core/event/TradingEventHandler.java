package architectspalette.core.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
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
    public static void onTradesLoaded (VillagerTradesEvent event) {
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
//            trades.putAll(professionMap.get(profession));
        }
//
//        if (event.getType() == VillagerProfession.FISHERMAN) {
//            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
//            trades.get(1).add(new BasicTrade(2, new ItemStack(APBlocks.COD_LOG.get(), 8), 5, 2));
//        }
    }
}
