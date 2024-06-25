package architectspalette.core.registry.util;

import architectspalette.core.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import static architectspalette.core.APConstants.mcLoc;

public class RegistryUtils {
    // (ender) yes forge at's the Tab reg keys, and not im not doing that, this ref is fine
    static final ResourceKey<CreativeModeTab> INGREDIENTS_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, mcLoc("ingredients"));

    public static Item resourceItem() {
        return new Item(new Item.Properties());
    }

    public static Supplier<Item> createItem(String name) {
        return createItem(name, RegistryUtils::resourceItem);
    }

    public static Supplier<Item> createItem(String name, Supplier<Item> item) {
        return Services.REGISTRY.registerItem(name, item, INGREDIENTS_TAB);
    }

    public static Supplier<Item> createItem(String name, Supplier<Item> item, ResourceKey<CreativeModeTab> tab) {
        return Services.REGISTRY.registerItem(name, item, tab);
    }


//	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier) {
//		return createBlock(name, supplier, CreativeModeTabs.BUILDING_BLOCKS);
//	}
//
//	@SafeVarargs
//	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, ResourceKey<CreativeModeTab>... group) {
//		RegistryObject<B> block = APBlocks.BLOCKS.register(name, supplier);
//		ForgeRegistryHelper.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
//		if (group != null) CreativeModeTabEventHandler.assignItemToTab(block, group);
//		return block;
//	}
//
//	public static <B extends Block> RegistryObject<B> createBlockNoItem(String name, Supplier<? extends B> supplier) {
//		return APBlocks.BLOCKS.register(name, supplier);
//	}
//
//
//	public static <B extends Block> StoneBlockSet createBoardSet(String name, Supplier<? extends B> supplier) {
//		StoneBlockSet boardSet = new StoneBlockSet(createBlock(name, supplier), StoneBlockSet.SetGroup.NO_WALLS).woodify();
//		boardSet.forEachRegistryObject((obj) -> ModelBakeEventHandler.register(obj, model -> new BoardModel(model, SpriteShift.getShift("block/" + name, "block/" + name + "_odd"))));
//		return boardSet;
//	}
}