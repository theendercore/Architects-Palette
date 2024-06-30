package architectspalette.core.datagen;

import architectspalette.core.registry.APTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, architectspalette.core.datagen.BlockTagProvider btp) {
        super(output, registriesFuture, btp);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        copyBlockTags();
    }

    private void copyBlockTags() {
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(APTags.NUBS, APTags.NUBS_ITEM);
        copy(APTags.COPPER_NUBS, APTags.COPPER_NUBS_ITEM);
    }


    @SafeVarargs
    private FabricTagProvider<Item>.FabricTagBuilder getOrCreateTag(TagKey<Item> tagKey, Supplier<Item>... suppliers) {
        var tag = getOrCreateTagBuilder(tagKey);
        Arrays.stream(suppliers).map(Supplier::get).forEach(tag::add);
        return tag;
    }
}
