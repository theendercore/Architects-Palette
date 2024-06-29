package architectspalette.core.integration;


import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocks;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldForItems;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static net.minecraft.world.entity.npc.VillagerProfession.FISHERMAN;

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
            //(ender) ItemsForEmeralds // buying
            //stack, price, count, max uses, xp, multiplier

            // (ender) EmeraldForItems // selling
            //itemLike, count, max uses, xp, costs

            // Fish Blocks
            addTrade(FISHERMAN, 2, new EmeraldForItems(APBlocks.COD_LOG.get(), 8, 6, 4, 2));
//            addTrade(FISHERMAN, 2, new EmeraldForItems(new ItemStack(APBlocks.SALMON_LOG.get()), 2, 8, 6, 4, 0.05f));
            // Entrails
//            addTrade(BUTCHER, 2, new EmeraldForItems(new ItemStack(APBlocks.ENTRAILS.get()), 1, 5, 5, 4, 0.0f));
            // Plating
//            addTrade(ARMORER, 2, new EmeraldForItems(new ItemStack(APBlocks.PLATING_BLOCK.get()), 3, 12, 6, 4, 0.1F));
            // Pipes
//            addTrade(TOOLSMITH, 2, new EmeraldForItems(new ItemStack(APBlocks.PIPE.get()), 4, 12, 6, 4, 0.1F));
            // Spools
//            addTrade(SHEPHERD, 2, new EmeraldForItems(new ItemStack(APBlocks.SPOOL.get()), 1, 2, 5, 4, 0.0F));

            // Temporary survival recipes until properly implemented
//            addTrade(MASON, 1, new EmeraldForItems(new ItemStack(APBlocks.MYONITE.get()), 1, 16, 5, 3, 0.05f));
//            addTrade(MASON, 1, new EmeraldForItems(new ItemStack(APBlocks.OLIVESTONE_BRICK.get()), 1, 16, 5, 3, 0.05f));
        }
        if (APConfig.WANDERER_TRADES_ENABLED.get()) {
            //stack, count, max uses, xp, costs
            addWondering(new EmeraldForItems(APBlocks.SUNSTONE.get(), 2, 20, 2, 6));
            addWondering(new EmeraldForItems(APBlocks.MOONSTONE.get(), 2, 20, 2, 6));

        }
    }

}
