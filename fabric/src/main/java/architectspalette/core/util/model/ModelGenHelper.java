package architectspalette.core.util.model;

import architectspalette.core.platform.Services;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static architectspalette.core.APConstants.LOGGER;
import static architectspalette.core.util.model.ModelHelpers.*;
import static net.minecraft.data.models.BlockModelGenerators.createSimpleBlock;

public interface ModelGenHelper {
    List<StoneBlockSet> uniqueNubs = Stream.of(APBlocks.PLATING_BLOCK, APBlocks.NETHER_BRASS).toList();

    Map<Block, BiConsumer<BlockModelGenerators, Block>> specialGenerators = Map.of(
            APBlocks.ORACLE_BLOCK.getChild(BlockNode.BlockType.SPECIAL).get(), BlockModelGenerators::createTrivialCube,
            APBlocks.MOONSHALE.getChild(BlockNode.BlockType.SPECIAL).get(), BlockModelGenerators::createTrivialCube,
            APBlocks.BREAD_BLOCK.getChild(BlockNode.BlockType.SPECIAL).get(), ModelGenHelper::makeBread
    );

    List<StoneBlockSet> specialModels = Stream.of(
            APBlocks.PLATING_BLOCK, APBlocks.POLISHED_GLOWSTONE, APBlocks.NETHER_BRASS, APBlocks.ANCIENT_PLATING, APBlocks.WARDSTONE
    ).toList();

    List<StoneBlockSet> abyssalineModels = Stream.of(
            APBlocks.ABYSSALINE_BRICKS, APBlocks.ABYSSALINE_TILES, APBlocks.HADALINE_BRICKS, APBlocks.HADALINE_TILES
    ).toList();

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
                    case SPECIAL -> {
                        var generator = specialGenerators.get(block);
                        if (generator != null) generator.accept(gen, block);
                        else
                            LOGGER.warn("Special block [{}] not defined for node: {}", block.getName().getString(), node.getName());
                    }
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
                case BLOCK -> {
                    if (abyssalineModels.contains(set)) abyssalineCube(gen, block);
                    else gen.createTrivialCube(block);
                }
                case PILLAR -> pillar(gen, block);
                case SLAB -> {
                    if (specialModels.contains(set)) specialSlab(gen, block, parent);
                    else if (abyssalineModels.contains(set)) abyssalineSlab(gen, block, parent);
                    else slab(gen, block, parent);
                }
                case VERTICAL_SLAB -> {
                    if (abyssalineModels.contains(set)) abyssalineVerticalSlab(gen, block, parent);
                    else verticalSlab(gen, block, parent);
                }
                case STAIRS -> stairs(gen, block, parent);
                case WALL -> {
                    if (specialModels.contains(set)) fancyWall(gen, block);
                    else wall(gen, block, parent);
                }
                case FENCE -> fence(gen, block, parent);
                case NUB -> {
                    if ((uniqueNubs.contains(set))) nubUnique(gen, block);
                    else nub(gen, block);
                }
            }
        });
    }

    static void makeBread(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(block).put(TextureSlot.ALL, model(block));
        var model = ModelTemplates.CUBE_ALL.create(Services.REGISTRY.getId(() -> block).withPrefix("block/bread/"), texture, gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, model));
        gen.delegateItemModel(block, model);
    }
}
