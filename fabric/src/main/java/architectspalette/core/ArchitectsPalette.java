package architectspalette.core;

import architectspalette.core.config.APConfig;
import architectspalette.core.datagen.*;
import architectspalette.core.datagen.worldgen.APFeatureCreator;
import architectspalette.core.datagen.worldgen.APWorldGenProvider;
import architectspalette.core.event.*;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.registry.APBiomeModifications;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.util.BurnableBlockItem;
import architectspalette.core.util.DebugCommand;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.fml.config.ModConfig;

import static architectspalette.core.APConstants.MOD_ID;

public class ArchitectsPalette implements ModInitializer, ClientModInitializer, DataGeneratorEntrypoint {

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, APConfig.COMMON_CONFIG);


        APCommon.init();
        APCommon.lateInit();

        // Events
        CreativeModeTabEventHandler.modifyTabs();
        TradingEventHandler.registerTrades();
        ModifyLootTableEventHandler.modify();

        APBiomeModifications.init();

        APVerticalSlabsCondition.registerCondition();
        FuelRegistry.INSTANCE.add(APItems.CHARCOAL_BLOCK.get(), ((BurnableBlockItem) APItems.CHARCOAL_BLOCK.get()).getBurnTime());


        // Debug
        DebugCommand.registerDebugCommand();
    }

    @Override
    public void onInitializeClient() {
        APCommon.initClient();

        RegisterParticleProvidersEventHandler.registerParticleFactories();
        APModelLoaders.init();
        ModelBakeEventHandler.init();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        APBlockTagProvider blockTags = pack.addProvider(APBlockTagProvider::new);
        pack.addProvider((o, r) -> new APItemTagProvider(o, r, blockTags));
        pack.addProvider(APWorldGenProvider::new);
        pack.addProvider(APAdvancementProvider::new);
        pack.addProvider(APLangProvider::new);
        pack.addProvider(APRecipeProvider::new);
        pack.addProvider(APLootTablesProvider::new);
        pack.addProvider(APModelProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, APFeatureCreator::bootstrapConfigured);
        builder.add(Registries.PLACED_FEATURE, APFeatureCreator::bootstrapPlaced);
    }
}
