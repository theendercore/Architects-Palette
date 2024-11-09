package architectspalette.core.util.model;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.stream.Stream;

import static architectspalette.core.APConstants.LOGGER;
import static architectspalette.core.util.model.ModelHelpers.*;

public interface ModelGenHelper {
    List<StoneBlockSet> uniqueNubs = Stream.of(APBlocks.PLATING_BLOCK, APBlocks.NETHER_BRASS).toList();

    static void makeNodeModels(BlockModelGenerators gen, BlockNode node) {
        LOGGER.info("Processing node: {}", node.getName());
        node.forEach((n) -> {
            if (!n.getFlag(BlockNode.ExcludeFlag.MODELS)) {
                Block block = n.get();

                var parent = (n.parent != null) ? n.parent.get() : null;
                switch (n.type) {
                    case BASE, BRICKS, CRACKED, MOSSY, TILES, CHISELED, POLISHED, LAMP, DARK, PLATING ->
                            gen.createTrivialCube(block);
                    case PILLAR -> pillar(gen, block);
                    case SLAB -> slab(gen, block, parent);
                    case VERTICAL_SLAB -> verticalSlab(gen, block, parent);
                    case STAIRS -> stairs(gen, block, parent);
                    case WALL -> wall(gen, block, parent);
                    case FENCE -> fence(gen, block, parent);
                    case NUB -> {
                        if (node == APBlocks.BREAD_BLOCK) nubUnique(gen, block);
                        else nub(gen, block);
                    }
                    case SPECIAL -> LOGGER.warn("Special block not defined for node: {}", node.getName());
                }
            }
        });
    }

    static void makeBlockSetModels(BlockModelGenerators gen, StoneBlockSet set) {
        var name = set.get().getName().getString();
        LOGGER.info("Processing Set: {}", name);
        var parent = set.get();
        set.forEachPart((part, block) -> {
            switch (part) {
                case BLOCK -> gen.createTrivialCube(block);
                case PILLAR -> pillar(gen, block);
                case SLAB -> slab(gen, block, parent);
                case VERTICAL_SLAB -> verticalSlab(gen, block, parent);
                case STAIRS -> stairs(gen, block, parent);
                case WALL -> wall(gen, block, parent);
                case FENCE -> fence(gen, block, parent);
                case NUB -> {
                    if ((uniqueNubs.contains(set))) nubUnique(gen, block);
                    else nub(gen, block);
                }
            }
        });
    }
}
