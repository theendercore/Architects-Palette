package architectspalette.core.platform;

import architectspalette.core.APConstants;
import architectspalette.core.event.CreativeModeTabEventHandler;
import architectspalette.core.platform.services.IRegistryHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, APConstants.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, APConstants.MOD_ID);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
//        BLOCKS.register(modEventBus);
    }


    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type, ResourceKey<CreativeModeTab> group) {
        Supplier<T> item = ITEMS.register(name, type);
        addCreativeTabItems(group, item);
        CreativeModeTabEventHandler.assignItemToTab(item, group);
        return item;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type) {
        return BLOCKS.register(name, type);
    }
}
