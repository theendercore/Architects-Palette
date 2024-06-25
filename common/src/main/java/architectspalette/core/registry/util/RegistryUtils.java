package architectspalette.core.registry.util;

import architectspalette.core.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static architectspalette.core.APConstants.mcLoc;

public class RegistryUtils {
    // (ender) yes forge AT's the Tab reg keys, and not im not doing that, this ref is fine
    public static final ResourceKey<CreativeModeTab> INGREDIENTS_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc("ingredients"));
    public static final ResourceKey<CreativeModeTab> BUILDING_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc("building_blocks"));
    public static final ResourceKey<CreativeModeTab> FUNCTIONAL_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc("functional_blocks"));
    public static final ResourceKey<CreativeModeTab> REDSTONE_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc("redstone_blocks"));

    public static Item resourceItem() {
        return new Item(new Item.Properties());
    }

    public static Supplier<Item> createItem(String name) {
        return createItem(name, RegistryUtils::resourceItem);
    }

    public static Supplier<Item> createItem(String name, Supplier<Item> item) {
        return Services.REGISTRY.registerItem(name, item, INGREDIENTS_TAB);
    }

    @SafeVarargs
    public static Supplier<Item> createItem(String name, Supplier<Item> item, @Nullable ResourceKey<CreativeModeTab>... tab) {
        return Services.REGISTRY.registerItem(name, item, tab);
    }

    public static Supplier<Block> createBlock(String name, Supplier<Block> block) {
        return createBlock(name, block, BUILDING_BLOCKS);
    }

    @SafeVarargs
    public static Supplier<Block> createBlock(String name, Supplier<Block> block, @Nullable ResourceKey<CreativeModeTab>... tab) {
        Supplier<Block> regBlock = Services.REGISTRY.registerBlock(name, block);
        createItem(name, () -> new BlockItem(regBlock.get(), new Item.Properties()), tab);
        return block;
    }

    public static Supplier<Block> createBlockNoItem(String name, Supplier<Block> block) {
        return Services.REGISTRY.registerBlock(name, block);
    }

//	public static <B extends Block> StoneBlockSet createBoardSet(String name, Supplier<? extends B> supplier) {
//		StoneBlockSet boardSet = new StoneBlockSet(createBlock(name, supplier), StoneBlockSet.SetGroup.NO_WALLS).woodify();
//		boardSet.forEachRegistryObject((obj) -> ModelBakeEventHandler.register(obj, model -> new BoardModel(model, SpriteShift.getShift("block/" + name, "block/" + name + "_odd"))));
//		return boardSet;
//	}

}