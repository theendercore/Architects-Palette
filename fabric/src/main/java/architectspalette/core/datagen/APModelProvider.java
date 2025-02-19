package architectspalette.core.datagen;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Blocks;

import java.util.stream.Stream;

import static architectspalette.core.util.model.NodeAndSetGenerator.*;
import static architectspalette.core.util.model.ModelHelpers.*;

public class APModelProvider extends FabricModelProvider {
    public APModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        BlockNode.forAllBaseNodes((n) -> makeNodeModels(gen, n));
        StoneBlockSet.forAllSets((s) -> makeBlockSetModels(gen, s));
        excludedModelGen(gen);

        // Make createAbyssaline function
        abyssalineStaticPillar(gen, APBlocks.ABYSSALINE.get());
        abyssalineCube(gen, APBlocks.CHISELED_ABYSSALINE_BRICKS.get());
        abyssalinePillar(gen, APBlocks.ABYSSALINE_PILLAR.get());
        abyssalinePillar(gen, APBlocks.ABYSSALINE_LAMP_BLOCK.get());
        abyssalineStaticPillarTB(gen, APBlocks.ABYSSALINE_PLATING.get());

        abyssalineStaticPillar(gen, APBlocks.HADALINE.get());
        abyssalineCube(gen, APBlocks.CHISELED_HADALINE_BRICKS.get());
        abyssalinePillar(gen, APBlocks.HADALINE_PILLAR.get());
        abyssalinePillar(gen, APBlocks.HADALINE_LAMP_BLOCK.get());
        abyssalineStaticPillarTB(gen, APBlocks.HADALINE_PLATING.get());

        gen.woodProvider(APBlocks.SALMON_LOG.get()).logWithHorizontal(APBlocks.SALMON_LOG.get()).wood(APBlocks.SALMON_SCALES.get());
        gen.woodProvider(APBlocks.COD_LOG.get()).logWithHorizontal(APBlocks.COD_LOG.get()).wood(APBlocks.COD_SCALES.get());

        pipe(gen, APBlocks.PIPE.get());
        sidePillar(gen, APBlocks.SPOOL.get());
        pillar(gen, APBlocks.CHARCOAL_BLOCK.get());

        sidePillar(gen, APBlocks.OLIVESTONE_PILLAR.get());
        gen.createTrivialCube(APBlocks.CRACKED_OLIVESTONE_BRICKS.get());
        gen.createTrivialCube(APBlocks.CRACKED_OLIVESTONE_TILES.get());
        gen.createTrivialCube(APBlocks.CHISELED_OLIVESTONE.get());
        tile(gen, APBlocks.ILLUMINATED_OLIVESTONE.get());

        gen.createTrivialCube(APBlocks.CRACKED_ALGAL_BRICKS.get());
        gen.createTrivialCube(APBlocks.CHISELED_ALGAL_BRICKS.get());
        gen.createTrivialCube(APBlocks.ALGAL_LAMP.get());

        gen.createTrivialCube(APBlocks.FLINT_BLOCK.get());
        rotatableColumn(gen, APBlocks.FLINT_PILLAR.get());

        gen.createTrivialCube(APBlocks.CHISELED_PACKED_ICE.get());
        pillar(gen, APBlocks.PACKED_ICE_PILLAR.get());

        staticPillar(gen, APBlocks.CHISELED_SUNMETAL_BLOCK.get());
        pillar(gen, APBlocks.SUNMETAL_PILLAR.get());
        sunmetalBars(gen, APBlocks.SUNMETAL_BARS.get());

        pillar(gen, APBlocks.OSSEOUS_PILLAR.get());
        staticPillarNamed(gen, APBlocks.OSSEOUS_SKULL.get(), "osseous_skull_top", "osseous_skull");
        staticPillarNamed(gen, APBlocks.LIT_OSSEOUS_SKULL.get(), "osseous_skull_top", "osseous_skull_lit");

