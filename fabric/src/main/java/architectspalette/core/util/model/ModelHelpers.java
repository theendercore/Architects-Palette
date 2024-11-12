package architectspalette.core.util.model;

import architectspalette.content.blocks.*;
import architectspalette.content.blocks.abyssaline.AbyssalineBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;

import java.util.List;
import java.util.Optional;

import static architectspalette.core.APConstants.mcLoc;
import static architectspalette.core.APConstants.modLoc;
import static architectspalette.core.util.model.Models.*;
import static net.minecraft.data.models.BlockModelGenerators.*;

public interface ModelHelpers {
    Condition centerTall = Condition.or(
            condition(WallBlock.NORTH_WALL, WallSide.TALL),
            condition(WallBlock.EAST_WALL, WallSide.TALL),
            condition(WallBlock.SOUTH_WALL, WallSide.TALL),
            condition(WallBlock.WEST_WALL, WallSide.TALL)
    );

    Condition centerShort = Condition.or(
            condition(WallBlock.UP, false),
            Condition.condition().term(WallBlock.NORTH_WALL, WallSide.LOW, WallSide.NONE),
            Condition.condition().term(WallBlock.EAST_WALL, WallSide.LOW, WallSide.NONE),
            Condition.condition().term(WallBlock.SOUTH_WALL, WallSide.LOW, WallSide.NONE),
            Condition.condition().term(WallBlock.WEST_WALL, WallSide.LOW, WallSide.NONE)
    );

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
    static void slab(BlockModelGenerators gen, Block block, TextureMapping texture, ResourceLocation full) {
        var id = ModelTemplates.SLAB_BOTTOM.create(block, texture, gen.modelOutput);
        var id2 = ModelTemplates.SLAB_TOP.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createSlab(block, id, id2, full));
        gen.delegateItemModel(block, id);
    }

    static void slab(BlockModelGenerators gen, Block block, Block ends, Block side, Block full) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(ends))
                .put(TextureSlot.SIDE, model(side))
                .put(TextureSlot.TOP, model(ends));
        slab(gen, block, texture, model(full));
    }

    static void slab(BlockModelGenerators gen, Block block, Block texture) {
        slab(gen, block, texture, texture, texture);
    }

    static void specialSlab(BlockModelGenerators gen, Block block, Block ends) {
        TextureMapping texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.BOTTOM, model(ends))
                .put(TextureSlot.SIDE, model(block))
                .put(TextureSlot.TOP, model(ends));
        var full = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(block, "_full", texture, gen.modelOutput);
        slab(gen, block, texture, full);
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
                .select(VerticalSlabBlock.VerticalSlabType.DOUBLE, modelVariant(full))
                .select(VerticalSlabBlock.VerticalSlabType.NORTH, modelVariant(half))
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

    // Nub
    static void nub(BlockModelGenerators gen, Block block, TextureMapping texture) {
        var base = NUB.create(block, texture, gen.modelOutput);
        var horizontal = NUB_HORIZONTAL.createWithSuffix(block, "_horizontal", texture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(NubBlock.FACING)
                .select(Direction.NORTH, modelVariant(horizontal))
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

    // Fancy Wall
    static void fancyWall(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.WALL, model(block));
        var centerShort = FW_CENTER_SHORT.create(block, texture, gen.modelOutput);
        var centerTall = FW_CENTER_TALL.create(block, texture, gen.modelOutput);
        var northShort = FW_NORTH_SHORT.create(block, texture, gen.modelOutput);
        var northTall = FW_NORTH_TALL.create(block, texture, gen.modelOutput);
        var eastShort = FW_EAST_SHORT.create(block, texture, gen.modelOutput);
        var eastTall = FW_EAST_TALL.create(block, texture, gen.modelOutput);
        var southShort = FW_SOUTH_SHORT.create(block, texture, gen.modelOutput);
        var southTall = FW_SOUTH_TALL.create(block, texture, gen.modelOutput);
        var westShort = FW_WEST_SHORT.create(block, texture, gen.modelOutput);
        var westTall = FW_WEST_TALL.create(block, texture, gen.modelOutput);
        var post = FW_POST.create(block, texture, gen.modelOutput);
        var inventory = FW_INVENTORY.create(block, texture, gen.modelOutput);

        gen.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(condition(WallBlock.UP, true), modelVariant(post))
                .with(ModelHelpers.centerTall, modelVariant(centerTall))
                .with(ModelHelpers.centerShort, modelVariant(centerShort))
                .with(condition(WallBlock.NORTH_WALL, WallSide.LOW), modelVariant(northShort))
                .with(condition(WallBlock.EAST_WALL, WallSide.LOW), modelVariant(eastShort))
                .with(condition(WallBlock.SOUTH_WALL, WallSide.LOW), modelVariant(southShort))
                .with(condition(WallBlock.WEST_WALL, WallSide.LOW), modelVariant(westShort))
                .with(condition(WallBlock.NORTH_WALL, WallSide.TALL), modelVariant(northTall))
                .with(condition(WallBlock.EAST_WALL, WallSide.TALL), modelVariant(eastTall))
                .with(condition(WallBlock.SOUTH_WALL, WallSide.TALL), modelVariant(southTall))
                .with(condition(WallBlock.WEST_WALL, WallSide.TALL), modelVariant(westTall))
        );
        gen.delegateItemModel(block, inventory);
    }

    // Abyssaline
    static void abyssalineStaticPillar(BlockModelGenerators gen, Block block) {
        var texture = new TextureMapping()
                .put(TextureSlot.END, abyssaline(block, "_top"))
                .put(TextureSlot.SIDE, abyssaline(block, "_side"));
        var chargedTexture = new TextureMapping()
                .put(TextureSlot.END, abyssaline(block, "_top_charged"))
                .put(TextureSlot.SIDE, abyssaline(block, "_side_charged"));
        var model = ModelTemplates.CUBE_COLUMN.create(block, texture, gen.modelOutput);
        var chargedModel = ModelTemplates.CUBE_COLUMN.createWithSuffix(block, "_charged", chargedTexture, gen.modelOutput);
        gen.blockStateOutput.accept(chargedMap(block, model, chargedModel));
        gen.delegateItemModel(block, model);
    }

    static void abyssalineStaticPillarTB(BlockModelGenerators gen, Block block) {
        var texture = new TextureMapping()
                .put(TextureSlot.TOP, abyssaline(block, "_top"))
                .put(TextureSlot.SIDE, abyssaline(block, "_side"))
                .put(TextureSlot.BOTTOM, abyssaline(block, "_bottom"));
        var chargedTexture = new TextureMapping()
                .put(TextureSlot.TOP, abyssaline(block, "_top_charged"))
                .put(TextureSlot.SIDE, abyssaline(block, "_side_charged"))
                .put(TextureSlot.BOTTOM, abyssaline(block, "_bottom_charged"));
        var model = ModelTemplates.CUBE_BOTTOM_TOP.create(block, texture, gen.modelOutput);
        var chargedModel = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(block, "_charged", chargedTexture, gen.modelOutput);
        gen.blockStateOutput.accept(chargedMap(block, model, chargedModel));
        gen.delegateItemModel(block, model);
    }

    static void abyssalineCube(BlockModelGenerators gen, Block block) {
        var texture = new TextureMapping().put(TextureSlot.ALL, abyssaline(block, ""));
        var chargedTexture = new TextureMapping().put(TextureSlot.ALL, abyssaline(block, "_charged"));
        var model = ModelTemplates.CUBE_ALL.create(block, texture, gen.modelOutput);
        var chargedModel = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_charged", chargedTexture, gen.modelOutput);
        gen.blockStateOutput.accept(chargedMap(block, model, chargedModel));
        gen.delegateItemModel(block, model);
    }

    static void abyssalinePillar(BlockModelGenerators gen, Block block) {
        var texture = new TextureMapping()
                .put(TextureSlot.END, abyssaline(block, "_top"))
                .put(TextureSlot.SIDE, abyssaline(block, "_side"));
        var chargedTexture = new TextureMapping()
                .put(TextureSlot.END, abyssaline(block, "_top_charged"))
                .put(TextureSlot.SIDE, abyssaline(block, "_side_charged"));
        var model = ModelTemplates.CUBE_COLUMN.create(block, texture, gen.modelOutput);
        var chargedModel = ModelTemplates.CUBE_COLUMN.createWithSuffix(block, "_charged", chargedTexture, gen.modelOutput);
        gen.blockStateOutput.accept(chargedMap(block, model, chargedModel).with(PropertyDispatch.property(RotatedPillarBlock.AXIS)
                .select(Direction.Axis.Y, Variant.variant())
                .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(Direction.Axis.X, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
        ));
        gen.delegateItemModel(block, model);
    }

    static void abyssalineSlab(BlockModelGenerators gen, Block block, Block fullBlock) {
        var texture = new TextureMapping()
                .put(TextureSlot.BOTTOM, abyssaline(fullBlock, ""))
                .put(TextureSlot.TOP, abyssaline(fullBlock, ""))
                .put(TextureSlot.SIDE, abyssaline(fullBlock, ""));
        var chargedTexture = new TextureMapping()
                .put(TextureSlot.BOTTOM, abyssaline(fullBlock, "_charged"))
                .put(TextureSlot.TOP, abyssaline(fullBlock, "_charged"))
                .put(TextureSlot.SIDE, abyssaline(fullBlock, "_charged"));
        var bottom = ModelTemplates.SLAB_BOTTOM.create(block, texture, gen.modelOutput);
        var top = ModelTemplates.SLAB_TOP.create(block, texture, gen.modelOutput);
        var full = model(fullBlock);
        var bottomCharged = ModelTemplates.SLAB_BOTTOM.createWithSuffix(block, "_charged", chargedTexture, gen.modelOutput);
        var topCharged = ModelTemplates.SLAB_TOP.createWithSuffix(block, "_charged", chargedTexture, gen.modelOutput);
        var fullCharged = model(fullBlock).withSuffix("_charged");
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.SLAB_TYPE, AbyssalineBlock.CHARGED)
                .generate((type, charge) -> modelVariant(switch (type) {
                    case BOTTOM -> (charge) ? bottomCharged : bottom;
                    case TOP -> (charge) ? topCharged : top;
                    case DOUBLE -> (charge) ? fullCharged : full;
                }))));
        gen.delegateItemModel(block, bottom);
    }

    static void abyssalineVerticalSlab(BlockModelGenerators gen, Block block, Block full) {
        var texture = new TextureMapping()
                .put(TextureSlot.BOTTOM, abyssaline(full, ""))
                .put(TextureSlot.TOP, abyssaline(full, ""))
                .put(TextureSlot.SIDE, abyssaline(full, ""));
        var chargedTexture = new TextureMapping()
                .put(TextureSlot.BOTTOM, abyssaline(full, "_charged"))
                .put(TextureSlot.TOP, abyssaline(full, "_charged"))
                .put(TextureSlot.SIDE, abyssaline(full, "_charged"));
        var slab = VERTICAL_SLAB.create(block, texture, gen.modelOutput);
        var chargedSlab = VERTICAL_SLAB.createWithSuffix(block, "_charged", chargedTexture, gen.modelOutput);
        var fullModel = model(full);
        var fullCharged = model(full).withSuffix("_charged");
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(VerticalSlabBlock.TYPE, AbyssalineBlock.CHARGED)
                .generate((type, charge) -> {
                    var variant = Variant.variant();
                    var slabModel = charge ? chargedSlab : slab;
                    switch (type) {
                        case DOUBLE -> variant.with(VariantProperties.MODEL, (charge) ? fullCharged : fullModel);
                        case NORTH -> variant.with(VariantProperties.MODEL, slabModel);
                        case EAST ->
                                variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.MODEL, slabModel);
                        case SOUTH ->
                                variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.MODEL, slabModel);
                        case WEST ->
                                variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.MODEL, slabModel);
                    }
                    return variant;
                })
        ));
        gen.delegateItemModel(block, slab);
    }

    static MultiVariantGenerator chargedMap(Block block, ResourceLocation model, ResourceLocation chargedModel) {
        return booleanMap(block, model, chargedModel, AbyssalineBlock.CHARGED);
    }

    // Misc
    static void createTrivialBlock(BlockModelGenerators gen, Block block, ModelTemplate model) {
        gen.blockStateOutput.accept(createSimpleBlock(block, model.create(block, TextureMapping.defaultTexture(block), gen.modelOutput)));
    }

    static void tile(BlockModelGenerators gen, Block block) {
        var model = TILE.create(block, TextureMapping.defaultTexture(block).put(TextureSlot.ALL, model(block)), gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, model));
    }

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

    static void rotatableColumn(BlockModelGenerators gen, Block block) {
        var model = TexturedModel.COLUMN.create(block, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, modelVariant(model)).with(gen.createColumnWithFacing()));
    }

    static void staticPillar(BlockModelGenerators gen, Block block) {
        gen.blockStateOutput.accept(createSimpleBlock(block, TexturedModel.COLUMN.create(block, gen.modelOutput)));
    }
    static void staticCubeTB(BlockModelGenerators gen, Block block) {
        var texture = new TextureMapping()
                .put(TextureSlot.TOP, abyssaline(block, "_top"))
                .put(TextureSlot.SIDE, abyssaline(block, "_side"))
                .put(TextureSlot.BOTTOM, abyssaline(block, "_bottom"));
        var model = ModelTemplates.CUBE_BOTTOM_TOP.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, model));
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

    static void cageLantern(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(model(block));
        var litTexture = TextureMapping.defaultTexture(model(block).withSuffix("_lit"));

        var base = CAGE_LANTERN.create(block, texture, gen.modelOutput);
        var lit = CAGE_LANTERN.createWithSuffix(block, "_lit", litTexture, gen.modelOutput);

        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(CageLanternBlock.LIT)
                .select(false, modelVariant(base))
                .select(true, modelVariant(lit))
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
                .select(PipeBlock.PipeBlockPart.MIDDLE, modelVariant(middle))
                .select(PipeBlock.PipeBlockPart.TOP, modelVariant(top))
                .select(PipeBlock.PipeBlockPart.BOTTOM, modelVariant(bottom))
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
                            case DOWN -> modelVariant(top);
                            case UP -> modelVariant(bottom);

                            case NORTH -> modelVariant(north);
                            case SOUTH -> modelVariant(south);
                            case WEST -> modelVariant(west);
                            case EAST -> modelVariant(east);
                        } : modelVariant(half))

                ));
        gen.delegateItemModel(block, half);
    }

    static void sunstone(BlockModelGenerators gen, Block block) {
        var base = ModelTemplates.CUBE_ALL.create(block, allTexture(block, ""), gen.modelOutput);
        var dim = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_dim", allTexture(block, "_dim"), gen.modelOutput);
        var bright = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_bright", allTexture(block, "_bright"), gen.modelOutput);

        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(SunstoneBlock.LIGHT)
                .select(0, modelVariant(base))
                .select(1, modelVariant(dim))
                .select(2, modelVariant(bright))
        ));
        gen.delegateItemModel(block, bright);
    }

    static void runicGlowstone(BlockModelGenerators gen, Block block) {
        TextureMapping texture = new TextureMapping()
                .put(TextureSlot.PARTICLE, model(block).withSuffix("_top"))
                .put(TextureSlot.DOWN, model(block).withSuffix("_bottom"))
                .put(TextureSlot.UP, model(block).withSuffix("_top"))
                .put(TextureSlot.NORTH, model(block).withSuffix("_north"))
                .put(TextureSlot.EAST, model(block).withSuffix("_east"))
                .put(TextureSlot.SOUTH, model(block).withSuffix("_south"))
                .put(TextureSlot.WEST, model(block).withSuffix("_west"));

        var model = ModelTemplates.CUBE.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, modelVariant(model)).with(gen.createColumnWithFacing()));
    }

    static void totemWing(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(block);
        var model = FLAT_PANE.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, modelVariant(model)).with(createHorizontalFacingDispatch()));
        gen.createSimpleFlatItemModel(block);
    }

    static void totem(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.TOP, modLoc("block/acacia_totem_top"))
                .put(TextureSlot.BOTTOM, modLoc("block/acacia_totem_bottom"))
                .put(TextureSlot.SIDE, modLoc("block/acacia_totem_side"))
                .put(TextureSlot.FRONT, modLoc("block/acacia_totem_side"))
                .put(TextureSlot.PARTICLE, modLoc("block/acacia_totem_side"));

        var model = ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, modelVariant(model)).with(createHorizontalFacingDispatch()));
        gen.delegateItemModel(block, model);
    }

    static void copyTotem(BlockModelGenerators gen, Block block, int number) {
        var texture = TextureMapping.defaultTexture(block).put(TextureSlot.FRONT, modLoc("block/acacia_totem_face" + number));
        var model = createModel("blank_acacia_totem", Optional.empty(), TextureSlot.FRONT).create(block, texture, gen.modelOutput);
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, modelVariant(model)).with(createHorizontalFacingDispatch()));
        gen.delegateItemModel(block, model);
    }

    static void createSoulFire(BlockModelGenerators gen, Block block) {
        List<ResourceLocation> list = gen.createFloorFireModels(block);
        List<ResourceLocation> list2 = gen.createSideFireModels(block);
        gen.blockStateOutput.accept(MultiPartGenerator.multiPart(block).with(wrapModels(list, (variant) -> variant)).with(wrapModels(list2, (variant) -> variant)).with(wrapModels(list2, (variant) -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))).with(wrapModels(list2, (variant) -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))).with(wrapModels(list2, (variant) -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
    }

    static void makeHazardSign(BlockModelGenerators gen, Block block) {
        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, modelVariant(model(block))).with(createHorizontalFacingDispatch()));
        gen.createSimpleFlatItemModel(block);
    }

    static void chain(BlockModelGenerators gen, Block block) {
        var texture = TextureMapping.defaultTexture(block)
                .put(TextureSlot.PARTICLE, model(block))
                .put(TextureSlot.ALL, model(block));
        var model = new ModelTemplate(Optional.of(mcLoc("block/chain")), Optional.empty(), TextureSlot.PARTICLE, TextureSlot.ALL).create(block, texture, gen.modelOutput);
        gen.createAxisAlignedPillarBlockCustomModel(block, model);
        gen.createSimpleFlatItemModel(block.asItem());
    }

    static MultiVariantGenerator booleanMap(Block block, ResourceLocation falseModel, ResourceLocation trueModel, BooleanProperty propert) {
        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(propert)
                .select(false, modelVariant(falseModel))
                .select(true, modelVariant(trueModel))
        );
    }

    // Util
    static TextureMapping allTexture(Block block, String suffix) {
        return TextureMapping.defaultTexture(model(block)).put(TextureSlot.ALL, model(block).withSuffix(suffix));
    }

    static ResourceLocation model(Block block) {
        return ModelLocationUtils.getModelLocation(block);
    }

    static ResourceLocation nubTexture(Block block) {
        return modelPrefixed(block, "nubs/");
    }

    static ResourceLocation abyssaline(Block block, String suffix) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPrefix("block/abyssaline/").withSuffix(suffix);
    }

    static ResourceLocation modelPrefixed(Block block, String prefix) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPrefix("block/" + prefix);
    }

    static <T extends Comparable<T>> Condition.TerminalCondition condition(Property<T> property, T comparable) {
        return Condition.condition().term(property, comparable);
    }

    static Variant modelVariant(ResourceLocation model) {
        return Variant.variant().with(VariantProperties.MODEL, model);
    }
}
