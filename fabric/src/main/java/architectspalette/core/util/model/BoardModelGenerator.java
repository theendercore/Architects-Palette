package architectspalette.core.util.model;

import architectspalette.core.registry.util.BlockNode;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.model.WrappedModelTemplate.boardWarp;
import static architectspalette.core.registry.APBlocks.boards;
import static architectspalette.core.util.model.ModelHelpers.createVerticalSlab;
import static architectspalette.core.util.model.ModelHelpers.model;
import static architectspalette.core.util.model.Models.VERTICAL_SLAB;
import static net.minecraft.data.models.BlockModelGenerators.*;

public interface BoardModelGenerator {
    static void makeBoards(BlockModelGenerators gen) {
        boards.forEach(type -> processNode(gen, type));
    }

    static void processNode(BlockModelGenerators gen, BlockNode node) {
        node.forEach((n) -> {
            Block block = n.get();
            var parent = (n.parent != null) ? n.parent.get() : null;
            switch (n.type) {
                case BASE -> board(gen, block);
                case SLAB -> boardSlab(gen, block, parent);
                case VERTICAL_SLAB -> boardVerticalSlab(gen, block, parent);
                case STAIRS -> boardStairs(gen, block, parent);
                case WALL -> boardWall(gen, block, parent);
                default -> {
                }
            }
        });
    }

    static void board(BlockModelGenerators gen, Block block) {
        var tex = TextureMapping.cube(block);
        var id = boardWarp(ModelTemplates.CUBE_ALL).create(block, tex, gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, id));
        gen.delegateItemModel(block, id);
    }

    static void boardSlab(BlockModelGenerators gen, Block block, Block full) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(full))
                .put(TextureSlot.SIDE, model(full))
                .put(TextureSlot.TOP, model(full));
        var id = boardWarp(ModelTemplates.SLAB_BOTTOM).create(block, texture, gen.modelOutput);
        var id2 = boardWarp(ModelTemplates.SLAB_TOP).create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createSlab(block, id, id2, model(full)));
        gen.delegateItemModel(block, id);
    }

    static void boardVerticalSlab(BlockModelGenerators gen, Block block, Block full) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(full))
                .put(TextureSlot.SIDE, model(full))
                .put(TextureSlot.TOP, model(full));
        var vSlab = boardWarp(VERTICAL_SLAB).create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createVerticalSlab(block, vSlab, model(full)));
        gen.delegateItemModel(block, vSlab);
    }

    static void boardStairs(BlockModelGenerators gen, Block block, Block full) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(full))
                .put(TextureSlot.SIDE, model(full))
                .put(TextureSlot.TOP, model(full));
        ResourceLocation id = boardWarp(ModelTemplates.STAIRS_INNER).create(block, texture, gen.modelOutput);
        ResourceLocation id2 = boardWarp(ModelTemplates.STAIRS_STRAIGHT).create(block, texture, gen.modelOutput);
        ResourceLocation id3 = boardWarp(ModelTemplates.STAIRS_OUTER).create(block, texture, gen.modelOutput);

        gen.blockStateOutput.accept(createStairs(block, id, id2, id3));
        gen.delegateItemModel(block, id2);
    }

    static void boardWall(BlockModelGenerators gen, Block wallBlock, Block full) {
        TextureMapping texture = TextureMapping.defaultTexture(model(wallBlock)).put(TextureSlot.WALL, model(full));
        ResourceLocation id = boardWarp(ModelTemplates.WALL_POST).create(wallBlock, texture, gen.modelOutput);
        ResourceLocation id2 = boardWarp(ModelTemplates.WALL_LOW_SIDE).create(wallBlock, texture, gen.modelOutput);
        ResourceLocation id3 = boardWarp(ModelTemplates.WALL_TALL_SIDE).create(wallBlock, texture, gen.modelOutput);
        gen.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, id, id2, id3));
        gen.delegateItemModel(wallBlock, boardWarp(ModelTemplates.WALL_INVENTORY).create(wallBlock, texture, gen.modelOutput));

    }
}
