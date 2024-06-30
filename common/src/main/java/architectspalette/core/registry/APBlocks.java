package architectspalette.core.registry;

import architectspalette.content.blocks.*;
import architectspalette.content.blocks.PipeBlock;
import architectspalette.content.blocks.abyssaline.*;
import architectspalette.content.blocks.entrails.DrippyBlock;
import architectspalette.content.blocks.flint.FlintBlock;
import architectspalette.content.blocks.flint.FlintPillarBlock;
import architectspalette.content.blocks.util.DirectionalFacingBlock;
import architectspalette.content.worldgen.features.APTreeGrowers;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.RegistryUtils;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.function.Supplier;

import static architectspalette.core.registry.util.RegistryUtils.*;
import static architectspalette.core.registry.util.StoneBlockSet.SetComponent.*;
import static architectspalette.core.registry.util.StoneBlockSet.SetGroup.*;
import static net.minecraft.world.level.block.WeatheringCopper.WeatherState.*;

public class APBlocks {
    public static void init(){}
    public static final ArrayList<BlockNode> boards = new ArrayList<>();


    // Abyssaline
    public static final Supplier<Block> ABYSSALINE = createBlock("abyssaline", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE));
    public static final StoneBlockSet ABYSSALINE_BRICKS = new StoneBlockSet(createBlock("abyssaline_bricks", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE)), SLABS);
    public static final StoneBlockSet ABYSSALINE_TILES = new StoneBlockSet(createBlock("abyssaline_tiles", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE)), SLABS);
    public static final Supplier<Block> CHISELED_ABYSSALINE_BRICKS = createBlock("chiseled_abyssaline_bricks", () -> new ChiseledAbyssalineBlock(APBlockProperties.CHISELED_ABYSSALINE));
    public static final Supplier<Block> ABYSSALINE_PILLAR          = createBlock("abyssaline_pillar",          () -> new AbyssalinePillarBlock(APBlockProperties.ABYSSALINE));
    public static final Supplier<Block> ABYSSALINE_LAMP_BLOCK      = createBlock("abyssaline_lamp",            () -> new AbyssalineLampBlock(APBlockProperties.ABYSSALINE_LAMP.sound(SoundType.GLASS)));
    public static final Supplier<AbyssalineBlock> ABYSSALINE_PLATING = createBlock("abyssaline_plating", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE));

    // Hadaline
    public static final Supplier<Block> HADALINE = createBlock("hadaline", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE));
    public static final StoneBlockSet HADALINE_BRICKS = new StoneBlockSet(createBlock("hadaline_bricks", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE)), SLABS);
    public static final StoneBlockSet HADALINE_TILES = new StoneBlockSet(createBlock("hadaline_tiles", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE)), SLABS);
    public static final Supplier<ChiseledAbyssalineBlock> CHISELED_HADALINE_BRICKS = createBlock("chiseled_hadaline_bricks", () -> new ChiseledAbyssalineBlock(APBlockProperties.CHISELED_ABYSSALINE));
    public static final Supplier<AbyssalinePillarBlock>   HADALINE_PILLAR          = createBlock("hadaline_pillar",          () -> new AbyssalinePillarBlock(APBlockProperties.ABYSSALINE));
    public static final Supplier<AbyssalineLampBlock>     HADALINE_LAMP_BLOCK      = createBlock("hadaline_lamp",            () -> new AbyssalineLampBlock(APBlockProperties.ABYSSALINE_LAMP.sound(SoundType.GLASS)));
    public static final Supplier<AbyssalineBlock> HADALINE_PLATING = createBlock("hadaline_plating", () -> new AbyssalineBlock(APBlockProperties.ABYSSALINE));

    // Villager Trade blocks
     // Funny fish blocks
    public static final Supplier<Block>    SALMON_LOG = createBlock("salmon_log",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MapColor.TERRACOTTA_RED)));
    public static final Supplier<Block>       COD_LOG = createBlock("cod_log",       () -> new RotatedPillarBlock(APBlockProperties.Meat(MapColor.TERRACOTTA_YELLOW)));
    public static final Supplier<Block> SALMON_SCALES = createBlock("salmon_scales", () -> new RotatedPillarBlock(APBlockProperties.Meat(MapColor.TERRACOTTA_RED)));
    public static final Supplier<Block>    COD_SCALES = createBlock("cod_scales",    () -> new RotatedPillarBlock(APBlockProperties.Meat(MapColor.TERRACOTTA_YELLOW)));
     // Entrails
    public static final StoneBlockSet ENTRAILS = new StoneBlockSet(createBlock("entrails", () -> new DrippyBlock(APBlockProperties.Meat(MapColor.TERRACOTTA_PINK))), NO_WALLS).usesAxe();
     // Plating & Piping
    public static final StoneBlockSet PLATING_BLOCK = new StoneBlockSet(createBlock("plating_block", () -> new Block(APBlockProperties.PLATING)), TYPICAL, NUB);
    public static final Supplier<Block> PIPE = createBlock("pipe", () -> new PipeBlock(BlockBehaviour.Properties.ofFullCopy(APBlocks.PLATING_BLOCK.get()).noOcclusion()));
     //Spools
    public static final Supplier<Block> SPOOL = createBlock("spool", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));

    // Charcoal Block
    public static final Supplier<Block> CHARCOAL_BLOCK = createBlockNoItem("charcoal_block", () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.COAL_BLOCK)));

    // Myonite (previously limestone)
    public static final StoneBlockSet MYONITE             = new StoneBlockSet(createBlock("myonite",              () -> new Block(APBlockProperties.MYONITE)));
    public static final StoneBlockSet MYONITE_BRICK       = new StoneBlockSet(createBlock("myonite_bricks",       () -> new Block(APBlockProperties.MYONITE)));
    public static final StoneBlockSet MUSHY_MYONITE_BRICK = new StoneBlockSet(createBlock("mushy_myonite_bricks", () -> new Block(APBlockProperties.MYONITE)));

    // Olivestone
    public static final StoneBlockSet OLIVESTONE_BRICK = new StoneBlockSet(createBlock("olivestone_bricks", () -> new Block(APBlockProperties.OLIVESTONE)));
    public static final StoneBlockSet OLIVESTONE_TILE  = new StoneBlockSet(createBlock("olivestone_tiles",  () -> new Block(APBlockProperties.OLIVESTONE)));

    public static final Supplier<Block> OLIVESTONE_PILLAR         = createBlock("olivestone_pillar", () -> new RotatedPillarBlock(APBlockProperties.OLIVESTONE));
    public static final Supplier<Block> CRACKED_OLIVESTONE_BRICKS = createBlock("cracked_olivestone_bricks", () -> new Block(APBlockProperties.OLIVESTONE));
    public static final Supplier<Block> CRACKED_OLIVESTONE_TILES  = createBlock("cracked_olivestone_tiles",  () -> new Block(APBlockProperties.OLIVESTONE));
    public static final Supplier<Block> CHISELED_OLIVESTONE       = createBlock("chiseled_olivestone", () -> new Block(APBlockProperties.OLIVESTONE));
    public static final Supplier<Block> ILLUMINATED_OLIVESTONE    = createBlock("illuminated_olivestone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(OLIVESTONE_BRICK.get()).lightLevel((state) -> 15)));

    // Algal Brick
    public static final StoneBlockSet ALGAL_BRICK = new StoneBlockSet(createBlock("algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));
    public static final Supplier<Block> CRACKED_ALGAL_BRICKS  = createBlock("cracked_algal_bricks",  () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final Supplier<Block> CHISELED_ALGAL_BRICKS = createBlock("chiseled_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK));
    public static final StoneBlockSet OVERGROWN_ALGAL_BRICK = new StoneBlockSet(createBlock("overgrown_algal_bricks", () -> new Block(APBlockProperties.ALGAL_BRICK)));
    public static final Supplier<Block> ALGAL_LAMP = createBlock("algal_lamp", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SEA_LANTERN)));

    // Ore Bricks
    public static final BlockNode COAL_BRICKS = oreBrickSet("coal");
    public static final BlockNode LAPIS_BRICKS = oreBrickSet("lapis");
    public static final BlockNode REDSTONE_BRICKS = oreBrickSet("redstone");
    public static final BlockNode IRON_BRICKS = oreBrickSet("iron");
    public static final BlockNode GOLD_BRICKS = oreBrickSet("gold");
    public static final BlockNode EMERALD_BRICKS = oreBrickSet("emerald");
    public static final BlockNode DIAMOND_BRICKS = oreBrickSet("diamond");

    private static BlockNode oreBrickSet(String ore) {
        return new BlockNode.Builder()
                .tool(BlockNode.Tool.PICK)
                .base(createBlock(ore + "_ore_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS))))
                .addPart(BlockNode.BlockType.CRACKED)
                .addPart(BlockNode.BlockType.CHISELED)
                .commonVariants()
                .build();
    }

    // Flint Blocks
    public static final Supplier<Block> FLINT_BLOCK  	= createBlock("flint_block",  () -> new FlintBlock(APBlockProperties.FLINT));
    public static final StoneBlockSet FLINT_TILES  			= new StoneBlockSet(createBlock("flint_tiles",  () -> new FlintBlock(APBlockProperties.FLINT)));
    public static final Supplier<Block> FLINT_PILLAR 	= createBlock("flint_pillar", () -> new FlintPillarBlock(APBlockProperties.FLINT));

    // Polished Packed Ice
    public static final StoneBlockSet POLISHED_PACKED_ICE = new StoneBlockSet(createBlock("polished_packed_ice", () -> new Block(APBlockProperties.BUILDING_ICE)));
    public static final Supplier<Block> CHISELED_PACKED_ICE = createBlock("chiseled_packed_ice", () -> new Block(APBlockProperties.BUILDING_ICE));
    public static final Supplier<Block> PACKED_ICE_PILLAR   = createBlock("packed_ice_pillar",   () -> new RotatedPillarBlock(APBlockProperties.BUILDING_ICE));

    // Sunmetal
    public static final StoneBlockSet SUNMETAL = new StoneBlockSet(createBlock("sunmetal_block", () -> new Block(APBlockProperties.SUNMETAL)), NO_WALLS, NUB);
    public static final Supplier<Block> CHISELED_SUNMETAL_BLOCK = createBlock("chiseled_sunmetal_block", () -> new Block(APBlockProperties.SUNMETAL));
    public static final Supplier<Block> SUNMETAL_PILLAR         = createBlock("sunmetal_pillar", () -> new RotatedPillarBlock(APBlockProperties.SUNMETAL));
    public static final Supplier<Block> SUNMETAL_BARS           = createBlock("sunmetal_bars", () -> new IronBarsBlock(APBlockProperties.SUNMETAL.noOcclusion()));

    // Osseous Bricks
    public static final StoneBlockSet OSSEOUS_BRICK = new StoneBlockSet(createBlock("osseous_bricks", () -> new Block(Block.Properties.ofFullCopy(Blocks.BONE_BLOCK))));
    public static final Supplier<Block> OSSEOUS_PILLAR = createBlock("osseous_pillar", () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.BONE_BLOCK)));
    public static final Supplier<Block> OSSEOUS_SKULL = createBlock("osseous_skull", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)));
    public static final Supplier<Block> LIT_OSSEOUS_SKULL = createBlock("lit_osseous_skull", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).lightLevel(e -> 12)));
    // Withered
     // Todo: Replace bone block recipe to one that uses withered bone meal if that gets in
    public static final Supplier<Block> WITHERED_BONE_BLOCK = createBlock("withered_bone_block", () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.BONE_BLOCK)));
    public static final StoneBlockSet      WITHERED_OSSEOUS_BRICK = new StoneBlockSet(createBlock("withered_osseous_bricks", () -> new Block(Block.Properties.ofFullCopy(Blocks.BONE_BLOCK))));
    public static final Supplier<Block> WITHERED_OSSEOUS_PILLAR = createBlock("withered_osseous_pillar", () -> new RotatedPillarBlock(Block.Properties.ofFullCopy(Blocks.BONE_BLOCK)));
    public static final Supplier<Block> WITHERED_OSSEOUS_SKULL = createBlock("withered_osseous_skull", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)));
    public static final Supplier<Block> LIT_WITHERED_OSSEOUS_SKULL = createBlock("lit_withered_osseous_skull", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).lightLevel(e -> 12)));
    // Wither Lamp
    public static final Supplier<Block> WITHER_LAMP = createBlock("wither_lamp", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SEA_LANTERN)));

    // Entwine
    public static final StoneBlockSet ENTWINE = new StoneBlockSet(createBlock("entwine_block", () -> new Block(APBlockProperties.ENTWINE)), NO_WALLS);
    public static final Supplier<Block> ENTWINE_PILLAR = createBlock("entwine_pillar", () -> new RotatedPillarBlock(APBlockProperties.ENTWINE));
    public static final Supplier<Block> CHISELED_ENTWINE = createBlock("chiseled_entwine", () -> new Block(APBlockProperties.ENTWINE));
    public static final Supplier<Block> ENTWINE_BARS = createBlock("entwine_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(ENTWINE.get()).noOcclusion()));

    // Heavy Stone Bricks
    public static final Supplier<Block> HEAVY_STONE_BRICKS = createBlock("heavy_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> HEAVY_MOSSY_STONE_BRICKS = createBlock("heavy_mossy_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSSY_STONE_BRICKS)));
    public static final Supplier<Block> HEAVY_CRACKED_STONE_BRICKS = createBlock("heavy_cracked_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_STONE_BRICKS)));

    // Polished Glowstone
    public static final StoneBlockSet POLISHED_GLOWSTONE = new StoneBlockSet(createBlock("polished_glowstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE))), NO_STAIRS, NUB);
    public static final Supplier<Block> RUNIC_GLOWSTONE = createBlock("runic_glowstone", () -> new DirectionalFacingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)));

    // Scute Block
    public static final Supplier<Block> SCUTE_BLOCK = createBlock("scute_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(5.0F, 6.0F).sound(SoundType.BASALT).instrument(NoteBlockInstrument.BASEDRUM)));
    // Rotten Flesh Block
    public static final Supplier<Block> ROTTEN_FLESH_BLOCK = createBlock("rotten_flesh_block", () -> new Block(APBlockProperties.Meat(MapColor.COLOR_ORANGE)));

    // Gilded Sandstone
    public static final StoneBlockSet GILDED_SANDSTONE = new StoneBlockSet(createBlock("gilded_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE))), NO_WALLS);
    public static final Supplier<Block> GILDED_SANDSTONE_PILLAR = createBlock("gilded_sandstone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE)));
    public static final Supplier<Block> CHISELED_GILDED_SANDSTONE = createBlock("chiseled_gilded_sandstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE)));

    // Mossy Blackstone Variants
    public static final Supplier<Block> WEEPING_BLACKSTONE = createBlock("weeping_blackstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE)));
    public static final Supplier<Block> TWISTING_BLACKSTONE = createBlock("twisting_blackstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE)));
    public static final Supplier<Block> WEEPING_BLACKSTONE_BRICKS = createBlock("weeping_blackstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));
    public static final Supplier<Block> TWISTING_BLACKSTONE_BRICKS = createBlock("twisting_blackstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    // End Stone Variants
    public static final Supplier<Block>   CHORAL_END_STONE_BRICKS = createBlock("choral_end_stone_bricks",   () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS)));
    public static final Supplier<Block>  CRACKED_END_STONE_BRICKS = createBlock("cracked_end_stone_bricks",  () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_END_STONE_BRICKS = createBlock("chiseled_end_stone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS)));

    // Warpstone
    public static final StoneBlockSet WARPSTONE = new StoneBlockSet(createBlock("warpstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))));

    // Twisted Wood
     // Todo: Bookshelf, sign(?), boat(?), custom BlockSetType(?), custom WoodType(?)
    public static final StoneBlockSet TWISTED_PLANKS = new StoneBlockSet(createBlock("twisted_planks", () -> new Block(APBlockProperties.TwistedWood())), NO_WALLS).woodify();

    public static final Supplier<Block>  STRIPPED_TWISTED_LOG = createBlock("stripped_twisted_log", () -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final Supplier<Block> STRIPPED_TWISTED_WOOD = createBlock("stripped_twisted_wood",() -> new RotatedPillarBlock(APBlockProperties.TwistedWood()));
    public static final Supplier<Block>           TWISTED_LOG = createBlock("twisted_log",          () -> new StrippablePilarBlock(APBlockProperties.TwistedWood(), STRIPPED_TWISTED_LOG.get()));
    public static final Supplier<Block>          TWISTED_WOOD = createBlock("twisted_wood",         () -> new StrippablePilarBlock(APBlockProperties.TwistedWood(), STRIPPED_TWISTED_WOOD.get()));
    public static final Supplier<Block>        TWISTED_LEAVES = createBlock("twisted_leaves",       () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final Supplier<Block>         TWISTED_FENCE = createBlock("twisted_fence",        () -> new         FenceBlock(APBlockProperties.TwistedWood()), RegistryUtils.BUILDING_BLOCKS);
    public static final Supplier<Block>    TWISTED_FENCE_GATE = createBlock("twisted_fence_gate",   () -> new     FenceGateBlock(WoodType.OAK, APBlockProperties.TwistedWood()), RegistryUtils.BUILDING_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);
    public static final Supplier<Block>          TWISTED_DOOR = createBlock("twisted_door",         () -> new          DoorBlock(BlockSetType.OAK, APBlockProperties.TwistedWood().noOcclusion()), RegistryUtils.BUILDING_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);
    public static final Supplier<Block>      TWISTED_TRAPDOOR = createBlock("twisted_trapdoor",     () -> new      TrapDoorBlock(BlockSetType.OAK, APBlockProperties.TwistedWood().noOcclusion()), RegistryUtils.BUILDING_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);
    public static final Supplier<Block>        TWISTED_BUTTON = createBlock("twisted_button",
            () -> new    ButtonBlock(BlockSetType.OAK, 30, APBlockProperties.TwistedWood(true)), RegistryUtils.BUILDING_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);
    public static final Supplier<Block> TWISTED_PRESSURE_PLATE = createBlock("twisted_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, APBlockProperties.TwistedWood(true)), RegistryUtils.BUILDING_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);
    public static final Supplier<Block>        TWISTED_SAPLING = createBlock("twisted_sapling", () -> new SaplingBlock(APTreeGrowers.WARPED_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)), RegistryUtils.NATURAL_BLOCKS);
    public static final Supplier<Block> POTTED_TWISTED_SAPLING = createPottedPlant(TWISTED_SAPLING);

    // Basalt Tiles
    public static final StoneBlockSet BASALT_TILES = new StoneBlockSet(createBlock("basalt_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT))));
    public static final Supplier<Block>  CRACKED_BASALT_TILES = createBlock("cracked_basalt_tiles",  () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)));
    public static final Supplier<Block> CHISELED_BASALT_TILES = createBlock("chiseled_basalt_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)));

    // Celestial Stones
    public static final Supplier<Block> SUNSTONE  = createBlock("sunstone",  () -> new SunstoneBlock(APBlockProperties.SUNSTONE, SunstoneBlock::sunstoneLight));
    public static final Supplier<Block> MOONSTONE = createBlock("moonstone", () -> new SunstoneBlock(APBlockProperties.SUNSTONE, SunstoneBlock::moonstoneLight));

    // Odd block variants
    public static final Supplier<Block> MOLTEN_NETHER_BRICKS = createBlock("molten_nether_bricks", () -> new Block(APBlockProperties.MOLTEN_BRICK));
    public static final Supplier<Block> COARSE_SNOW = createBlock("coarse_snow", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW_BLOCK)));

    // Heavy End Stone Bricks
    public static final Supplier<Block> HEAVY_END_STONE_BRICKS = createBlock("heavy_end_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS), BigBrickBlock.BrickType.END_STONE));
    public static final Supplier<Block> HEAVY_CRACKED_END_STONE_BRICKS = createBlock("heavy_cracked_end_stone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS), BigBrickBlock.BrickType.END_STONE));

    // Cage Lanterns
    public static final Supplier<Block> REDSTONE_CAGE_LANTERN  = createBlock("redstone_cage_lantern", () -> new CageLanternBlock(APBlockProperties.CAGE_LANTERN, 3), RegistryUtils.FUNCTIONAL_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);
    public static final Supplier<Block> GLOWSTONE_CAGE_LANTERN = createBlock("glowstone_cage_lantern", () -> new CageLanternBlock(APBlockProperties.CAGE_LANTERN, 3), RegistryUtils.FUNCTIONAL_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);
    public static final Supplier<Block> ALGAL_CAGE_LANTERN     = createBlock("algal_cage_lantern", () -> new CageLanternBlock(APBlockProperties.CAGE_LANTERN, 3), RegistryUtils.FUNCTIONAL_BLOCKS, RegistryUtils.REDSTONE_BLOCKS);

    // Acacia Totems
    public static final Supplier<TotemWingBlock> ACACIA_TOTEM_WING = createBlock("acacia_totem_wing", () -> new TotemWingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS).noOcclusion().noLootTable().sound(SoundType.SCAFFOLDING).noCollission()));
    public static final Supplier<Block> GRINNING_ACACIA_TOTEM = createBlock("grinning_acacia_totem", () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.GRINNING));
    public static final Supplier<Block> PLACID_ACACIA_TOTEM   = createBlock("placid_acacia_totem",   () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.PLACID));
    public static final Supplier<Block> SHOCKED_ACACIA_TOTEM  = createBlock("shocked_acacia_totem",  () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.SHOCKED));
    public static final Supplier<Block> BLANK_ACACIA_TOTEM    = createBlock("blank_acacia_totem",    () -> new TotemBlock(APBlockProperties.ACACIA_TOTEM, ACACIA_TOTEM_WING.get(), TotemBlock.TotemFace.BLANK));

    // Ender Pearl Block
    public static final Supplier<Block> ENDER_PEARL_BLOCK = createBlock("ender_pearl_block", () -> new Block(APBlockProperties.ENDER_PEARL));

    // Boards
    public static final BlockNode OAK_BOARDS      = createBoardNode("oak_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final BlockNode BIRCH_BOARDS    = createBoardNode("birch_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final BlockNode SPRUCE_BOARDS   = createBoardNode("spruce_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final BlockNode JUNGLE_BOARDS   = createBoardNode("jungle_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final BlockNode DARK_OAK_BOARDS = createBoardNode("dark_oak_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final BlockNode ACACIA_BOARDS   = createBoardNode("acacia_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final BlockNode CRIMSON_BOARDS  = createBoardNode("crimson_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final BlockNode WARPED_BOARDS   = createBoardNode("warped_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));
    public static final BlockNode MANGROVE_BOARDS = createBoardNode("mangrove_boards", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final BlockNode TWISTED_BOARDS  = createBoardNode("twisted_boards", () -> new Block(APBlockProperties.TwistedWood()));

    // Railings
    public static final Supplier<Block> OAK_RAILING      = createBlock("oak_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final Supplier<Block> BIRCH_RAILING    = createBlock("birch_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final Supplier<Block> SPRUCE_RAILING   = createBlock("spruce_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final Supplier<Block> JUNGLE_RAILING   = createBlock("jungle_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final Supplier<Block> DARK_OAK_RAILING = createBlock("dark_oak_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final Supplier<Block> ACACIA_RAILING   = createBlock("acacia_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final Supplier<Block> CRIMSON_RAILING  = createBlock("crimson_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final Supplier<Block> WARPED_RAILING   = createBlock("warped_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));
    public static final Supplier<Block> MANGROVE_RAILING = createBlock("mangrove_railing", () -> new RailingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final Supplier<Block> TWISTED_RAILING  = createBlock("twisted_railing", () -> new RailingBlock(APBlockProperties.TwistedWood()));

    // New stone block sets
    // Dripstone
    public static final StoneBlockSet DRIPSTONE_BRICKS = new StoneBlockSet(createBlock("dripstone_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK))));
    public static final Supplier<Block> DRIPSTONE_PILLAR = createBlock("dripstone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK)));
    public static final Supplier<Block> CHISELED_DRIPSTONE = createBlock("chiseled_dripstone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK)));
    public static final Supplier<Block> HEAVY_DRIPSTONE_BRICKS = createBlock("heavy_dripstone_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK), BigBrickBlock.BrickType.DRIPSTONE));
    public static final Supplier<Block> DRIPSTONE_LAMP = createBlock("dripstone_lamp", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).lightLevel((e) -> 8)));
    // Calcite
    public static final StoneBlockSet CALCITE_BRICKS = new StoneBlockSet(createBlock("calcite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE))));
    public static final Supplier<Block> CALCITE_PILLAR = createBlock("calcite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)));
    public static final Supplier<Block> CHISELED_CALCITE = createBlock("chiseled_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)));
    public static final Supplier<Block> HEAVY_CALCITE_BRICKS = createBlock("heavy_calcite_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE), BigBrickBlock.BrickType.CALCITE));
    public static final Supplier<Block> CALCITE_LAMP = createBlock("calcite_lamp", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE).lightLevel((e) -> 8)));
    // Tuff
    public static final StoneBlockSet TUFF_BRICKS = new StoneBlockSet(createBlock("tuff_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF))));
    public static final Supplier<Block> TUFF_PILLAR = createBlock("tuff_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)));
    public static final Supplier<Block> CHISELED_TUFF = createBlock("chiseled_tuff", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)));
    public static final Supplier<Block> HEAVY_TUFF_BRICKS = createBlock("heavy_tuff_bricks", () -> new BigBrickBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF), BigBrickBlock.BrickType.TUFF));
    public static final Supplier<Block> TUFF_LAMP = createBlock("tuff_lamp", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF).lightLevel((e) -> 8)));

    // Radioactive Crystals
    public static final Supplier<Block> HELIODOR_ROD = createBlock("heliodor_rod", () -> new GlassLikePillarBlock(APBlockProperties.NETHER_CRYSTAL));
    public static final Supplier<Block> EKANITE_ROD  = createBlock("ekanite_rod",  () -> new GlassLikePillarBlock(APBlockProperties.NETHER_CRYSTAL));
    public static final Supplier<Block> MONAZITE_ROD = createBlock("monazite_rod", () -> new GlassLikePillarBlock(APBlockProperties.NETHER_CRYSTAL));

    // Unobtanium
    public static final Supplier<Block> UNOBTANIUM_BLOCK = createBlock("unobtanium_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)));

    // Nether Brass
    public static final StoneBlockSet NETHER_BRASS = new StoneBlockSet(createBlock("nether_brass_block", () -> new Block(APBlockProperties.NETHER_BRASS)), TYPICAL, NUB);
    public static final StoneBlockSet CUT_NETHER_BRASS = new StoneBlockSet(createBlock("cut_nether_brass", () -> new Block(APBlockProperties.NETHER_BRASS)));
    public static final Supplier<Block> NETHER_BRASS_PILLAR = createBlock("nether_brass_pillar", () -> new RotatedPillarBlock(APBlockProperties.NETHER_BRASS));
    public static final StoneBlockSet SMOOTH_NETHER_BRASS = new StoneBlockSet(createBlock("smooth_nether_brass", () -> new Block(APBlockProperties.NETHER_BRASS)), NO_WALLS);
    public static final Supplier<Block> NETHER_BRASS_FIRE = createBlockNoItem("nether_brass_fire", () -> new GreenFireBlock(APBlockProperties.GREEN_FIRE));
    public static final Supplier<Block> NETHER_BRASS_CHAIN = createBlock("nether_brass_chain", () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(NETHER_BRASS.get()).sound(SoundType.CHAIN)));
    public static final Supplier<Block> NETHER_BRASS_LANTERN = createBlock("nether_brass_lantern", () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(NETHER_BRASS.get()).sound(SoundType.LANTERN).lightLevel((a)->13)), RegistryUtils.BUILDING_BLOCKS, RegistryUtils.FUNCTIONAL_BLOCKS);
    public static final Supplier<Block> NETHER_BRASS_TORCH = createBlockNoItem("nether_brass_torch", () -> new CustomTorchBlock(APBlockProperties.BRASS_TORCH, ParticleRegistry.GREEN_FLAME));
    public static final Supplier<Block> NETHER_BRASS_WALL_TORCH = createBlockNoItem("nether_brass_wall_torch", () -> new CustomWallTorchBlock(APBlockProperties.BRASS_TORCH.dropsLike(NETHER_BRASS_TORCH.get()), ParticleRegistry.GREEN_FLAME));

    public static final StoneBlockSet ESOTERRACK = new StoneBlockSet(createBlock("esoterrack", () -> new Block(APBlockProperties.ESOTERRACK)));
    public static final StoneBlockSet ESOTERRACK_BRICKS = new StoneBlockSet(createBlock("esoterrack_bricks", () -> new Block(APBlockProperties.ESOTERRACK)));
    public static final Supplier<Block> ESOTERRACK_PILLAR = createBlock("esoterrack_pillar", () -> new RotatedPillarBlock(APBlockProperties.ESOTERRACK));

    public static final StoneBlockSet ONYX = new StoneBlockSet(createBlock("onyx", () -> new Block(APBlockProperties.ONYX)));
    public static final StoneBlockSet ONYX_BRICKS = new StoneBlockSet(createBlock("onyx_bricks", () -> new Block(APBlockProperties.ONYX)));
    public static final Supplier<Block> ONYX_PILLAR = createBlock("onyx_pillar", () -> new RotatedPillarBlock(APBlockProperties.ONYX));

    public static final StoneBlockSet WARDSTONE = new StoneBlockSet(createBlock("wardstone", () -> new Block(APBlockProperties.WARDSTONE)));
    public static final Supplier<Block> CHISELED_WARDSTONE = createBlock("chiseled_wardstone", () -> new Block(APBlockProperties.WARDSTONE));
    public static final StoneBlockSet WARDSTONE_BRICKS = new StoneBlockSet(createBlock("wardstone_bricks", () -> new Block(APBlockProperties.WARDSTONE)));
    public static final Supplier<Block> WARDSTONE_PILLAR = createBlock("wardstone_pillar", () -> new RotatedPillarBlock(APBlockProperties.WARDSTONE));
    public static final Supplier<Block> WARDSTONE_LAMP = createBlock("wardstone_lamp", () -> new Block(BlockBehaviour.Properties.ofFullCopy(WARDSTONE.get()).lightLevel((state) -> 14)));

    public static final StoneBlockSet ANCIENT_PLATING = new StoneBlockSet(createBlock("ancient_plating", () -> new Block(APBlockProperties.ANCIENT_PLATING)), TYPICAL, FENCE);

    //    public static final Supplier<Block> ABYSSALINE_NUB = createBlock("abyssaline_nub", () -> new AbyssalineNubBlock(APBlockProperties.ABYSSALINE_NUB), CreativeModeTab.TAB_DECORATIONS);

    public static final Supplier<Block> STONE_NUB = makeNub("stone_nub", Blocks.STONE);
    public static final Supplier<Block> SMOOTH_STONE_NUB = makeNub("smooth_stone_nub", Blocks.SMOOTH_STONE);
    public static final Supplier<Block> SANDSTONE_NUB = makeNub("sandstone_nub", Blocks.SANDSTONE);
    public static final Supplier<Block> ANDESITE_NUB = makeNub("andesite_nub", Blocks.ANDESITE);
    public static final Supplier<Block> GRANITE_NUB = makeNub("granite_nub", Blocks.GRANITE);
    public static final Supplier<Block> DIORITE_NUB = makeNub("diorite_nub", Blocks.DIORITE);
    public static final Supplier<Block> BLACKSTONE_NUB = makeNub("blackstone_nub", Blocks.BLACKSTONE);
    public static final Supplier<Block> DEEPSLATE_NUB = makeNub("deepslate_nub", Blocks.POLISHED_DEEPSLATE);
    public static final Supplier<Block> BONE_NUB = makeNub("bone_nub", Blocks.BONE_BLOCK);
    public static final Supplier<Block> NUB_OF_ENDER = makeNub("nub_of_ender", ENDER_PEARL_BLOCK);
    public static final Supplier<Block> IRON_NUB = makeNub("iron_nub", Blocks.IRON_BLOCK);
    public static final Supplier<Block> GOLD_NUB = makeNub("gold_nub", Blocks.GOLD_BLOCK);
    public static final Supplier<Block> DIAMOND_NUB = makeNub("diamond_nub", Blocks.DIAMOND_BLOCK);
    public static final Supplier<Block> EMERALD_NUB = makeNub("emerald_nub", Blocks.EMERALD_BLOCK);
    public static final Supplier<Block> NETHERITE_NUB = makeNub("netherite_nub", Blocks.NETHERITE_BLOCK);

    public static final Supplier<Block> COPPER_NUB = makeCopperNub("copper_nub", Blocks.COPPER_BLOCK, UNAFFECTED);
    public static final Supplier<Block> WAXED_COPPER_NUB = makeCopperNub("waxed_copper_nub", Blocks.COPPER_BLOCK, UNAFFECTED);
    public static final Supplier<Block> EXPOSED_COPPER_NUB = makeCopperNub("exposed_copper_nub", Blocks.EXPOSED_COPPER, EXPOSED);
    public static final Supplier<Block> WAXED_EXPOSED_COPPER_NUB = makeCopperNub("waxed_exposed_copper_nub", Blocks.EXPOSED_COPPER, EXPOSED);
    public static final Supplier<Block> WEATHERED_COPPER_NUB = makeCopperNub("weathered_copper_nub", Blocks.WEATHERED_COPPER, WEATHERED);
    public static final Supplier<Block> WAXED_WEATHERED_COPPER_NUB = makeCopperNub("waxed_weathered_copper_nub", Blocks.WEATHERED_COPPER, WEATHERED);
    public static final Supplier<Block> OXIDIZED_COPPER_NUB = makeCopperNub("oxidized_copper_nub", Blocks.OXIDIZED_COPPER, OXIDIZED);
    public static final Supplier<Block> WAXED_OXIDIZED_COPPER_NUB = makeCopperNub("waxed_oxidized_copper_nub", Blocks.OXIDIZED_COPPER, OXIDIZED);

    public static final Supplier<Block> HAZARD_SIGN = createBlock("hazard_sign", () -> new SmallSignBlock(APBlockProperties.PLATING), RegistryUtils.BUILDING_BLOCKS);


    public static final BlockNode TREAD_PLATE = new BlockNode.Builder()
            .tool(BlockNode.Tool.IRON_PICK)
            .style(BlockNode.Style.CUBE)
            .base(createBlock("tread_plate", () -> new Block(APBlockProperties.PLATING)))
            .slabs()
            .variants(BlockNode.BlockType.STAIRS, BlockNode.BlockType.WALL)
            .build();

    public static final BlockNode HAZARD_BLOCK = new BlockNode.Builder()
            .tool(BlockNode.Tool.PICK)
            .base(createBlock("hazard_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_CONCRETE))))
            .slabs()
            .variants(BlockNode.BlockType.WALL)
            .build();

    public static final BlockNode SHEET_METAL = new BlockNode.Builder()
            .tool(BlockNode.Tool.PICK)
            .base(createBlock("sheet_metal_block", () -> new Block(APBlockProperties.PLATING)))
            .variants(BlockNode.BlockType.WALL)
            .build();

    public static final BlockNode BREAD_BLOCK = new BlockNode.Builder()
            .tool(BlockNode.Tool.AXE)
            .exclude(BlockNode.ExcludeFlag.MODELS)
            .base(createBlock("bread_block", () -> new BreadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL))))
            .addPart(BlockNode.BlockType.SLAB, b -> b.exclude(BlockNode.ExcludeFlag.MODELS))
            .addPart(BlockNode.BlockType.NUB)
            .addPart(BlockNode.BlockType.SPECIAL, b -> {
                b.setName("crustless_bread_block");
            })
            .build();

    public static final BlockNode ORACLE_BLOCK = new BlockNode.Builder()
            .tool(BlockNode.Tool.PICK)
            .base(createBlock("oracle_block", () -> new Block(APBlockProperties.ORACLE)))
            .slabs()
            .variants(BlockNode.BlockType.STAIRS, BlockNode.BlockType.PILLAR)
            .bricks(b -> {
                b.commonVariants();
                b.addPart(BlockNode.BlockType.DARK, BlockNode.Builder::commonVariants);
            })
            .tiles(b -> {
                b.slabs();
                b.variants(BlockNode.BlockType.STAIRS, BlockNode.BlockType.WALL);
            })
            .addPart(BlockNode.BlockType.SPECIAL, b -> {
                b.setName("framed_oracle_block");
            })
            .addPart(BlockNode.BlockType.LAMP, b -> b.base(createBlock("oracle_lamp", () -> new Block(APBlockProperties.ORACLE_LAMP))))
            .build();

    public static final BlockNode CEREBRAL_BLOCK = new BlockNode.Builder()
            .tool(BlockNode.Tool.PICK)
            .base(createBlock("cerebral_block", () -> new Block(APBlockProperties.CEREBRAL)))
            .slabs()
            .variants(BlockNode.BlockType.STAIRS, BlockNode.BlockType.WALL, BlockNode.BlockType.PILLAR)
            .tiles(b -> {
                b.exclude(BlockNode.ExcludeFlag.MODELS);
                b.commonVariants();
            })
            .build();

    public static final BlockNode MOONSHALE = new BlockNode.Builder()
            .tool(BlockNode.Tool.PICK)
            .base(createBlock("moonshale", () -> new Block(APBlockProperties.MOONSHALE)))
            .commonVariants()
            .exclude(BlockNode.ExcludeFlag.MODELS)
            .variants(BlockNode.BlockType.CHISELED)
            .bricks(b -> {
                b.commonVariants();
                b.addPart(BlockNode.BlockType.CRACKED);
                b.withPart(BlockNode.BlockType.CHISELED)
                        .setName("moonshale_flagstone");
            })
            .addPart(BlockNode.BlockType.SPECIAL, builder -> {
                builder.setName("bordered_moonshale");
            })
            .build();
    public static final BlockNode CRATERSTONE = new BlockNode.Builder()
            .tool(BlockNode.Tool.PICK)
            .base(createBlock("craterstone", () -> new Block(APBlockProperties.MOONSHALE)))
            .commonVariants()
            .build();
    public static final BlockNode NEBULITE = new BlockNode.Builder()
            .tool(BlockNode.Tool.PICK)
            .base(createBlock("nebulite", () -> new Block(APBlockProperties.NEBULITE)))
            .commonVariants()
            .addPart(BlockNode.BlockType.POLISHED, b -> {
                b.slabs();
                b.addPart(BlockNode.BlockType.STAIRS);
            })
            .build();
}