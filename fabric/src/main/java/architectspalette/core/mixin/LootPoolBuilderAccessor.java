package architectspalette.core.mixin;

import architectspalette.core.util.ItemReplacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.spongepowered.asm.mixin.*;

@Mixin(LootPool.Builder.class)
public abstract class LootPoolBuilderAccessor implements ItemReplacer {

    @Mutable
    @Shadow @Final private ImmutableList.Builder<LootPoolEntryContainer> entries;

    @Override
    public void architects_Palette$replaceItem(Item oldItem, Item newItem) {
        var newEntries = ImmutableList.<LootPoolEntryContainer>builder();
        for (var entry : this.entries.build()) {
            if (entry instanceof LootItemAccessor itemEntry && itemEntry.getItem().value() == oldItem) {
                itemEntry.setItem(new Holder.Direct<>(newItem));
            }
            newEntries.add(entry);
        }
        this.entries = newEntries;
    }
}