        pillar(gen, APBlocks.WITHERED_BONE_BLOCK.get());
        pillar(gen, APBlocks.WITHERED_OSSEOUS_PILLAR.get());
        staticPillarNamed(gen, APBlocks.WITHERED_OSSEOUS_SKULL.get(), "withered_osseous_skull_top", "withered_osseous_skull");
        staticPillarNamed(gen, APBlocks.LIT_WITHERED_OSSEOUS_SKULL.get(), "withered_osseous_skull_top", "withered_osseous_skull_lit");
        gen.createTrivialCube(APBlocks.WITHER_LAMP.get());

        pillar(gen, APBlocks.ENTWINE_PILLAR.get());
        gen.createTrivialCube(APBlocks.CHISELED_ENTWINE.get());
        entwineBars(gen, APBlocks.ENTWINE_BARS.get());

        // Heavy Bricks
        heavyBrick(gen, APBlocks.HEAVY_STONE_BRICKS.get());
        heavyBrick(gen, APBlocks.HEAVY_MOSSY_STONE_BRICKS.get());
        heavyBrick(gen, APBlocks.HEAVY_CRACKED_STONE_BRICKS.get());

        runicGlowstone(gen, APBlocks.RUNIC_GLOWSTONE.get());
        gen.createTrivialCube(APBlocks.SCUTE_BLOCK.get());
        gen.createTrivialCube(APBlocks.ROTTEN_FLESH_BLOCK.get());

        pillar(gen, APBlocks.GILDED_SANDSTONE_PILLAR.get());
        staticSidePillar(gen, APBlocks.CHISELED_GILDED_SANDSTONE.get());

        staticSidePillar(gen, APBlocks.WEEPING_BLACKSTONE.get());
        staticSidePillar(gen, APBlocks.TWISTING_BLACKSTONE.get());
        gen.createTrivialCube(APBlocks.WEEPING_BLACKSTONE_BRICKS.get());
        gen.createTrivialCube(APBlocks.TWISTING_BLACKSTONE_BRICKS.get());

        gen.createTrivialCube(APBlocks.CHORAL_END_STONE_BRICKS.get());
        gen.createTrivialCube(APBlocks.CRACKED_END_STONE_BRICKS.get());
        gen.createTrivialCube(APBlocks.CHISELED_END_STONE_BRICKS.get());

        // Twisted Wood
        gen.woodProvider(APBlocks.STRIPPED_TWISTED_LOG.get()).logWithHorizontal(APBlocks.STRIPPED_TWISTED_LOG.get()).wood(APBlocks.STRIPPED_TWISTED_WOOD.get());
        gen.woodProvider(APBlocks.TWISTED_LOG.get()).logWithHorizontal(APBlocks.TWISTED_LOG.get()).wood(APBlocks.TWISTED_WOOD.get());
        gen.createTrivialBlock(APBlocks.TWISTED_LEAVES.get(), TexturedModel.LEAVES);
        fence(gen, APBlocks.TWISTED_FENCE.get(), APBlocks.TWISTED_PLANKS.get());
        fenceGate(gen, APBlocks.TWISTED_FENCE_GATE.get(), APBlocks.TWISTED_PLANKS.get());
        gen.createDoor(APBlocks.TWISTED_DOOR.get());
        gen.createOrientableTrapdoor(APBlocks.TWISTED_TRAPDOOR.get());
        button(gen, APBlocks.TWISTED_BUTTON.get(), APBlocks.TWISTED_PLANKS.get());
        pressurePlate(gen, APBlocks.TWISTED_PRESSURE_PLATE.get(), APBlocks.TWISTED_PLANKS.get());
        gen.createPlant(APBlocks.TWISTED_SAPLING.get(), APBlocks.POTTED_TWISTED_SAPLING.get(), BlockModelGenerators.TintState.NOT_TINTED);

        tile(gen, APBlocks.CRACKED_BASALT_TILES.get());
        staticPillar(gen, APBlocks.CHISELED_BASALT_TILES.get());

