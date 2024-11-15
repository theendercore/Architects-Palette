package architectspalette.core.mixin;

import architectspalette.core.util.ItemReplacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;

@Mixin(LootPool.Builder.class)
public abstract class LootPoolBuilderAccessor implements ItemReplacer {

    @Mutable
    @Shadow
    @Final
    private ImmutableList.Builder<LootPoolEntryContainer> entries;

    @Override
    public void architects_Palette$replaceItem(Item oldItem, Item newItem) {
        var open = new ArrayList<>(this.entries.build());
        for (int i = 0; i < open.size(); i++) {
            var entry = open.get(i);
            if (entry instanceof LootItemAccessor itemEntry && itemEntry.getItem().value() == oldItem) {
                itemEntry.setItem(new Holder.Direct<>(newItem));
                open.set(i, entry);
            }
        }
        this.entries = ImmutableList.<LootPoolEntryContainer>builder().addAll(open);
    }
}
