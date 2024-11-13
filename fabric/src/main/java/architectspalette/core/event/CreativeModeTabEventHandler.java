package architectspalette.core.event;

import architectspalette.core.integration.VerticalSlabs;
import architectspalette.core.platform.FabricRegistryHelper;
import architectspalette.core.registry.APBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.*;

public class CreativeModeTabEventHandler {
    public static void modifyTabs() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            List<Supplier<? extends Item>> itemlist = FabricRegistryHelper.CREATIVE_TAB_ITEMS_MAP.get(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(group).get());
            if (itemlist != null) {
                entries.acceptAll(itemlist.stream().filter(VerticalSlabs::isVisible).map((it) -> it.get().getDefaultInstance()).toList());
            }
        });
        // (ender) Temporary creative tab
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, modLoc(MOD_ID), FabricItemGroup.builder()
                .title(Component.translatable(MOD_NAME))
                .icon(() -> new ItemStack(APBlocks.CHISELED_ABYSSALINE_BRICKS.get()))
                .displayItems((parameters, output) -> output.acceptAll(BuiltInRegistries.ITEM.stream()
                        .filter(it -> BuiltInRegistries.ITEM.getKey(it).getNamespace().equals(MOD_ID) && VerticalSlabs.isVisible(() -> it))
                        .map(ItemStack::new).toList()
                ))
                .build());
    }
}