        sunstone(gen, APBlocks.SUNSTONE.get());
        sunstone(gen, APBlocks.MOONSTONE.get());
        gen.createTrivialCube(APBlocks.MOLTEN_NETHER_BRICKS.get());
        gen.createTrivialCube(APBlocks.COARSE_SNOW.get());

        heavyBrick(gen, APBlocks.HEAVY_END_STONE_BRICKS.get());
        heavyBrick(gen, APBlocks.HEAVY_CRACKED_END_STONE_BRICKS.get());

        cageLantern(gen, APBlocks.REDSTONE_CAGE_LANTERN.get());
        cageLantern(gen, APBlocks.GLOWSTONE_CAGE_LANTERN.get());
        cageLantern(gen, APBlocks.ALGAL_CAGE_LANTERN.get());

        //totems
        totemWing(gen, APBlocks.ACACIA_TOTEM_WING.get());
        copyTotem(gen, APBlocks.GRINNING_ACACIA_TOTEM.get(), 1);
        copyTotem(gen, APBlocks.PLACID_ACACIA_TOTEM.get(), 2);
        copyTotem(gen, APBlocks.SHOCKED_ACACIA_TOTEM.get(), 3);
        totem(gen, APBlocks.BLANK_ACACIA_TOTEM.get());

        gen.createTrivialCube(APBlocks.ENDER_PEARL_BLOCK.get());

        // Railing
        railing(gen, APBlocks.OAK_RAILING.get(), Blocks.OAK_PLANKS);
        railing(gen, APBlocks.BIRCH_RAILING.get(), Blocks.BIRCH_PLANKS);
        railing(gen, APBlocks.SPRUCE_RAILING.get(), Blocks.SPRUCE_PLANKS);
        railing(gen, APBlocks.JUNGLE_RAILING.get(), Blocks.JUNGLE_PLANKS);
        railing(gen, APBlocks.DARK_OAK_RAILING.get(), Blocks.DARK_OAK_PLANKS);
        railing(gen, APBlocks.ACACIA_RAILING.get(), Blocks.ACACIA_PLANKS);
        railing(gen, APBlocks.MANGROVE_RAILING.get(), Blocks.MANGROVE_PLANKS);
        railing(gen, APBlocks.CHERRY_RAILING.get(), Blocks.CHERRY_PLANKS);
        railingCustom(gen, APBlocks.BAMBOO_RAILING.get(), Blocks.BAMBOO_FENCE);
        railing(gen, APBlocks.CRIMSON_RAILING.get(), Blocks.CRIMSON_PLANKS);
        railing(gen, APBlocks.WARPED_RAILING.get(), Blocks.WARPED_PLANKS);
        railing(gen, APBlocks.TWISTED_RAILING.get(), APBlocks.TWISTED_PLANKS.get());

        sidePillar(gen, APBlocks.DRIPSTONE_PILLAR.get());
        staticSidePillar(gen, APBlocks.CHISELED_DRIPSTONE.get());
        heavyBrick(gen, APBlocks.HEAVY_DRIPSTONE_BRICKS.get());
        gen.createTrivialCube(APBlocks.DRIPSTONE_LAMP.get());

        sidePillar(gen, APBlocks.CALCITE_PILLAR.get());
        gen.createTrivialCube(APBlocks.CHISELED_CALCITE.get());
        heavyBrick(gen, APBlocks.HEAVY_CALCITE_BRICKS.get());
        gen.createTrivialCube(APBlocks.CALCITE_LAMP.get());

        sidePillar(gen, APBlocks.TUFF_PILLAR.get());
        staticSidePillar(gen, APBlocks.CHISELED_TUFF.get());
        heavyBrick(gen, APBlocks.HEAVY_TUFF_BRICKS.get());
        gen.createTrivialCube(APBlocks.TUFF_LAMP.get());

        sidePillar(gen, APBlocks.HELIODOR_ROD.get());
        sidePillar(gen, APBlocks.EKANITE_ROD.get());
        sidePillar(gen, APBlocks.MONAZITE_ROD.get());

