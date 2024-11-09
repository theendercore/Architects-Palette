package architectspalette.core.util.model;

import architectspalette.content.blocks.CageLanternBlock;
import architectspalette.content.blocks.NubBlock;
import architectspalette.content.blocks.PipeBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.util.model.Models.*;
import static net.minecraft.data.models.BlockModelGenerators.createSlab;
import static net.minecraft.data.models.BlockModelGenerators.createStairs;

public interface ModelHelpers {

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

    // Slabs
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

    // Vertical Slab
    static void verticalSlab(BlockModelGenerators gen, Block block, TextureMapping texture, Block full) {
        var vSlab = VERTICAL_SLAB.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createVerticalSlab(block, vSlab, model(full)));
        gen.delegateItemModel(block, vSlab);
    }

    static void verticalSlab(BlockModelGenerators gen, Block block, Block ends, Block side, Block full) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(ends))
                .put(TextureSlot.SIDE, model(side))
                .put(TextureSlot.TOP, model(ends));
        verticalSlab(gen, block, texture, full);
    }

    static void verticalSlab(BlockModelGenerators gen, Block block, Block texture) {
        verticalSlab(gen, block, texture, texture, texture);
    }

    static BlockStateGenerator createVerticalSlab(Block block, ResourceLocation half, ResourceLocation full) {
        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(VerticalSlabBlock.TYPE)
                .select(VerticalSlabBlock.VerticalSlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, full))
                .select(VerticalSlabBlock.VerticalSlabType.NORTH, Variant.variant().with(VariantProperties.MODEL, half))
                .select(VerticalSlabBlock.VerticalSlabType.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.MODEL, half))
                .select(VerticalSlabBlock.VerticalSlabType.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.MODEL, half))
                .select(VerticalSlabBlock.VerticalSlabType.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.MODEL, half))
        );
    }

    // Walls
    static void wall(BlockModelGenerators gen, Block block) {
        wall(gen, block, block);
    }

    static void wall(BlockModelGenerators gen, Block block, Block textureBlock) {
        TextureMapping texture = TextureMapping.defaultTexture(model(block)).put(TextureSlot.WALL, model(textureBlock));
        wall(gen, block, texture);
    }

    static void wall(BlockModelGenerators gen, Block wallBlock, TextureMapping texture) {
        ResourceLocation id = ModelTemplates.WALL_POST.create(wallBlock, texture, gen.modelOutput);
        ResourceLocation id2 = ModelTemplates.WALL_LOW_SIDE.create(wallBlock, texture, gen.modelOutput);
        ResourceLocation id3 = ModelTemplates.WALL_TALL_SIDE.create(wallBlock, texture, gen.modelOutput);
        gen.blockStateOutput.accept(BlockModelGenerators.createWall(wallBlock, id, id2, id3));
        gen.delegateItemModel(wallBlock, ModelTemplates.WALL_INVENTORY.create(wallBlock, texture, gen.modelOutput));

    }

    // Fence
    static void fence(BlockModelGenerators gen, Block block, TextureMapping texture) {
        var post = ModelTemplates.FENCE_POST.create(block, texture, gen.modelOutput);
        var side = ModelTemplates.FENCE_SIDE.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(BlockModelGenerators.createFence(block, post, side));
        var itemModel = ModelTemplates.FENCE_INVENTORY.create(block, texture, gen.modelOutput);
        gen.delegateItemModel(block, itemModel);
    }

    static void fence(BlockModelGenerators gen, Block block, Block textureBlock) {
        fence(gen, block, TextureMapping.defaultTexture(model(textureBlock)));
    }

    static void fence(BlockModelGenerators gen, Block block) {
        fence(gen, block, block);
    }

    // Railing
    static void railing(BlockModelGenerators gen, Block block, TextureMapping texture) {
        var post = RAILING_POST.create(block, texture, gen.modelOutput);
        var side = RAILING_SIDE.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(BlockModelGenerators.createFence(block, post, side));
        gen.delegateItemModel(block, RAILING_INVENTORY.create(block, texture, gen.modelOutput));
    }

    static void railing(BlockModelGenerators gen, Block block, Block textureBlock) {
        railing(gen, block, TextureMapping.defaultTexture(model(textureBlock)));
    }

    static void railing(BlockModelGenerators gen, Block block) {
        railing(gen, block, block);
    }

    // Nub
    static void nub(BlockModelGenerators gen, Block block, TextureMapping texture) {
        var base = NUB.create(block, texture, gen.modelOutput);
        var horizontal = NUB_HORIZONTAL.createWithSuffix(block, "_horizontal", texture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(NubBlock.FACING)
                .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, horizontal))
                .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.MODEL, horizontal))
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.MODEL, horizontal))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.MODEL, horizontal))
                .select(Direction.UP, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.MODEL, base))
                .select(Direction.DOWN, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.MODEL, base))
        ));
        gen.delegateItemModel(block, base);
    }

    static void nub(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(model(block))
                .put(TextureSlot.TOP, nubTexture(block).withSuffix("_top"))
                .put(TextureSlot.BOTTOM, nubTexture(block).withSuffix("_top"))
                .put(TextureSlot.SIDE, nubTexture(block).withSuffix("_side"));
        nub(gen, block, texture);
    }

    static void nubUnique(BlockModelGenerators gen, Block block, Block textureBlock) {
        var texture = TextureMapping.defaultTexture(model(block))
                .put(TextureSlot.TOP, nubTexture(textureBlock).withSuffix("_top"))
                .put(TextureSlot.BOTTOM, nubTexture(textureBlock).withSuffix("_bottom"))
                .put(TextureSlot.SIDE, nubTexture(textureBlock).withSuffix("_side"));
        nub(gen, block, texture);
    }

    static void nubUnique(BlockModelGenerators gen, Block block) {
        nubUnique(gen, block, block);
    }


    // Misc
    static void pillar(BlockModelGenerators gen, Block block) {
        gen.createRotatedPillarWithHorizontalVariant(block, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
    }

    static void cageLantern(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(model(block));
        var litTexture = TextureMapping.defaultTexture(model(block).withSuffix("_lit"));

        var base = CAGE_LANTERN.create(block, texture, gen.modelOutput);
        var lit = CAGE_LANTERN.createWithSuffix(block, "_lit", litTexture, gen.modelOutput);

        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(CageLanternBlock.LIT)
                .select(false, Variant.variant().with(VariantProperties.MODEL, base))
                .select(true, Variant.variant().with(VariantProperties.MODEL, lit))
        ).with(PropertyDispatch.property(CageLanternBlock.FACING)
                .select(Direction.DOWN, Variant.variant())
                .select(Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(Direction.UP, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        ));
        gen.delegateItemModel(block, base);
    }

    static void pipe(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(model(block))
                .put(TextureSlot.EDGE, model(block).withSuffix("_edge"))
                .put(INNER, model(block).withSuffix("_inner"));
        var middleTexture = texture.copyAndUpdate(FACE, model(block).withSuffix("_mid"));
        var topTexture = texture.copyAndUpdate(FACE, model(block).withSuffix("_top"));
        var bottomTexture = texture.copyAndUpdate(FACE, model(block).withSuffix("_bottom"));

        var middle = PIPE.create(block, middleTexture, gen.modelOutput);
        var top = PIPE.createWithSuffix(block, "_top", topTexture, gen.modelOutput);
        var bottom = PIPE.createWithSuffix(block, "_bottom", bottomTexture, gen.modelOutput);

        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(PipeBlock.PART)
                .select(PipeBlock.PipeBlockPart.MIDDLE, Variant.variant().with(VariantProperties.MODEL, middle))
                .select(PipeBlock.PipeBlockPart.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                .select(PipeBlock.PipeBlockPart.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
        ).with(PropertyDispatch.property(PipeBlock.AXIS)
                .select(Direction.Axis.Y, Variant.variant())
                .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(Direction.Axis.X, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        ));
        gen.delegateItemModel(block, middle);
    }

    static ResourceLocation model(Block block) {
        return ModelLocationUtils.getModelLocation(block);
    }

    static ResourceLocation nubTexture(Block block) {
        return modelPrefixed(block, "nubs/");
    }

    static ResourceLocation modelPrefixed(Block block, String prefix) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPrefix("block/" + prefix);
    }
}
