package architectspalette.core.datagen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APBlocksFG;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import architectspalette.core.registry.util.StoneBlockSet.SetComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class APLootTables extends LootTableProvider {
    public APLootTables(PackOutput pack, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pack, Collections.emptySet(), List.of(new SubProviderEntry(APBlockLoot::new, LootContextParamSets.BLOCK)), lookupProvider);
    }

    private static class APBlockLoot extends BlockLootSubProvider {

        protected APBlockLoot(HolderLookup.Provider lookupProvider) {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
        }

        @Override
        protected void generate() {
            BlockNode.forAllBaseNodes(this::processBlockNode);
            this.add(APBlocks.TWISTED_LEAVES.get(), (leaves) -> this.createLeavesDrops(leaves, APBlocks.TWISTED_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            List<Block> blocks = new LinkedList<>();
            BlockNode.forAllBaseNodes((node -> node.forEach(n -> blocks.add(n.get()))));
            blocks.add(APBlocks.TWISTED_LEAVES.get());
            return blocks;
            //return APBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
        }

        private void slab(Block block) {
            this.add(block, createSlabItemTable(block));
        }

        private void processStoneBlockSet(StoneBlockSet set) {
            set.forEachPart((part, block) -> {
                if (part == SetComponent.SLAB) {
                    slab(block);
                }
                else if (part == SetComponent.VERTICAL_SLAB) {
                    slab(block);
                }
                else {
                    this.dropSelf(block);
                }
            });
        }

        private void processBlockNode(BlockNode node) {
            node.forEach((n) -> {
                //Todo: Silk Touch Flag
                var block = n.get();
                switch(n.type) {
                    case SLAB, VERTICAL_SLAB -> slab(block);
                    default -> this.dropSelf(block);
                }
            });
        }
    }
}
