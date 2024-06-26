package architectspalette.core.event;

import architectspalette.core.platform.ForgeRegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeModeTabEventHandler {
    @SubscribeEvent
    public static void onCreativeTabRegister(BuildCreativeModeTabContentsEvent event) {
        List<Supplier<? extends Item>> itemlist = ForgeRegistryHelper.CREATIVE_TAB_ITEMS_MAP.get(event.getTabKey());
        if (itemlist != null) {
            // (ender) add this back later
//                if (!(item.get() instanceof VerticalSlabBlock && !VerticalSlabBlock.isQuarkEnabled())) {
            event.acceptAll(itemlist.stream().map((it) -> it.get().getDefaultInstance()).toList());
//                }
        }
    }
}
