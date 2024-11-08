package architectspalette.core.util;

import architectspalette.core.registry.util.BlockNode;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.APConstants.LOGGER;
import static net.minecraft.data.models.BlockModelGenerators.createSlab;
import static net.minecraft.data.models.BlockModelGenerators.createStairs;

public interface ModelHelper {
    static void makeNodeModels(BlockModelGenerators gen, BlockNode node) {
        LOGGER.info("Processing node: {}", node.getName());
        node.forEach((n) -> {
            if (!n.getFlag(BlockNode.ExcludeFlag.MODELS)) {
                Block block = n.get();

                var parent = (n.parent != null) ? n.parent.get() : null;
                switch (n.type) {
                    case SLAB -> slab(gen, block, parent);
                    case VERTICAL_SLAB -> System.out.print("");
                    case STAIRS -> stairs(gen, block, parent);
                    case FENCE -> {
                    }
                    case WALL -> {
                    }
                    case NUB -> {
                    }
                    case PILLAR ->
                            gen.createRotatedPillarWithHorizontalVariant(block, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
                    case BASE, BRICKS, CRACKED, MOSSY, TILES, CHISELED, POLISHED, LAMP, DARK, PLATING ->
                            gen.createTrivialCube(block);
                    case SPECIAL -> LOGGER.warn("Special block not defined for node: {}", node.getName());
                }
            }
        });
    }

    static void stairs(BlockModelGenerators gen, Block block, Block texture) {
        stairs(gen, block, texture, texture);
    }

    static void stairs(BlockModelGenerators gen, Block block, Block ends, Block side) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(ends))
                .put(TextureSlot.SIDE, model(side))
                .put(TextureSlot.TOP, model(ends));
        stairs(gen, block, texture);
    }

    static void stairs(BlockModelGenerators gen, Block block, TextureMapping texture) {
        ResourceLocation id = ModelTemplates.STAIRS_INNER.create(block, texture, gen.modelOutput);
        ResourceLocation id2 = ModelTemplates.STAIRS_STRAIGHT.create(block, texture, gen.modelOutput);
        ResourceLocation id3 = ModelTemplates.STAIRS_OUTER.create(block, texture, gen.modelOutput);

        gen.blockStateOutput.accept(createStairs(block, id, id2, id3));
        gen.delegateItemModel(block, id2);
    }

    static void slab(BlockModelGenerators gen, Block block, Block texture) {
        slab(gen, block, texture, texture, texture);
    }

    static void slab(BlockModelGenerators gen, Block block, Block ends, Block side, Block full) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(ends))
                .put(TextureSlot.SIDE, model(side))
                .put(TextureSlot.TOP, model(ends));
        slab(gen, block, texture, full);
    }

    static void slab(BlockModelGenerators gen, Block block, TextureMapping texture, Block full) {
        var id = ModelTemplates.SLAB_BOTTOM.create(block, texture, gen.modelOutput);
        var id2 = ModelTemplates.SLAB_TOP.create(block, texture, gen.modelOutput);
        var id3 = model(full);

        gen.blockStateOutput.accept(createSlab(block, id, id2, id3));
        gen.delegateItemModel(block, id);
    }

    static ResourceLocation model(Block block) {
        return ModelLocationUtils.getModelLocation(block);
    }
}
