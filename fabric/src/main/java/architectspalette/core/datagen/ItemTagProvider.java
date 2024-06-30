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
        // (ender) "tags will be simple", yeah right smartass
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.FENCES, ItemTags.FENCES);

        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);

        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);

        copy(APTags.TWISTED_LOGS, APTags.TWISTED_LOGS_ITEM);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);

        copy(APTags.NUBS, APTags.NUBS_ITEM);
        copy(APTags.COPPER_NUBS, APTags.COPPER_NUBS_ITEM);

        copy(APTags.ABYSSALINE, APTags.ABYSSALINE_ITEM);
        copy(APTags.HADALINE, APTags.HADALINE_ITEM);
    }


    @SafeVarargs
    private FabricTagProvider<Item>.FabricTagBuilder getOrCreateTag(TagKey<Item> tagKey, Supplier<Item>... suppliers) {
        var tag = getOrCreateTagBuilder(tagKey);
        Arrays.stream(suppliers).map(Supplier::get).forEach(tag::add);
        return tag;
    }
}
