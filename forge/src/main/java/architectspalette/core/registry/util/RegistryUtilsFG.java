package architectspalette.core.registry.util;

import architectspalette.core.platform.ForgeRegistryHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class RegistryUtilsFG {

	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier) {
		return createBlock(name, supplier, CreativeModeTabs.BUILDING_BLOCKS);
	}

	@SafeVarargs
	public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<? extends B> supplier, ResourceKey<CreativeModeTab>... group) {
		RegistryObject<B> block = ForgeRegistryHelper.BLOCKS.register(name, supplier);
		ForgeRegistryHelper.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		return block;
	}

//	public static <B extends Block> StoneBlockSet createBoardSet(String name, Supplier<? extends B> supplier) {
//		StoneBlockSet boardSet = new StoneBlockSet(createBlock(name, supplier), StoneBlockSet.SetGroup.NO_WALLS).woodify();
//		boardSet.forEachRegistryObject((obj) -> ModelBakeEventHandler.register(obj, model -> new BoardModel(model, SpriteShift.getShift("block/" + name, "block/" + name + "_odd"))));
//		return boardSet;
//	}
}