package architectspalette.core.util.model;

import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static architectspalette.core.APConstants.LOGGER;
import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.util.BlockNode.BlockType.SLAB;
import static architectspalette.core.registry.util.BlockNode.BlockType.TILES;
import static architectspalette.core.util.model.BoardModelGenerator.makeBoards;
import static architectspalette.core.util.model.ModelHelpers.*;

public interface NodeAndSetGenerator {
    List<StoneBlockSet> uniqueNubs = Stream.of(PLATING_BLOCK, NETHER_BRASS).toList();
    Map<BlockNode, BiConsumer<BlockModelGenerators, Block>> specialGens = Map.of(
            ORACLE_BLOCK, BlockModelGenerators::createTrivialCube,
            MOONSHALE, BlockModelGenerators::createTrivialCube,
            BREAD_BLOCK, ModelHelpers::specialBread
    );
    List<StoneBlockSet> fancy = Stream.of(PLATING_BLOCK, POLISHED_GLOWSTONE, NETHER_BRASS, ANCIENT_PLATING, WARDSTONE).toList();
    List<StoneBlockSet> abyssaline = Stream.of(ABYSSALINE_BRICKS, ABYSSALINE_TILES, HADALINE_BRICKS, HADALINE_TILES).toList();
    List<StoneBlockSet> tiles = Stream.of(FLINT_TILES, GILDED_SANDSTONE, BASALT_TILES).toList();

    static void makeNodeModels(BlockModelGenerators gen, BlockNode node) {
        if (boards.contains(node)) return;
        LOGGER.info("Processing node: {}", node.getName());
        node.forEach((n) -> {
            if (!n.getFlag(BlockNode.ExcludeFlag.MODELS)) {
                Block block = n.get();
                var parent = (n.parent != null) ? n.parent.get() : null;
                switch (n.type) {
                    case BASE, BRICKS, CRACKED, MOSSY, TILES, CHISELED, POLISHED, LAMP, DARK, PLATING -> {
                        switch (n.style) {
                            case CUBE -> gen.createTrivialCube(block);
                            case TOP_SIDES -> staticPillar(gen, block);
                            case TOP_SIDE_BOTTOM -> staticCubeTB(gen, block);
                        }
                    }
                    case PILLAR -> pillar(gen, block);
                    case SLAB -> slab(gen, block, parent);
                    case VERTICAL_SLAB -> verticalSlab(gen, block, parent);
                    case STAIRS -> stairs(gen, block, parent);
                    case WALL -> wall(gen, block, parent);
                    case FENCE -> fence(gen, block, parent);
                    case NUB -> {
                        if (node == BREAD_BLOCK) nubUnique(gen, block);
                        else nub(gen, block);
                    }
                    case SPECIAL -> {
                        var generator = specialGens.get(node);
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
        var isAbyssaline = abyssaline.contains(set);
        set.forEachPart((part, block) -> {
            switch (part) {
                case BLOCK -> {
                    if (isAbyssaline) abyssalineCube(gen, block);
                    else if (set == CUT_NETHER_BRASS) staticSidePillar(gen, block);
                    else if (tiles.contains(set)) tile(gen, block);
                    else gen.createTrivialCube(block);
                }
                case PILLAR -> pillar(gen, block);
                case SLAB -> {
                    if (fancy.contains(set)) specialSlab(gen, block, parent);
                    else if (isAbyssaline) abyssalineSlab(gen, block, parent);
                    else slab(gen, block, parent);
                }
                case VERTICAL_SLAB -> {
                    if (isAbyssaline) abyssalineVerticalSlab(gen, block, parent);
                    else verticalSlab(gen, block, parent);
                }
                case STAIRS -> stairs(gen, block, parent);
                case WALL -> {
                    if (fancy.contains(set)) fancyWall(gen, block);
                    else wall(gen, block, parent);
                }
                case FENCE -> fence(gen, block, parent);
                case NUB -> {
                    if (uniqueNubs.contains(set)) nubUnique(gen, block);
                    else nub(gen, block);
                }
            }
        });
    }

    static void excludedModelGen(BlockModelGenerators gen) {
        cerebralTiles(gen, CEREBRAL_BLOCK.getChild(TILES).get());
        bread(gen, BREAD_BLOCK.get());
        breadSlab(gen, BREAD_BLOCK.getChild(SLAB).get(), BREAD_BLOCK.get());
        makeBoards(gen);
    }
}
