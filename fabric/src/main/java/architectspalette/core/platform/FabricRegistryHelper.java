package architectspalette.core.platform;

import architectspalette.core.config.APConfig;
import architectspalette.core.platform.services.IRegistryHelper;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.rl;

// Note: Registry entries MUST!!! be stored in a local variable before being put in a supplier
public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public void resisterConfig() {
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);
    }

    @SafeVarargs
    @Override
    public final <T extends Item> Supplier<T> registerItem(String name, Supplier<T> type, ResourceKey<CreativeModeTab>... groups) {
        T item = Registry.register(BuiltInRegistries.ITEM, rl(name), type.get());
        Supplier<T> supplierItem = () -> item;
        if (groups != null) {
            for (ResourceKey<CreativeModeTab> tab : groups) {
                addCreativeTabItems(tab, supplierItem);
            }
        }
        return supplierItem;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> type) {
        T block = Registry.register(BuiltInRegistries.BLOCK, rl(name), type.get());
        return () -> block;
    }

    @Override
    public <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> type) {
        T sound = Registry.register(BuiltInRegistries.SOUND_EVENT, rl(name), type.get());
        return () -> sound;
    }

    @Override
    public <T extends ParticleType<?>> Supplier<T> registerParticleType(String name, Supplier<T> type) {
        T particle = Registry.register(BuiltInRegistries.PARTICLE_TYPE, rl(name), type.get());
        return () -> particle;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends SimpleParticleType> Supplier<T> registerSimpleParticleType(String name) {
        return (Supplier<T>) registerParticleType(name, FabricParticleTypes::simple);
    }

    @Override
    public <T extends Feature<?>> Supplier<T> registerFeature(String name, Supplier<T> type) {
        T feature = Registry.register(BuiltInRegistries.FEATURE, rl(name), type.get());
        return () -> feature;
    }

    @Override
    public <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> type) {
        T recipeSerializer = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, rl(name), type.get());
        return () -> recipeSerializer;
    }

    @Override
    public <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> type) {
        T recipeType = Registry.register(BuiltInRegistries.RECIPE_TYPE, rl(name), type.get());
        return () -> recipeType;
    }

    @Override
    public <T extends CriterionTrigger<?>> Supplier<T> registerCriterion(String name, Supplier<T> type) {
        T criterion = Registry.register(BuiltInRegistries.TRIGGER_TYPES, rl(name), type.get());
        return () -> criterion;
    }

    @Override
    public <T extends Block> void setRenderLayer(Supplier<T> block, RenderType type) {
        BlockRenderLayerMap.INSTANCE.putBlock(block.get(), type);
    }

    @Nullable
    @Override
    public <T extends Block> ResourceLocation getId(Supplier<T> blockSupplier) {
        var id = blockId(blockSupplier.get());
        return (id == BuiltInRegistries.BLOCK.getDefaultKey()) ? null : id;
    }

    @SuppressWarnings("unchecked") // (ender) again, shut up java
    @Override
    public <T extends Block> List<T> getModBlocks() {
        return (List<T>) BuiltInRegistries.BLOCK.stream().filter(it -> blockId(it).getNamespace().equals(MOD_ID)).toList();
    }

    public static ResourceLocation blockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
