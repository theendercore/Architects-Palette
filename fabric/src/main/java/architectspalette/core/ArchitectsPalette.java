package architectspalette.core;

import architectspalette.content.particles.WizardParticle;
import architectspalette.core.datagen.BlockTagProvider;
import architectspalette.core.datagen.ItemTagProvider;
import architectspalette.core.event.CreativeModeTabEventHandler;
import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.registry.MiscRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.particle.FlameParticle;

import java.util.concurrent.atomic.AtomicReference;

public class ArchitectsPalette implements ModInitializer, ClientModInitializer, DataGeneratorEntrypoint {

    @Override
    public void onInitialize() {
        APCommon.initEarly();
        APCommon.init();

        CreativeModeTabEventHandler.init();
        APVerticalSlabsCondition.registerCondition();
    }

    @Override
    public void onInitializeClient() {
        APCommon.initClient();

        registerParticleFactories();
    }

    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.GREEN_FLAME.get(), FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.WIZARDLY_DEFENSE_BLAST.get(), WizardParticle.Provider::new);
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
