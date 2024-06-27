package architectspalette.core.platform;

import architectspalette.core.APConstants;
import architectspalette.core.config.APConfig;
import architectspalette.core.platform.services.IRegistryHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.MOD_ID;

public class ForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, APConstants.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, APConstants.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);


    @Override
    public void resisterConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);
    }

    public static void register(IEventBus modEventBus) {
        SOUNDS.register(modEventBus);
        ITEMS.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);
        BLOCKS.register(modEventBus);
        FEATURES.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
    }


    @SafeVarargs
    @Override
    public final <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type, ResourceKey<CreativeModeTab>... groups) {
        Supplier<T> item = ITEMS.register(name, type);
        if (groups != null) {
            for (ResourceKey<CreativeModeTab> tab : groups) {
                addCreativeTabItems(tab, item);
            }
        }
        return item;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> type) {
        return BLOCKS.register(name, type);
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> type) {
        return SOUNDS.register(name, type);
    }

    @Override
    public <T extends ParticleType<?>> Supplier<T> registerParticleType(String name, Supplier<T> type) {
        return PARTICLE_TYPES.register(name, type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends SimpleParticleType> Supplier<T> registerSimpleParticleType(String name) {
        return (Supplier<T>) registerParticleType(name, () -> new SimpleParticleType(false));
    }

    @Override
    public <T extends Feature<?>> Supplier<T> registerFeature(String name, Supplier<T> type) {
        return FEATURES.register(name, type);
    }

    @Override
    public <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> type) {
        return RECIPE_SERIALIZERS.register(name, type);
    }

    @Override
    public <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> type) {
        return RECIPE_TYPES.register(name, type);
    }

    @SuppressWarnings("deprecation")
    @Override
    public <T extends Block> void setRenderLayer(Supplier<T> block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), type);
    }

    @Nullable
    @Override
    public <T extends Block> ResourceLocation getId(Supplier<T> blockSupplier) {
        return ((RegistryObject<T>) blockSupplier).getId();
    }

    @SuppressWarnings("unchecked") // (ender) shut up java
    @Override
    public <T extends Block> List<T> getModBlocks() {
        return (List<T>) BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
