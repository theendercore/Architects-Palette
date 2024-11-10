package architectspalette.core.registry.util;

import architectspalette.content.blocks.NubBlock;
import architectspalette.core.platform.Services;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static architectspalette.core.registry.APBlocks.boards;
import static architectspalette.core.util.KeyMaker.vanillaTab;

public class RegistryUtils {
    // (ender) yes forge AT's the Tab reg keys, and not im not doing that, this ref is fine
    public static final ResourceKey<CreativeModeTab> INGREDIENTS_TAB = vanillaTab("ingredients");
    public static final ResourceKey<CreativeModeTab> BUILDING_BLOCKS = vanillaTab("building_blocks");
    public static final ResourceKey<CreativeModeTab> FUNCTIONAL_BLOCKS = vanillaTab("functional_blocks");
    public static final ResourceKey<CreativeModeTab> REDSTONE_BLOCKS = vanillaTab("redstone_blocks");
    public static final ResourceKey<CreativeModeTab> NATURAL_BLOCKS = vanillaTab("natural_blocks");

    public static Item resourceItem() {
        return new Item(new Item.Properties());
    }

    public static Supplier<Item> createItem(String name) {
        return createItem(name, RegistryUtils::resourceItem);
    }

    public static Supplier<Item> createItem(String name, Supplier<Item> item) {
        return createItem(name, item, INGREDIENTS_TAB);
    }

    @SafeVarargs
    public static Supplier<Item> createItem(String name, Supplier<Item> item, @Nullable ResourceKey<CreativeModeTab>... tab) {
        return Services.REGISTRY.registerItem(name, item, tab);
    }

    public static <T extends Block> Supplier<T> createBlock(String name, Supplier<T> block) {
        return createBlock(name, block, BUILDING_BLOCKS);
    }

    @SafeVarargs
    public static <T extends Block> Supplier<T> createBlock(String name, Supplier<T> block, @Nullable ResourceKey<CreativeModeTab>... tab) {
        Supplier<T> regBlock = Services.REGISTRY.registerBlock(name, block);
        createItem(name, () -> new BlockItem(regBlock.get(), new Item.Properties()), tab);
        return regBlock;
    }

    public static Supplier<Block> createBlockNoItem(String name, Supplier<Block> block) {
        return Services.REGISTRY.registerBlock(name, block);
    }


    // Specific block helpers
    public static Supplier<Block> createPottedPlant(Supplier<Block> plant) {
        ResourceLocation id = Services.REGISTRY.getId(plant);
        if (id == null) throw new IllegalStateException("Plant is not registered " + plant.get());
        String name = id.getPath();
        Supplier<Block> pot = createBlockNoItem("potted_" + name, () ->
                new FlowerPotBlock(plant.get(), Block.Properties.ofFullCopy(Blocks.POTTED_AZURE_BLUET))
        );
        return pot;
    }

    public static Supplier<Block> makeNub(String name, Block block_to_copy) {
        return createBlock(name, () -> new NubBlock(BlockBehaviour.Properties.ofFullCopy(block_to_copy)));
    }

    public static Supplier<Block> makeNub(String name, Supplier<Block> block_to_copy) {
        return createBlock(name, () -> new NubBlock(BlockBehaviour.Properties.ofFullCopy(block_to_copy.get())));
    }

    public static Supplier<Block> makeCopperNub(String name, Block block_to_copy, WeatheringCopper.WeatherState weatheringstate) {
        return createBlock(name, () -> new NubBlock.CopperNubBlock(BlockBehaviour.Properties.ofFullCopy(block_to_copy), weatheringstate));
    }

    @SuppressWarnings("unchecked")
    public static BlockNode createBoardNode(String name, Supplier<? extends Block> supplier) {
        BlockNode node = new BlockNode.Builder()
                .tool(BlockNode.Tool.AXE)
                .base((Supplier<Block>) createBlock(name, supplier))
                .commonVariants()
                .flag(BlockNode.DataFlag.BOARDS)
                .build();
        boards.add(node);
        //(ender-?) is this needed?
//        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
//            node.forEach((n) -> ModelBakeEventHandler.register(n.getObject(), model -> new BoardModel(model, SpriteShift.getShift("block/" + name, "block/" + name + "_odd"))));
//        });
        return node;
    }
    //(ender-?) is this needed?
//	public static <B extends Block> StoneBlockSet createBoardSet(String name, Supplier<? extends B> supplier) {
//		StoneBlockSet boardSet = new StoneBlockSet(createBlock(name, supplier), StoneBlockSet.SetGroup.NO_WALLS).woodify();
//		boardSet.forEachRegistryObject((obj) -> ModelBakeEventHandler.register(obj, model -> new BoardModel(model, SpriteShift.getShift("block/" + name, "block/" + name + "_odd"))));
//		return boardSet;
//	}

}