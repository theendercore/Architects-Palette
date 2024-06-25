package architectspalette.core.integration;


import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocksFG;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;
import net.minecraft.world.item.ItemStack;

import static architectspalette.core.event.TradingEventHandler.add_trade;
import static net.minecraft.world.entity.npc.VillagerProfession.*;

public class APTrades {

    public static void registerTrades() {
        if (APConfig.VILLAGER_TRADES_ENABLED.get()) {
            // (ender) stack, price, count, max uses, xp, multiplier (or I hope it's this)

            // Fish Blocks
            add_trade(FISHERMAN, 2, new ItemsForEmeralds(new ItemStack(APBlocksFG.COD_LOG.get()), 2, 8, 6, 4, 0.05f));
            add_trade(FISHERMAN, 2, new ItemsForEmeralds(new ItemStack(APBlocksFG.SALMON_LOG.get()), 2, 8, 6, 4, 0.05f));
            // Entrails
            add_trade(BUTCHER, 2, new ItemsForEmeralds(new ItemStack(APBlocksFG.ENTRAILS.get()), 1, 5, 5, 4, 0.0f));
            // Plating
            add_trade(ARMORER, 2, new ItemsForEmeralds(new ItemStack(APBlocksFG.PLATING_BLOCK.get()),3,12, 6, 4, 0.1F));
            // Pipes
            add_trade(TOOLSMITH, 2, new ItemsForEmeralds(new ItemStack(APBlocksFG.PIPE.get()),4,12, 6, 4, 0.1F));
            // Spools
            add_trade(SHEPHERD, 2, new ItemsForEmeralds(new ItemStack(APBlocksFG.SPOOL.get()),1,2, 5, 4, 0.0F));

            // Temporary survival recipes until properly implemented
            add_trade(MASON, 1, new ItemsForEmeralds(new ItemStack(APBlocksFG.MYONITE.get()), 1, 16, 5, 3, 0.05f));
            add_trade(MASON, 1, new ItemsForEmeralds(new ItemStack(APBlocksFG.OLIVESTONE_BRICK.get()),1, 16, 5, 3, 0.05f));
        }
    }

}
