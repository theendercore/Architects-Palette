package architectspalette.core.datagen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class APLootTablesProvider extends FabricBlockLootTableProvider {
    public APLootTablesProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        BlockNode.forAllBaseNodes(this::processBlockNode);
        this.add(APBlocks.TWISTED_LEAVES.get(), (leaves) -> this.createLeavesDrops(leaves, APBlocks.TWISTED_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    private void slab(Block block) {
        this.add(block, createSlabItemTable(block));
    }

    private void processBlockNode(BlockNode node) {
        node.forEach((n) -> {
            //Todo: Silk Touch Flag
            var block = n.get();
            switch (n.type) {
                case SLAB, VERTICAL_SLAB -> slab(block);
                default -> this.dropSelf(block);
            }
        });
    }
}
