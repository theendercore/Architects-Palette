package architectspalette.core.util.model;

import architectspalette.content.blocks.*;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static architectspalette.core.APConstants.modLoc;
import static architectspalette.core.util.model.Models.*;
import static net.minecraft.data.models.BlockModelGenerators.*;

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

    // Wood Set
    static void fenceGate(BlockModelGenerators gen, Block block, Block textureBlock) {
        var texture = TextureMapping.defaultTexture(model(textureBlock));
        var open = ModelTemplates.FENCE_GATE_OPEN.create(block, texture, gen.modelOutput);
        var closed = ModelTemplates.FENCE_GATE_CLOSED.create(block, texture, gen.modelOutput);
        var wallOpen = ModelTemplates.FENCE_GATE_WALL_OPEN.create(block, texture, gen.modelOutput);
        var wallClosed = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(BlockModelGenerators.createFenceGate(block, open, closed, wallOpen, wallClosed, true));
    }

    static void button(BlockModelGenerators gen, Block block, Block textureBlock) {
        var texture = TextureMapping.defaultTexture(model(textureBlock));
        var btn = ModelTemplates.BUTTON.create(block, texture, gen.modelOutput);
        var btnPressed = ModelTemplates.BUTTON_PRESSED.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(BlockModelGenerators.createButton(block, btn, btnPressed));
        var resourceLocation3 = ModelTemplates.BUTTON_INVENTORY.create(block, texture, gen.modelOutput);
        gen.delegateItemModel(block, resourceLocation3);
    }

    static void pressurePlate(BlockModelGenerators gen, Block block, Block textureBlock) {
        var texture = TextureMapping.defaultTexture(model(textureBlock));
        var up = ModelTemplates.PRESSURE_PLATE_UP.create(block, texture, gen.modelOutput);
        var down = ModelTemplates.PRESSURE_PLATE_DOWN.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(block, up, down));
    }

    // Misc
    static void pillar(BlockModelGenerators gen, Block block) {
        gen.createRotatedPillarWithHorizontalVariant(block, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
    }

    static void sidePillar(BlockModelGenerators gen, Block block) {
        customPillar(gen, block, "_top", "");
    }

    static void customPillar(BlockModelGenerators gen, Block block, String topSuffix, String sideSuffix) {
        var texture = TextureMapping.defaultTexture(model(block))
                .put(TextureSlot.END, model(block).withSuffix(topSuffix))
                .put(TextureSlot.SIDE, model(block).withSuffix(sideSuffix));
        var base = ModelTemplates.CUBE_COLUMN.create(block, texture, gen.modelOutput);
        var horizontal = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createRotatedPillarWithHorizontalVariant(block, base, horizontal));
    }

    static void runicGlowstone(BlockModelGenerators gen, Block block) {
        TextureMapping texture = new TextureMapping()
                .put(TextureSlot.PARTICLE,model(block).withSuffix( "_top"))
                .put(TextureSlot.DOWN, model(block).withSuffix( "_bottom"))
                .put(TextureSlot.UP,model(block).withSuffix( "_top"))
                .put(TextureSlot.NORTH,model(block).withSuffix( "_north"))
                .put(TextureSlot.EAST,model(block).withSuffix( "_east"))
                .put(TextureSlot.SOUTH,model(block).withSuffix( "_south"))
                .put(TextureSlot.WEST,model(block).withSuffix( "_west"));

        var model = ModelTemplates.CUBE.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(gen.createColumnWithFacing()));
    }

    static void rotatableColumn(BlockModelGenerators gen, Block block) {
        var model = TexturedModel.COLUMN.create(block, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(gen.createColumnWithFacing()));
    }

    static void staticPillar(BlockModelGenerators gen, Block block) {
        gen.blockStateOutput.accept(createSimpleBlock(block, TexturedModel.COLUMN.create(block, gen.modelOutput)));
    }

    static void staticSidePillar(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(model(block))
                .put(TextureSlot.END, model(block).withSuffix("_top"))
                .put(TextureSlot.SIDE, model(block));
        var base = ModelTemplates.CUBE_COLUMN.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, base));
    }

    static void staticPillarNamed(BlockModelGenerators gen, Block block, String top, String side) {
        var texture = TextureMapping.defaultTexture(model(block))
                .put(TextureSlot.END, modLoc("block/" + top))
                .put(TextureSlot.SIDE, modLoc("block/" + side));
        var model = ModelTemplates.CUBE_COLUMN.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, model));
    }
  /* static void aliasedTrivialBlock(BlockModelGenerators gen, Block block, String alias) {
        var texture = TextureMapping.defaultTexture(modLoc("block/" + alias)).put(TextureSlot.ALL, modLoc("block/" + alias));
        var model = ModelTemplates.CUBE_ALL.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, model));
    }*/

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

    static void heavyBrick(BlockModelGenerators gen, Block block) {
        var halfTexture = TextureMapping.defaultTexture(model(block))
                .put(HALF, model(block).withSuffix("_half"));
        var rlTexture = halfTexture.copyAndUpdate(RIGHT, model(block).withSuffix("_right"))
                .put(LEFT, model(block).withSuffix("_left"));
        var fullTexture = rlTexture.copyAndUpdate(TextureSlot.BOTTOM, model(block).withSuffix("_bottom"))
                .put(TextureSlot.TOP, model(block).withSuffix("_top"));
        var half = HEAVY_BRICKS_HALF.create(block, halfTexture, gen.modelOutput);
        var top = HEAVY_BRICKS_TOP.create(block, halfTexture.copyAndUpdate(TextureSlot.TOP, model(block).withSuffix("_top")), gen.modelOutput);
        var bottom = HEAVY_BRICKS_BOTTOM.create(block, halfTexture.copyAndUpdate(TextureSlot.BOTTOM, model(block).withSuffix("_bottom")), gen.modelOutput);
        var north = HEAVY_BRICKS_NORTH.create(block, fullTexture, gen.modelOutput);
        var east = HEAVY_BRICKS_EAST.create(block, rlTexture, gen.modelOutput);
        var south = HEAVY_BRICKS_SOUTH.create(block, fullTexture, gen.modelOutput);
        var west = HEAVY_BRICKS_WEST.create(block, rlTexture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.properties(BigBrickBlock.FACING, BigBrickBlock.PAIRED)
                        .generate((dir, paired) -> (paired) ? switch (dir) {
                            case DOWN -> Variant.variant().with(VariantProperties.MODEL, top);
                            case UP -> Variant.variant().with(VariantProperties.MODEL, bottom);

                            case NORTH -> Variant.variant().with(VariantProperties.MODEL, north);
                            case SOUTH -> Variant.variant().with(VariantProperties.MODEL, south);
                            case WEST -> Variant.variant().with(VariantProperties.MODEL, west);
                            case EAST -> Variant.variant().with(VariantProperties.MODEL, east);
                        } : Variant.variant().with(VariantProperties.MODEL, half))

                ));
        gen.delegateItemModel(block, half);
    }

    static void sunstone(BlockModelGenerators gen, Block block) {
        var base = ModelTemplates.CUBE_ALL.create(block, allTexture(block, ""), gen.modelOutput);
        var dim = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_dim",allTexture(block, "_dim"), gen.modelOutput);
        var bright = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_bright",allTexture(block, "_bright"), gen.modelOutput);

        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(SunstoneBlock.LIGHT)
                .select(0, Variant.variant().with(VariantProperties.MODEL, base))
                .select(1, Variant.variant().with(VariantProperties.MODEL, dim))
                .select(2, Variant.variant().with(VariantProperties.MODEL, bright))
        ));
        gen.delegateItemModel(block, bright);
    }

    static TextureMapping allTexture(Block block, String suffix) {
        return TextureMapping.defaultTexture(model(block)).put(TextureSlot.ALL, model(block).withSuffix(suffix));
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
