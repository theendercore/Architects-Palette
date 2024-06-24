package architectspalette.core.platform;

import architectspalette.core.platform.services.IRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static architectspalette.core.APConstants.modLoc;

// Note: Registry entries MUST!!! be stored in a local variable before being put in a supplier
public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type) {
        T item = Registry.register(BuiltInRegistries.ITEM, modLoc(name), type.get());
        Supplier<T> supplierItem = () -> item;
        CREATIVE_TAB_ITEMS.add(supplierItem);
        return supplierItem;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type) {
        T block = Registry.register(BuiltInRegistries.BLOCK, modLoc(name), type.get());
        return () -> block;
    }
}