        tile(gen, APBlocks.UNOBTANIUM_BLOCK.get());

        sidePillar(gen, APBlocks.NETHER_BRASS_PILLAR.get());
        createSoulFire(gen, APBlocks.NETHER_BRASS_FIRE.get());
        chain(gen, APBlocks.NETHER_BRASS_CHAIN.get());
        gen.createLantern(APBlocks.NETHER_BRASS_LANTERN.get());
        gen.createNormalTorch(APBlocks.NETHER_BRASS_TORCH.get(), APBlocks.NETHER_BRASS_WALL_TORCH.get());

        sidePillar(gen, APBlocks.ESOTERRACK_PILLAR.get());

        sidePillar(gen, APBlocks.ONYX_PILLAR.get());

        gen.createTrivialCube(APBlocks.CHISELED_WARDSTONE.get());
        sidePillar(gen, APBlocks.WARDSTONE_PILLAR.get());
        gen.createTrivialCube(APBlocks.WARDSTONE_LAMP.get());

        generateNubs(gen);

        makeHazardSign(gen, APBlocks.HAZARD_SIGN.get());
    }

    private void generateNubs(BlockModelGenerators gen) {
        nub(gen, APBlocks.STONE_NUB.get());
        nub(gen, APBlocks.SMOOTH_STONE_NUB.get());
        nub(gen, APBlocks.SANDSTONE_NUB.get());
        nub(gen, APBlocks.ANDESITE_NUB.get());
        nub(gen, APBlocks.GRANITE_NUB.get());
        nub(gen, APBlocks.DIORITE_NUB.get());
        nub(gen, APBlocks.BLACKSTONE_NUB.get());
        nub(gen, APBlocks.DEEPSLATE_NUB.get());
        nub(gen, APBlocks.BONE_NUB.get());
        nubUnique(gen, APBlocks.NUB_OF_ENDER.get());
        nub(gen, APBlocks.IRON_NUB.get());
        nubUnique(gen, APBlocks.GOLD_NUB.get());
        nub(gen, APBlocks.DIAMOND_NUB.get());
        nub(gen, APBlocks.EMERALD_NUB.get());
        nub(gen, APBlocks.NETHERITE_NUB.get());

        nubUnique(gen, APBlocks.COPPER_NUB.get());
        nubUnique(gen, APBlocks.WAXED_COPPER_NUB.get(), APBlocks.COPPER_NUB.get());
        nubUnique(gen, APBlocks.EXPOSED_COPPER_NUB.get());
        nubUnique(gen, APBlocks.WAXED_EXPOSED_COPPER_NUB.get(), APBlocks.EXPOSED_COPPER_NUB.get());
        nubUnique(gen, APBlocks.WEATHERED_COPPER_NUB.get());
        nubUnique(gen, APBlocks.WAXED_WEATHERED_COPPER_NUB.get(), APBlocks.WEATHERED_COPPER_NUB.get());
        nubUnique(gen, APBlocks.OXIDIZED_COPPER_NUB.get());
        nubUnique(gen, APBlocks.WAXED_OXIDIZED_COPPER_NUB.get(), APBlocks.OXIDIZED_COPPER_NUB.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        Stream.of(
                APItems.ALGAL_BLEND,
                APItems.ALGAL_BRICK,
                APItems.WITHERED_BONE,
                APItems.ENTWINE_ROD,
                APItems.SUNMETAL_BLEND,
                APItems.SUNMETAL_BRICK,
                APItems.UNOBTANIUM,
                APItems.BRASS_BLEND,
                APItems.NETHER_BRASS,
                APItems.NETHER_BRASS_NUGGET,
                APItems.WARDSTONE_BLEND,
                APItems.WARDSTONE_BRICK,
                APItems.ORACLE_JELLY,
                APItems.CEREBRAL_PLATE
        ).forEach((item) -> gen.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
    }
}
