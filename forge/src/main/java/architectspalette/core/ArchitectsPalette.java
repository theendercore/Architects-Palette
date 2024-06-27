package architectspalette.core;

import architectspalette.core.integration.APTrades;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.loot.WitheredBoneLootModifier;
import architectspalette.core.platform.ForgeRegistryHelper;
import architectspalette.core.registry.APBiomeModifiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static architectspalette.core.APConstants.MOD_ID;

@Mod(value = MOD_ID)
public class ArchitectsPalette {

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public ArchitectsPalette() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        APCommon.init();
        ForgeRegistryHelper.register(modEventBus);

        modEventBus.addListener(EventPriority.LOWEST, this::setupCommon);
        modEventBus.addListener(EventPriority.LOWEST, this::setupClient);

        // (ender) the rest will be heavily reworked in the future.

        var LOOT = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);
        LOOT.register("wither_skeleton_bones", WitheredBoneLootModifier.CODEC);
        LOOT.register(modEventBus);
        // Biomes need to be registered before features.
        APBiomeModifiers.BIOME_MODIFIER_SERIALIZER.register(modEventBus);

        APVerticalSlabsCondition.registerCondition(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    void setupCommon(final FMLCommonSetupEvent event) {
        // (ender) Thanks forge :)
        APCommon.initEarly();

        APTrades.registerTrades();
    }

    void setupClient(final FMLClientSetupEvent event) {
        APCommon.initClient();
    }
}
