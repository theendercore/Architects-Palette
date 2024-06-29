package architectspalette.core;

import architectspalette.core.datagen.BlockTagProvider;
import architectspalette.core.datagen.ItemTagProvider;
import architectspalette.core.event.CreativeModeTabEventHandler;
import architectspalette.core.event.RegisterParticleProvidersEventHandler;
import architectspalette.core.event.TradingEventHandler;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.registry.APBiomeModifications;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.concurrent.atomic.AtomicReference;

public class ArchitectsPalette implements ModInitializer, ClientModInitializer, DataGeneratorEntrypoint {

    @Override
    public void onInitialize() {
        APCommon.initConfig();

        APCommon.initEarly();
        APCommon.init();

        //events
        CreativeModeTabEventHandler.modifyTabs();
        TradingEventHandler.registerTrades();

        APBiomeModifications.init();

        APVerticalSlabsCondition.registerCondition();
    }

    @Override
    public void onInitializeClient() {
        APCommon.initClient();

        RegisterParticleProvidersEventHandler.registerParticleFactories();
    }



    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        // (ender) No, I don't think there is a nicer way of doing this :(
        AtomicReference<BlockTagProvider> blockTags = new AtomicReference<>();
        pack.addProvider((o, r) -> {
            blockTags.set(new BlockTagProvider(o, r));
            return blockTags.get();
        });

        pack.addProvider((o, r) -> new ItemTagProvider(o, r, blockTags.get()));
    }
}
