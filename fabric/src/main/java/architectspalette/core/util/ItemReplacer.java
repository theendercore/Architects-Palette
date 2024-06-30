package architectspalette.core.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;

public interface ItemReplacer {
    void architects_Palette$replaceItem(Item oldItem, Item newItem);

    public static void replaceItem(LootPool.Builder o, Item oldItem, Item newItem) {
        ((ItemReplacer) o).architects_Palette$replaceItem(oldItem, newItem);
    }
}
