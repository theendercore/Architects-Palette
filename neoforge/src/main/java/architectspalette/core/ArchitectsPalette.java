package architectspalette.core;


import architectspalette.core.config.APConfig;
import architectspalette.core.event.*;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.loot.WitheredBoneLootModifier;
import architectspalette.core.platform.NeoRegistryHelper;
import architectspalette.core.registry.APBiomeModifiers;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

import static architectspalette.core.APConstants.MOD_ID;

@Mod(MOD_ID)
public class ArchitectsPalette {

    public ArchitectsPalette(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);

        APCommon.init();
        NeoRegistryHelper.register(modBus);

        modBus.addListener(EventPriority.LOWEST, this::setupCommon);
        modBus.addListener(EventPriority.LOWEST, this::setupClient);

        // Events
        modBus.addListener(CreativeModeTabEventHandler::onCreativeTabRegister);
        NeoForge.EVENT_BUS.addListener(TradingEventHandler::onTradesLoaded);
        NeoForge.EVENT_BUS.addListener(TradingEventHandler::onWanderingTradesLoaded);
        WitheredBoneLootModifier.register(modBus);
        APVerticalSlabsCondition.registerCondition(modBus);
        NeoForge.EVENT_BUS.addListener(BlockToolModificationEventHandler::addCustomStripping);

        modBus.addListener(RegisterParticleProvidersEventHandler::registerParticleFactories);
        modBus.addListener(TextureStitchEventHandler::onTextureStitchPost);
        modBus.addListener(RegisterModelLoadersEventHandler::registerModelLoaders);
        modBus.addListener(ModelBakeEventHandler::onModelBake);

        APBiomeModifiers.BIOME_MODIFIER_SERIALIZER.register(modBus);
    }

    void setupCommon(final FMLCommonSetupEvent event) {
        APCommon.lateInit();
    }

    void setupClient(final FMLClientSetupEvent event) {
        APCommon.initClient();
    }
}