package architectspalette.core.event;

import architectspalette.core.registry.APItems;
import architectspalette.core.util.ItemReplacer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

public class ModifyLootTableEventHandler {
    public static void modify() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, lookup) -> {
            if (key == EntityType.WITHER_SKELETON.getDefaultLootTable()) {
                tableBuilder.modifyPools(original -> {
                            ItemReplacer.replaceItem(original, Items.BONE, APItems.WITHERED_BONE.get());
                            original.build();
                        }
                );
            }
        });
    }
}
