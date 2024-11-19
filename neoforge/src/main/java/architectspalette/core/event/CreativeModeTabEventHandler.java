package architectspalette.core.event;

import architectspalette.core.integration.VerticalSlabs;
import architectspalette.core.platform.NeoRegistryHelper;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;
import java.util.function.Supplier;

public class CreativeModeTabEventHandler {
    @SubscribeEvent
    public static void onCreativeTabRegister(BuildCreativeModeTabContentsEvent event) {
        List<Supplier<? extends Item>> itemlist = NeoRegistryHelper.CREATIVE_TAB_ITEMS_MAP.get(event.getTabKey());
        if (itemlist != null) {
            event.acceptAll(itemlist.stream().filter(VerticalSlabs::isVisible).map((it) -> it.get().getDefaultInstance()).toList());
        }
    }
}
