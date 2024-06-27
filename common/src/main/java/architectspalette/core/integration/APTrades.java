package architectspalette.core.integration;


import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocks;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldForItems;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static net.minecraft.world.entity.npc.VillagerProfession.*;

public class APTrades {
    // this + Profession -> Map of trades for that profession, Map + Trade level -> List of trades of that level
    public static final Map<VillagerProfession, Map<Integer, LinkedList<VillagerTrades.ItemListing>>> PROFESSION_MAP = new HashMap<>();


    public static void add_trade(VillagerProfession prof, Integer level, VillagerTrades.ItemListing trade) {
        PROFESSION_MAP.putIfAbsent(prof, new HashMap<>());
        PROFESSION_MAP.get(prof).putIfAbsent(level, new LinkedList<>());
        PROFESSION_MAP.get(prof).get(level).add(trade);
    }

    //(currently un-used as i dont know where to put it)
    private static void clear_map() {
        PROFESSION_MAP.clear();
    }

    public static void registerTrades() {
        if (APConfig.VILLAGER_TRADES_ENABLED.get()) {
            //(ender) ItemsForEmeralds // buying
            //stack, price, count, max uses, xp, multiplier

            // (ender) EmeraldForItems // selling
            //stack, count, max uses, xp, costs

            // Fish Blocks
            add_trade(FISHERMAN, 2, new EmeraldForItems(APBlocks.COD_LOG.get(), 8, 6, 4, 2));
//            add_trade(FISHERMAN, 2, new EmeraldForItems(new ItemStack(APBlocks.SALMON_LOG.get()), 2, 8, 6, 4, 0.05f));
            // Entrails
//            add_trade(BUTCHER, 2, new EmeraldForItems(new ItemStack(APBlocks.ENTRAILS.get()), 1, 5, 5, 4, 0.0f));
            // Plating
//            add_trade(ARMORER, 2, new EmeraldForItems(new ItemStack(APBlocks.PLATING_BLOCK.get()), 3, 12, 6, 4, 0.1F));
            // Pipes
//            add_trade(TOOLSMITH, 2, new EmeraldForItems(new ItemStack(APBlocks.PIPE.get()), 4, 12, 6, 4, 0.1F));
            // Spools
//            add_trade(SHEPHERD, 2, new EmeraldForItems(new ItemStack(APBlocks.SPOOL.get()), 1, 2, 5, 4, 0.0F));

            // Temporary survival recipes until properly implemented
//            add_trade(MASON, 1, new EmeraldForItems(new ItemStack(APBlocks.MYONITE.get()), 1, 16, 5, 3, 0.05f));
//            add_trade(MASON, 1, new EmeraldForItems(new ItemStack(APBlocks.OLIVESTONE_BRICK.get()), 1, 16, 5, 3, 0.05f));
        }
    }

}
