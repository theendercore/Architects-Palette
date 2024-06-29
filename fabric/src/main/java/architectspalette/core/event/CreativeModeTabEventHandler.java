package architectspalette.core.event;

import architectspalette.core.platform.FabricRegistryHelper;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public class CreativeModeTabEventHandler {
    public static void modifyTabs() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            List<Supplier<? extends Item>> itemlist = FabricRegistryHelper.CREATIVE_TAB_ITEMS_MAP.get(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(group).get());
            if (itemlist != null) {
                // (ender) add this back later
//                if (!(item.get() instanceof VerticalSlabBlock && !VerticalSlabBlock.isQuarkEnabled())) {
                entries.acceptAll(itemlist.stream().map((it) -> it.get().getDefaultInstance()).toList());
//                }
            }
        });
    }
}
