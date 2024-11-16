package architectspalette.core;


import architectspalette.core.config.APConfig;
import architectspalette.core.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import static architectspalette.core.APConstants.MOD_ID;

@Mod(MOD_ID)
public class ArchitectsPalette {

    public ArchitectsPalette(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);

        APCommon.init();
        NeoForgeRegistryHelper.register(modBus);

        modBus.addListener(EventPriority.LOWEST, this::setupCommon);
        modBus.addListener(EventPriority.LOWEST, this::setupClient);

     /*   var LOOT = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);
        LOOT.register("wither_skeleton_bones", WitheredBoneLootModifier.CODEC);
        LOOT.register(modEventBus);
        // Biomes need to be registered before features.
        APBiomeModifiers.BIOME_MODIFIER_SERIALIZER.register(modEventBus);

        APVerticalSlabsCondition.registerCondition(modEventBus);*/

//        NeoForge.EVENT_BUS.register(this);
    }

    void setupCommon(final FMLCommonSetupEvent event) {
        // (ender) Thanks neoforge :)
        APCommon.lateInit();
    }

    void setupClient(final FMLClientSetupEvent event) {
        APCommon.initClient();
    }
}