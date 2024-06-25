package architectspalette.core;

import architectspalette.core.config.APConfig;
import architectspalette.core.platform.ForgeRegistryHelper;
import architectspalette.core.registry.APRecipes;
import architectspalette.core.integration.APCriterion;
import architectspalette.core.integration.APTrades;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.loot.WitheredBoneLootModifier;
import architectspalette.core.registry.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static architectspalette.core.APConstants.MOD_ID;

@Mod(value = MOD_ID)
public class ArchitectsPalette {

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public ArchitectsPalette() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);

        MiscRegistry.PARTICLE_TYPES.register(modEventBus);
        APSounds.SOUNDS.register(modEventBus);
        APBlocks.BLOCKS.register(modEventBus);

        ForgeRegistryHelper.register(modEventBus);
//        ForgeRegistryHelper.BLOCKS.register(modEventBus);

        APFeatures.FEATURES.register(modEventBus);
        APRecipes.RECIPE_TYPES.register(modEventBus);

        modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
        modEventBus.addListener(EventPriority.LOWEST, this::setupClient);

//        registerRecipeSerializers(modEventBus);
        registerLootSerializers(modEventBus);
        // Biomes need to be registered before features.
        registerBiomeSerializers(modEventBus);

        DeferredRegister.create(ForgeRegistries.CONDITION_SERIALIZERS, MOD_ID)
                .register("enable_vertical_slabs", () -> APVerticalSlabsCondition.CODEC);

        MinecraftForge.EVENT_BUS.register(this);

        APCommonClass.init();
    }

    void setupCommon(final FMLCommonSetupEvent event) {

        APBlockProperties.registerFlammables();
        APTrades.registerTrades();

        // Is this okay to go here?
        // (ender) I mean that's kind what vanilla does so probably
        APCriterion.init();
    }

//    void registerRecipeSerializers(IEventBus bus) {
//        //Register the recipe type
//        DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ArchitectsPalette.MOD_ID);
//        RegistryObject<RecipeType<WarpingRecipe>> WARPING = RECIPE_TYPES.register(WarpingRecipe.TYPE.toString(), () -> new RecipeType<>() {
//        });
//
//        //Register the serializer
//        DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArchitectsPalette.MOD_ID);
//        RegistryObject<WarpingRecipe.Serializer> WARPING_S = RECIPE_SERIALIZERS.register(WarpingRecipe.TYPE.toString(), () -> WarpingRecipe.SERIALIZER);
//
//        RECIPE_TYPES.register(bus);
//        RECIPE_SERIALIZERS.register(bus);
//    }

    void registerLootSerializers(IEventBus bus) {
        DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);
        RegistryObject<MapCodec<WitheredBoneLootModifier>> WITHER_SKELETON_DROPS = LOOT.register("wither_skeleton_bones", WitheredBoneLootModifier.CODEC);

        LOOT.register(bus);
    }

    void registerBiomeSerializers(IEventBus bus) {
        APBiomeModifiers.BIOME_MODIFIER_SERIALIZER.register(bus);
    }

    void setupClient(final FMLClientSetupEvent event) {
        APRenderLayers.setupRenderLayers();
    }
}
