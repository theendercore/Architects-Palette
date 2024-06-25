package architectspalette.core.platform;

import architectspalette.core.config.APConfig;
import architectspalette.core.platform.services.IRegistryHelper;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.config.ModConfig;

import java.util.function.Supplier;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.modLoc;

// Note: Registry entries MUST!!! be stored in a local variable before being put in a supplier
public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public void resisterConfig() {
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type, ResourceKey<CreativeModeTab> group) {
        T item = Registry.register(BuiltInRegistries.ITEM, modLoc(name), type.get());
        Supplier<T> supplierItem = () -> item;
        addCreativeTabItems(group, supplierItem);
        return supplierItem;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlockNoItem(String name, Supplier<T> type) {
        T block = Registry.register(BuiltInRegistries.BLOCK, modLoc(name), type.get());
        return () -> block;
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> type) {
        T block = Registry.register(BuiltInRegistries.SOUND_EVENT, modLoc(name), type.get());
        return () -> block;
    }
}
