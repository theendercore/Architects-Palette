package architectspalette.core.event;

import architectspalette.core.api.ItemReplacer;
import architectspalette.core.registry.APItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

public class ModifyLootTableEventHandler {
    public static void modify() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
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
