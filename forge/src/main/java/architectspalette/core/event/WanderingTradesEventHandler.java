package architectspalette.core.event;

import architectspalette.core.config.APConfig;
import architectspalette.core.registry.APBlocksFG;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;
import net.minecraft.world.item.ItemStack;
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
            // stack, price, count, max uses, xp, multiplier (or I hope it's this)
            generic.add(new ItemsForEmeralds(new ItemStack(APBlocksFG.SUNSTONE.get()), 2, 6, 20, 2, 0f));
            generic.add(new ItemsForEmeralds(new ItemStack(APBlocksFG.MOONSTONE.get()), 2, 6, 20, 2, 0f));
        }
    }
}
