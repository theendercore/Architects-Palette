package architectspalette.core.integration;


import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocks;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static net.minecraft.world.entity.npc.VillagerProfession.*;

public class APTrades {
    // this + Profession -> Map of trades for that profession, Map + Trade level -> List of trades of that level
    public static final Map<VillagerProfession, Map<Integer, LinkedList<VillagerTrades.ItemListing>>> PROFESSION_MAP = new HashMap<>();
    public static final Map<Integer, LinkedList<VillagerTrades.ItemListing>> WONDERING_TRADES = new HashMap<>();

    private static void addTrade(VillagerProfession prof, Integer level, VillagerTrades.ItemListing trade) {
        PROFESSION_MAP.putIfAbsent(prof, new HashMap<>());
        PROFESSION_MAP.get(prof).putIfAbsent(level, new LinkedList<>());
        PROFESSION_MAP.get(prof).get(level).add(trade);
    }

    private static void addWondering(VillagerTrades.ItemListing trade) {
        addWondering(1, trade);
    }

    // (ender) this is here is there is ever a need for rare trades
    private static void addWondering(Integer level, VillagerTrades.ItemListing trade) {
        WONDERING_TRADES.putIfAbsent(level, new LinkedList<>());
        WONDERING_TRADES.get(level).add(trade);
    }

    public static void registerTrades() {
        if (APConfig.VILLAGER_TRADES_ENABLED.get()) {
            //(ender) just here to help with math
            // ItemsForEmeralds // selling item
            //itemLike, price, count, max uses, xp, (multiplier)

            // Fish Blocks
            addTrade(FISHERMAN, 2, new ItemsForEmeralds(APBlocks.COD_LOG.get(), 2, 8, 6, 4));
            addTrade(FISHERMAN, 2, new ItemsForEmeralds(APBlocks.SALMON_LOG.get(), 4, 2, 8, 6));
            // Entrails
            addTrade(BUTCHER, 2, new ItemsForEmeralds(APBlocks.ENTRAILS.get().asItem(), 4, 1, 5, 5, 0.0f));
            // Plating
            addTrade(ARMORER, 2, new ItemsForEmeralds(APBlocks.PLATING_BLOCK.get().asItem(), 4, 3, 12, 6, 0.1F));
            // Pipes
            addTrade(TOOLSMITH, 2, new ItemsForEmeralds(APBlocks.PIPE.get().asItem(), 4, 4, 12, 6, 0.1F));
            // Spools
            addTrade(SHEPHERD, 2, new ItemsForEmeralds(APBlocks.SPOOL.get().asItem(), 4, 1, 2, 5, 0.0F));

            // Temporary survival recipes until properly implemented
            addTrade(MASON, 1, new ItemsForEmeralds(APBlocks.MYONITE.get(), 3, 1, 16, 5));
            addTrade(MASON, 1, new ItemsForEmeralds(APBlocks.OLIVESTONE_BRICK.get(), 3, 1, 16, 5));
        }
        if (APConfig.WANDERER_TRADES_ENABLED.get()) {
            //itemLike, price, count, max uses, xp
            addWondering(new ItemsForEmeralds(APBlocks.SUNSTONE.get(), 2, 6, 20, 2));
            addWondering(new ItemsForEmeralds(APBlocks.MOONSTONE.get(), 2, 6, 20, 2));
        }
    }
}
