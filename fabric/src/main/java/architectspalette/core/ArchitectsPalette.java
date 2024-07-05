package architectspalette.core;

import architectspalette.core.datagen.APAdvancementProvider;
import architectspalette.core.datagen.APBlockTagProvider;
import architectspalette.core.datagen.APItemTagProvider;
import architectspalette.core.datagen.APLangProvider;
import architectspalette.core.datagen.worldgen.APFeatureCreator;
import architectspalette.core.datagen.worldgen.APWorldGenProvider;
import architectspalette.core.event.CreativeModeTabEventHandler;
import architectspalette.core.event.ModifyLootTableEventHandler;
import architectspalette.core.event.RegisterParticleProvidersEventHandler;
import architectspalette.core.event.TradingEventHandler;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.registry.APBiomeModifications;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.util.BurnableBlockItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

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
        ModifyLootTableEventHandler.modify();

        APBiomeModifications.init();

        APVerticalSlabsCondition.registerCondition();
        FuelRegistry.INSTANCE.add(APItems.CHARCOAL_BLOCK.get(), ((BurnableBlockItem) APItems.CHARCOAL_BLOCK.get()).getBurnTime());
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
        AtomicReference<APBlockTagProvider> blockTags = new AtomicReference<>();
        pack.addProvider((o, r) -> {
            blockTags.set(new APBlockTagProvider(o, r));
            return blockTags.get();
        });

        pack.addProvider((o, r) -> new APItemTagProvider(o, r, blockTags.get()));
        pack.addProvider(APWorldGenProvider::new);
        pack.addProvider(APAdvancementProvider::new);
        pack.addProvider(APLangProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, APFeatureCreator::bootstrapConfigured);
        builder.add(Registries.PLACED_FEATURE, APFeatureCreator::bootstrapPlaced);
    }
}
